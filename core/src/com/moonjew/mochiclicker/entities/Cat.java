package com.moonjew.mochiclicker.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.moonjew.mochiclicker.MochiClicker;
import com.moonjew.mochiclicker.Upgrade;
import com.moonjew.mochiclicker.io.Animation;

import java.util.ArrayList;
import java.util.List;

public class Cat {
    private static List<String> usedNames = new ArrayList<>();
    Animation texture; //change to animation when ready
    Texture sourceTexture;
    Rectangle position;
    Vector2 velocity; //temp variable
    Rectangle room;
    String name;
    int happiness;
    boolean sleeping;
    double sleep;
    double tired;
    int health;
    double level;

    public Cat(Texture sourceTexture, int x, int y, int width, int height, Rectangle room) {
        this.sourceTexture = sourceTexture;
        TextureRegion src = new TextureRegion(sourceTexture, 388, 42);
        this.texture = new Animation(src, 5, 2.0f);
        this.position = new Rectangle(x + room.x, y + room.y, width, height);
        this.velocity = new Vector2(100, 100);
        this.room = room;
        this.level = 1;
        this.happiness = 1;
        this.sleeping = false;
        this.tired = 0;
        this.name = randomName();
        usedNames.add(this.name);
    }

    public void update(float deltaTime){
        texture.update(deltaTime);
        //update animation
        if(!sleeping) {
            position.x += velocity.x * deltaTime;
            position.y += velocity.y * deltaTime;
        }
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

        if (!sleeping) {
            tired += deltaTime;
            if (tired >= 10) {
                sleeping = true;
            }
        }

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

    public String getName() {
        return name;
    }

    public int getHappiness() {
        return happiness;
    }
    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
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
