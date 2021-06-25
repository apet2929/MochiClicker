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
    float happiness; //0 - 100, affects cat speed
    boolean sleeping;
    double tired; //0 - 10, once the cat reaches 10, it falls asleep
    float health; //0 - 100, affects cat's health? not sure how to implement this
    float hunger;
    double level; //The number of upgrades purchased, affects how much catnip is acquired per click
    Vector2 targetPosition;
    boolean hitTargetPosition;
    int speed = 2;
    public boolean alert; //health = 0

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
        this.hunger = 0;
        this.health = 0;
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

        if (!sleeping && !alert) {
            tired += deltaTime;
            if (tired >= 50) {
                sleep();
            }
            happiness -= deltaTime * 3;
            hunger += deltaTime * 5;

            if(hunger >= 75 || happiness <= 20){
                health -= deltaTime*0.1;
            }
            if(hunger >= 100 || happiness <= 0){
                health -= deltaTime*0.9;
            }
        }


        happiness = clampFloat(happiness, 0, 100);
        health = clampFloat(health, 0, 100);
        hunger = clampFloat(hunger, 0, 100);

        if (sleeping && tired != 0) {
            tired -= deltaTime * 2;

            if (tired <= 0) {
                sleeping = false;
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

//            increaseHealth();
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

    private boolean checkMove(){
        return happiness >= Math.random() * 100;
    }
    private void genTargetPosition() {
        double targetX = Math.random() * room.width + room.x;
        double targetY = Math.random() * room.height + room.y;
        targetPosition = new Vector2((float) targetX, (float) targetY);
    }
    public boolean randomTick(){
        boolean passed = (int) (Math.random() * 1000) < Math.floor(level);
        if(sleeping && passed) {
            tired -= 1.0;
        }
        return passed;

    }
    public void sleep(){
        //set animation to sleeping animation
        sleeping = true;
    }

    public void increaseHealth(){
        health += 10;
    }

    public void eat(){
        this.hunger -= 20;
        this.hunger = clampFloat(hunger, 0, 100);
    }
    public void pet(){
        this.happiness += 5;
        this.happiness = clampFloat(happiness, 0, 100);
    }
    //happiness function?

    public static float clampFloat(float val, float min, float max){
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
                "Elwood"
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
