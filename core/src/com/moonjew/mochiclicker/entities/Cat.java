package com.moonjew.mochiclicker.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.moonjew.mochiclicker.MochiClicker;
import com.moonjew.mochiclicker.room.Decoration;
import com.moonjew.mochiclicker.room.Room;
import com.moonjew.mochiclicker.io.Animation;

import java.util.ArrayList;
import java.util.List;

public class Cat {
    private static final List<String> usedNames = new ArrayList<>();
    Animation texture;
    Animation sleepingTexture;
    Animation idlingTexture;
    Texture sourceTexture;
    Texture sleepTexture;
    Texture idleTexture;
    Rectangle position;
    Rectangle floorBounds;
    Vector2 velocity; //temp variable
    Room room;
    String name;

    CatState state;

    float happiness; //0 - 100, affects how likely the cat is to fall asleep
    public float happinessModifier;
    public int maxHappiness; //How much happiness it gets from toys

    float tired; // how tired the cat is
    public int maxTired; // upgrade in shop
    public float tiredModifier; // bed decoration

    float health; //0 - 100, affects cat's health? not sure how to implement this
    public int maxHealth; // upgrade in the shop

    float hunger; //0 - 100, the cat gets slower until it reaches 100, then it stops moving
    public int maxHunger; // food bowl
    public float hungerModifier; //upgrade in the shop

    float outsideChanceModifier; // upgrade in the shop

    int level; //The number of upgrades purchased, affects how much catnip is acquired per click

    //Movement
    Vector2 targetPosition; //Where the cat is going to
    boolean hitTargetPosition;
    int speed = 20; // upgrade in the shop

    private float maxTimeOutside; //How long the cat will be outside
    private boolean inMainRoom; //If the cat is in the main room, you don't have to care for it.

    public Cat(String name, Texture sourceTexture, Texture sleepTexture, Texture idleTexture, int width, int height, Room room) {
        this.name = name;
        this.sourceTexture = sourceTexture;
        TextureRegion src = new TextureRegion(sourceTexture, 400, 42);
        this.texture = new Animation(src, 5, 0.6f);
        this.sleepTexture = sleepTexture;
        TextureRegion src2 = new TextureRegion(sleepTexture, 160, 42);
        this.sleepingTexture = new Animation(src2, 2, 1.0f);
        this.idleTexture = idleTexture;
        TextureRegion src3 = new TextureRegion(idleTexture, 770, 60);
        this.idlingTexture = new Animation(src3, 14, 1.5f);
        //this.sleepingTexture = new Animation(new TextureRegion(new Texture("paige_sleep.png"), 160, 42), 2, 1.0f);
        this.floorBounds = new Rectangle(0,0, room.getRectangle().width/3, room.getRectangle().height/3).setCenter(MochiClicker.WIDTH/2,MochiClicker.HEIGHT/3);
        this.position = new Rectangle( floorBounds.x, floorBounds.y, -width, height);
        this.velocity = new Vector2();
        this.room = room;

        this.state = new CatState(CatState.CatStateType.DEFAULT);
        this.level = 1;
        this.happiness = 100;
        this.happinessModifier = 1; // upgrade in the shop
        this.maxHappiness = 100;

        this.tired = 0;
        this.maxTired = 15;
        this.tiredModifier = 1;

        this.hunger = 0;
        this.maxHunger = 100;
        this.hungerModifier = 1;

        this.health = 100;
        this.maxHealth = 100;
        this.maxTimeOutside = 10;
        usedNames.add(this.name);

        outsideChanceModifier = 0.0f;
        double targetX = Math.random()*room.getRectangle().width + room.getRectangle().x;
        double targetY = Math.random()*room.getRectangle().height + room.getRectangle().y;
        targetPosition = new Vector2((float)targetX, (float)targetY);
        genTargetPosition();
    }

    public void render(SpriteBatch spriteBatch, Camera cam){
        if(state.type == CatState.CatStateType.DEFAULT) {
            spriteBatch.draw(getTexture(), cam.position.x + getPosition().x, cam.position.y + getPosition().y, getPosition().width, getPosition().height);
        } else if(state.type == CatState.CatStateType.SLEEPING){
            spriteBatch.draw(sleepingTexture.getFrame(), cam.position.x + getPosition().x, cam.position.y + getPosition().y, getPosition().width, getPosition().height);
        }
        else if(state.type == CatState.CatStateType.IDLE) {
            spriteBatch.draw(idlingTexture.getFrame(),  cam.position.x + getPosition().x, cam.position.y + getPosition().y,getPosition().width * 0.7f, getPosition().height * 1.4f);
        }

        spriteBatch.draw(getTexture(), cam.position.x + targetPosition.x, cam.position.y + targetPosition.y, 32, 32);
    }

    public void update(float deltaTime){

        if(this.state.type == CatState.CatStateType.DEFAULT) tired += deltaTime * tiredModifier;
        hunger += deltaTime * hungerModifier;
        happiness -= deltaTime * happinessModifier;
        health -= deltaTime * room.getMessList().size() / 10;


        if(tired > maxTired){
            changeState(new CatState(CatState.CatStateType.SLEEPING, maxTired - room.getDecorationValue(Decoration.DecorationType.BED)));
        }

        doMovement(deltaTime);

        if(state.finished){
            changeState(new CatState(CatState.CatStateType.DEFAULT));
        }

        getCurrentAnimation().update(deltaTime);
    }

    public void moveToTarget(){
        float xDif = targetPosition.x - (position.x + position.width/2);
        float yDif = targetPosition.y - (position.y);

        if(Math.abs(xDif) < 10 && Math.abs(yDif) < 10 || hitTargetPosition) {
            hitTargetPosition = true;
            //we hit the target
            if(checkMove() || (isIdle() && state.finished)){
                genTargetPosition();
                this.hitTargetPosition = false;
            } else {
                changeState(new CatState(CatState.CatStateType.IDLE, 5));
            }
        }
        double mag = Math.sqrt(xDif * xDif + yDif * yDif);

        float tempSpeed = speed * hunger/maxHunger;
        float tempX = (float) (xDif / mag * tempSpeed); //previous = 2 next = 1
        float tempY= (float) (yDif / mag * tempSpeed);

        boolean newYee = tempX > 0;
        boolean yee = velocity.x > 0;
        if(newYee != yee){ //it flipped
            position.width = -position.width;
            position.x -= position.width;
        }
        velocity.x = tempX;
        velocity.y = tempY;
    }

    public boolean touch(float x, float y){
        if(position.width > 0){
            return x > position.x && x < position.x + position.width && y > position.y && y < position.y + position.height;
        } return x < position.x && x > position.x + position.width && y > position.y && y < position.y + position.height;
    }
    public void sendToMainRoom() {
        this.inMainRoom = true;
        this.health = 100;
        this.hunger = 0;
        this.happiness = 100;
        this.tired = 0;
    }
    private boolean checkMove() {
        return Math.random() * 100 < hunger;
    }
    private void genTargetPosition() {
        double targetX = (Math.random() * floorBounds.width) + floorBounds.x;
        double targetY = (Math.random() * floorBounds.height) + floorBounds.y;
        targetPosition = new Vector2((float) targetX, (float) targetY);
    }
    public boolean randomTick(){
        return (int) (Math.random() * 1000) < Math.floor(level);
    }
    public void sendOutside(){
        this.state = new CatState(CatState.CatStateType.OUTSIDE, 20);
    }
    public boolean isIdle(){
        return this.state.type == CatState.CatStateType.IDLE;
    }
    public void sleep(){
        changeState(new CatState(CatState.CatStateType.SLEEPING, maxTired, room.getDecorationValue(Decoration.DecorationType.BED)));
        this.tired = maxTired;
    }

    Animation getCurrentAnimation(){
        if(this.state.type == CatState.CatStateType.DEFAULT){
            return texture;
        } else if(this.state.type == CatState.CatStateType.SLEEPING){
            return sleepingTexture;
        } else if(this.state.type == CatState.CatStateType.IDLE){
            return idlingTexture;
        }
        return texture;
    }
    public void changeState(CatState state){
        if(state.type != this.state.type){
            System.out.println(this.state + " -> " + state);
            this.state = state;
        }
    }
    public void increaseHealth(){
        health += 10;
    }

    public void eat(){
        this.hunger -= 20 * maxHunger;
        this.hunger = clampFloat(hunger, 0, 100);
    }
    public void pet(){
        this.happiness += 5 * maxHappiness;
        this.happiness = clampFloat(happiness, 0, 100);
    }

    public static float clampFloat(float val, float min, float max){ //Clamps a variable in between 2 values
        if(val < min) return min;
        if(val > max) return max;
        return val;

    }
    public boolean isDying(){
        return this.state.type == CatState.CatStateType.DYING;
    }
    public void heal() {
        this.state = new CatState(CatState.CatStateType.DEFAULT);
        this.health = 100;
    }

    public void doMovement(float deltaTime){
        moveToTarget();
        if(canMove()) {
            position.x += velocity.x * deltaTime * hunger;
            position.y += velocity.y * deltaTime * hunger;
        }
    }

    public boolean updateOutside(float deltaTime){
        this.state.update(deltaTime);
        texture.update(deltaTime);
        if(this.state.finished){
            System.out.println("Cat is returning");
            //send inside
            double rand = Math.random();
            if(rand <= 0.2 - outsideChanceModifier){
                //bad outcome
                health -= 20;
            } else if(rand <= 0.4 - outsideChanceModifier){
                //do nothing
            } else if(rand <= 0.6 - outsideChanceModifier){
                //good outcome, increase random stat by some amount
                increaseRandomStat(1);
            } else if(rand <= 0.8 - outsideChanceModifier){
                //better outcome
                increaseRandomStat(3);
            } else {
                //best outcome
                increaseRandomStat(5);
            }
            return true;
        }
        doMovement(deltaTime);

        return false;
    }
    public void increaseRandomStat(int value){
        double stat = Math.random() * 6;
        if(stat < 1){
            //increase health
            maxHealth += value;
        }
        else if(stat < 2){
            //tired
            tiredModifier += value * 0.2;
        }
        else if(stat < 3){
            maxHunger += value;
        }
        else if(stat < 4){
            maxHappiness += value;
        }
        else if(stat < 5){
            maxTired += value / 2;
        }
        else {
            maxTimeOutside -= value;
        }
    }

    private boolean canMove(){
        return !isIdle() && !isSleeping() && !isDying();
    }
    public CatState getState() {
        return state;
    }

    public void setState(CatState state) {
        this.state = state;
    }

    public void setHunger(float hunger) {
        this.hunger = hunger;
    }
    public float getHunger() {
        return hunger;
    }
    public String getName() {
        return name;
    }
    public float getHappiness() {
        return happiness;
    }
    public void setHappiness(float happiness) {
        this.happiness = happiness;
    }
    public float getHealth() {
        return health;
    }
    public void setHealth(float health) {
        this.health = health;
    }
    public double getLevel() {
        return level;
    }
    public void levelUp() {
        this.level++;
    }
    public void setPosition(Rectangle position) {
        this.position = position;
    }
    public double getTired() {
        return tired;
    }
    public boolean isSleeping() {
        return this.state.type == CatState.CatStateType.SLEEPING;
    }
    public boolean isOutside(){
        return this.state.type == CatState.CatStateType.OUTSIDE;
    }
    public TextureRegion getTexture(){
        return texture.getFrame();
    }
    public Rectangle getPosition(){
        return position;
    }
    public void dispose(){
        sourceTexture.dispose();
    }

    public static String randomName(){
        String[] names = {
                "Bob", "Joe", "Dog", "Paige",
                "Sparky", "Marshmallow", "Rigby",
                "Elwood", "Coco",
                "Tiger"
        };
        //make sure that names don't get reused
        //I actually found a use for do while loops? wtf??
        int nameIndex;
        do {
            nameIndex = (int) (Math.random() * names.length);
        } while(usedNames.contains(names[nameIndex]));
        usedNames.add(names[nameIndex]);
        return names[nameIndex];
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", happiness=" + happiness +
                ", tired=" + tired +
                ", health=" + health +
                ", level=" + level +
                '}';
    }
}
