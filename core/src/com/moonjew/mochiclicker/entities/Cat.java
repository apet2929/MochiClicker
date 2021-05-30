package com.moonjew.mochiclicker.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.moonjew.mochiclicker.MochiClicker;

public class Cat {
    Texture texture; //change to animation when ready
    Rectangle position;
    Vector2 velocity; //temp variable
    Rectangle room;
    int happiness;
    boolean sleeping;
    double sleep;
    double tired;
    int health;
    double level;

    public Cat(Texture texture, int x, int y, int width, int height, Rectangle room) {
        this.texture = texture;
        this.position = new Rectangle(x + room.x, y + room.y, width, height);
        this.velocity = new Vector2(100, 100);
        this.room = room;
    }

    public void update(float deltaTime){
        //update animation
        if(!sleeping) {
            position.x += velocity.x * deltaTime;
            position.y += velocity.y * deltaTime;
        }
        if((position.x + position.width) > room.width || position.x < room.x){
            velocity.x *= -1;
        }
        if((position.y + position.height) > room.height || position.y < room.y){
            velocity.y *= -1;
        }

        if (!sleeping) {
            tired += deltaTime;
            if (tired >= 50) {
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

    public double getTired() {
        return tired;
    }

    public boolean isSleeping() {
        return sleeping;
    }

    public Texture getTexture(){
        return texture;
    }
    public Rectangle getPosition(){
        return position;
    }

}
