package com.moonjew.mochiclicker.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.moonjew.mochiclicker.io.Animation;

import java.util.ArrayList;
import java.util.List;

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

    public Cat(Texture sourceTexture, int x, int y, int width, int height, Rectangle room) {
        this.sourceTexture = sourceTexture;
        TextureRegion src = new TextureRegion(sourceTexture, 400, 42);
        this.texture = new Animation(src, 5, 0.6f);
        this.position = new Rectangle(x + room.x, y + room.y, width, height);
        this.velocity = new Vector2(1, 1);
        this.room = room;
        this.level = 1;
        this.happiness = 100;
        this.sleeping = false;
        this.tired = 0;
        this.hunger = 0;
        this.name = randomName();
        usedNames.add(this.name);
    }

    public void update(float deltaTime){
        texture.update(deltaTime);
        //update animation

        //movement
        if(!sleeping) {
            position.x += velocity.x * deltaTime * happiness;
            position.y += velocity.y * deltaTime * happiness;
        }

        //clamp position inside of room, reverse direction if collide with walls
        if((position.x + position.width) > room.width){
            velocity.x *= -1;
            position.x = room.width - position.width;
        } else if (position.x < room.x){
            velocity.x *= -1;
            position.x  = room.x;
        }
        if((position.y + position.height) > room.height){
            velocity.y *= -1;
            position.y = room.height - position.height;
        } else if (position.y < room.y) {
            velocity.y *= -1;
            position.y  = room.y;
        }

        //handle state

        if (!sleeping) {
            tired += deltaTime;
            if (tired >= 10) {
                sleeping = true;
            }
            happiness -= deltaTime * 10;
            health -= deltaTime * 0.01f;
            hunger += deltaTime * 5;
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

    public boolean randomTick(){
        boolean passed = (int) (Math.random() * 1000) < Math.floor(level);
        if(sleeping && passed) {
            tired -= 1.0;
        }
        return passed;

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
