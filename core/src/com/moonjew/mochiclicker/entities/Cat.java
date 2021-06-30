package com.moonjew.mochiclicker.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.moonjew.mochiclicker.io.Animation;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Cat {
    private static final List<String> usedNames = new ArrayList<>();
    Animation texture;
    Texture sourceTexture;
    Rectangle position;
    Vector2 velocity; //temp variable
    Rectangle room; //boundaries of the room
    String name;


    float happiness; //0 - 100, affects how likely the cat is to fall asleep
    int happyModifier; //How much happiness it gets from toys

    boolean sleeping; //Is the cat asleep?
    double tired; //0 - 10, once the cat reaches 10, it falls asleep
    int sleepModifier; //How fast the cat wakes up after falling asleep

    float health; //0 - 100, affects cat's health? not sure how to implement this
    int healthModifier; //How much health the cat gains from exercise
    public boolean alert; //Cat is about to die!

    float hunger; //0 - 100, the cat gets slower until it reaches 100, then it stops moving
    int hungerModifier; //How much hunger it gets from food

    double level; //The number of upgrades purchased, affects how much catnip is acquired per click

    //Movement
    Vector2 targetPosition; //Where the cat is going to
    boolean hitTargetPosition;
    int speed = 2;

    public float outsideTimer; //How long the cat has been outside, -1 if cat is not outside
    private float timeOutside; //How long the cat will be outside
    private boolean inMainRoom; //If the cat is in the main room, you don't have to care for it.

    public Cat(String name, Texture sourceTexture, int x, int y, int width, int height, Rectangle room) {
        this.name = name;
        this.sourceTexture = sourceTexture;
        TextureRegion src = new TextureRegion(sourceTexture, 400, 42);
        this.texture = new Animation(src, 5, 0.6f);
        this.position = new Rectangle(x + room.x, y + room.y, -width, height);
        this.velocity = new Vector2();
        this.room = room;
        this.level = 1;
        this.happiness = 100;
        this.sleeping = false;
        this.tired = 0;
        this.sleepModifier = 1;
        this.hunger = 0;
        this.hungerModifier = 1;
        this.health = 100;
        this.healthModifier = 1;
        this.timeOutside = 10;
        this.outsideTimer = -1;
        usedNames.add(this.name);
        double targetX = Math.random()*room.width + room.x;
        double targetY = Math.random()*room.height + room.y;
        targetPosition = new Vector2((float)targetX, (float)targetY);
        genTargetPosition();
    }

    public void render(SpriteBatch spriteBatch, Camera cam){
        spriteBatch.draw(getTexture(), cam.position.x + getPosition().x, cam.position.y + getPosition().y, getPosition().width, getPosition().height);
        spriteBatch.draw(getTexture(), cam.position.x + targetPosition.x, cam.position.y + targetPosition.y, 32, 32);
    }

    public void update(float deltaTime){
        //update animation
        if(!sleeping) texture.update(deltaTime);

        if(this.health == 0){
            this.alert = true;
        }

        //movement
        if(!sleeping && !alert) {
            moveToTarget();
            position.x += velocity.x * deltaTime * (100 - hunger);
            position.y += velocity.y * deltaTime * (100 - hunger);
        }

        //handle state
        if(outsideTimer == -1 && !inMainRoom) {
            if (!sleeping && !alert) {
                tired += deltaTime;
                if (tired >= 50) {
                    sleep();
                }
                happiness -= deltaTime * 3;
                hunger += deltaTime * 5;

                if (hunger >= 75 || happiness <= 20) {
                    health -= deltaTime * 0.1;
                }
                if (hunger >= 100 || happiness <= 0) {
                    health -= deltaTime * 0.9;
                }
            }

            happiness = clampFloat(happiness, 0, 100);
            health = clampFloat(health, 0, 100);
            hunger = clampFloat(hunger, 0, 100);

            if (sleeping && tired != 0) {
                tired -= deltaTime * sleepModifier;

                if (tired <= 0) {
                    sleeping = false;
                }
            }
        } else if(outsideTimer != -1){
            outsideTimer += deltaTime;
            if(outsideTimer > timeOutside){
                //return with stuff
                outsideTimer = -1;
            }
        }
    }

    public void moveToTarget(){
        float xDif = targetPosition.x - (position.x + position.width/2);
        float yDif = targetPosition.y - (position.y);

        if(Math.abs(xDif) < 10 && Math.abs(yDif) < 10 || hitTargetPosition) {
            hitTargetPosition = true;
            //we hit the target
            if(checkMove()){
                genTargetPosition();
                this.hitTargetPosition = false;
            } else {
                this.targetPosition = new Vector2(-50,-50);
                sleep();
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
    public void sendToMainRoom(){
        this.inMainRoom = true;
    }

    private boolean checkMove(){
        return happiness >= Math.random() * 100;
    }
    private void genTargetPosition() {
        double targetX = Math.random() * room.width + room.x;
        double targetY = Math.random() * room.height + room.y;
        targetPosition = new Vector2((float) targetX, (float) targetY);
    }
    public boolean randomTick(){
        return (int) (Math.random() * 1000) < Math.floor(level);
    }
    public void sendOutside(){
        if(this.outsideTimer == -1) this.outsideTimer = 0.0f;
    }
    public void sleep(){
        //set animation to sleeping animation
        sleeping = true;
    }
    public void increaseHealth(){
        health += 10;
    }

    public void eat(){
        this.hunger -= 20 * hungerModifier;
        this.hunger = clampFloat(hunger, 0, 100);
    }
    public void pet(){
        this.happiness += 5 * happyModifier;
        this.happiness = clampFloat(happiness, 0, 100);
    }

    public static float clampFloat(float val, float min, float max){ //Clamps a variable in between 2 values
        if(val < min) return min;
        if(val > max) return max;
        return val;

    }

    public void heal() {
        this.alert = false;
        this.health = 100;
    }
    public void die() {

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
    public void setSleeping(boolean sleeping) {
        this.sleeping = sleeping;
    }
    public void setPosition(Rectangle position) {
        this.position = position;
    }
    public double getTired() {
        return tired;
    }
    public boolean isSleeping() {
        return sleeping;
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
                "Elwood", "Coco"
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
