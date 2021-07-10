package com.moonjew.mochiclicker.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.moonjew.mochiclicker.room.Decoration;
import com.moonjew.mochiclicker.room.Room;
import com.moonjew.mochiclicker.upgrades.Upgrade;
import com.moonjew.mochiclicker.io.Animation;

import java.util.ArrayList;
import java.util.List;

public class Cat {
    private static final List<String> usedNames = new ArrayList<>();
    Animation texture;
    Animation sleepingTexture;
    Texture sourceTexture;
    Texture sleepTexture;
    Rectangle position;
    Rectangle floorBounds;
    Vector2 velocity; //temp variable
    Room room;
    String name;

    CatState state;

    float happiness; //0 - 100, affects how likely the cat is to fall asleep
    public int maxHappiness; //How much happiness it gets from toys

    float tired; //0 - 10, once the cat reaches 10, it falls asleep
    public int maxTired; //How fast the cat wakes up after falling asleep
    public float tiredModifier; // How fast the cat falls asleep

    float health; //0 - 100, affects cat's health? not sure how to implement this
    public int maxHealth; //How much health the cat gains from exercise

    float hunger; //0 - 100, the cat gets slower until it reaches 100, then it stops moving
    public int maxHunger; //How much hunger it gets from food

    float outsideChanceModifier;

    int level; //The number of upgrades purchased, affects how much catnip is acquired per click

    //Movement
    Vector2 targetPosition; //Where the cat is going to
    boolean hitTargetPosition;
    int speed = 2;

    private float maxTimeOutside; //How long the cat will be outside
    private boolean inMainRoom; //If the cat is in the main room, you don't have to care for it.

    public Cat(String name, Texture sourceTexture, Texture sleepTexture, int x, int y, int width, int height, Room room) {
        this.name = name;
        this.sourceTexture = sourceTexture;
        TextureRegion src = new TextureRegion(sourceTexture, 400, 42);
        this.texture = new Animation(src, 5, 0.6f);
        this.sleepTexture = sleepTexture;
        TextureRegion src2 = new TextureRegion(sleepTexture, 160, 42);
        this.sleepingTexture = new Animation(src2, 2, 1.0f);
        //this.sleepingTexture = new Animation(new TextureRegion(new Texture("paige_sleep.png"), 160, 42), 2, 1.0f);
        this.floorBounds = new Rectangle(room.getRectangle().x + room.getRectangle().width/3, room.getRectangle().y, room.getRectangle().width*2/3, room.getRectangle().height/2);
        this.position = new Rectangle(x + floorBounds.x, y + floorBounds.y, -width, height);
        this.velocity = new Vector2();
        this.room = room;

        this.state = new CatState(CatState.CatStateType.DEFAULT);
        this.level = 1;
        this.happiness = 100;
        this.maxHappiness = 100;
        this.tired = 0;

        this.maxTired = 5;
        this.tiredModifier = 1;
        this.hunger = 0;
        this.maxHunger = 100;
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
        if(!isSleeping()) {
            spriteBatch.draw(getTexture(), cam.position.x + getPosition().x, cam.position.y + getPosition().y, getPosition().width, getPosition().height);
        } else {
            spriteBatch.draw(sleepingTexture.getFrame(), cam.position.x + getPosition().x, cam.position.y + getPosition().y, getPosition().width, getPosition().height);
        }
        spriteBatch.draw(getTexture(), cam.position.x + targetPosition.x, cam.position.y + targetPosition.y, 32, 32);
    }

    public void update(float deltaTime){
        //update animation
        if(!isSleeping()) texture.update(deltaTime);
        else {
            sleepingTexture.update(deltaTime);
        }

        //movement
        if(!isSleeping() && !isDying() && !isIdle()) {
            doMovement(deltaTime);
        }

        //handle state
        if(!isOutside() && !inMainRoom) {
            if (!isSleeping() && !isDying() && !isIdle()) {
                tired += deltaTime * ((100-tiredModifier)/100);
                if (tired >= maxTired) {
                    sleep();
                }
                happiness -= deltaTime * room.getDecoration(Decoration.DecorationType.PAINTING);
                hunger += deltaTime * room.getDecoration(Decoration.DecorationType.FOOD_WATER_BOWL);

                if (hunger >= maxHunger * 0.25f || happiness <= maxHappiness * 0.25f) {
                    health -= deltaTime * 0.1 * ((100-room.getDecoration(Decoration.DecorationType.LITTER_BOX))/100);
                }
                if (hunger >= 100 || happiness <= 0) {
                    health -= deltaTime * 0.9 * ((100-room.getDecoration(Decoration.DecorationType.LITTER_BOX))/100);
                }
            }

            happiness = clampFloat(happiness, 0, maxHappiness);
            health = clampFloat(health, 0, maxHealth);
            hunger = clampFloat(hunger, 0, maxHunger);

            //State stuff
            this.state.update(deltaTime);
            if(isSleeping()) {
                if (!this.state.finished) {
                    this.tired = this.state.maxTime - this.state.timer;
                } else {
                    this.tired = 0;
                }
            }
            if(this.health == 0){
                changeState(new CatState(CatState.CatStateType.DYING));
            }
            if(this.state.finished){
                changeState(new CatState(CatState.CatStateType.DEFAULT));
            }
        }
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
                if(!isSleeping() && !isIdle()) {
                    changeState(new CatState(CatState.CatStateType.IDLE, 5));
                }
            }

            increaseHealth();
        }
        double mag = Math.sqrt(xDif * xDif + yDif * yDif);

        float tempX = (float) (xDif / mag * speed); //previous = 2 next = 1
        float tempY= (float) (yDif / mag * speed);

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
        return happiness >= Math.random() * 75;
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
        changeState(new CatState(CatState.CatStateType.SLEEPING, maxTired, room.getDecoration(Decoration.DecorationType.BED)));
        this.tired = maxTired;
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
        position.x += velocity.x * deltaTime * (maxHunger - hunger);
        position.y += velocity.y * deltaTime * (maxHunger - hunger);
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
                "Elwood", "Coco", "Tiger"
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
