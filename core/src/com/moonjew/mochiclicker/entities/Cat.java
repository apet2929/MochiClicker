package com.moonjew.mochiclicker.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Cat {
    Texture texture; //change to animation when ready
    Rectangle position;

    public Cat(Texture texture, int x, int y, int width, int height) {
        this.texture = texture;
        this.position = new Rectangle(x, y, width, height);
    }

    public void update(float deltaTime){
        //update animation
        position.x += 50 * deltaTime;
    }
    public Texture getTexture(){
        return texture;
    }
    public Rectangle getPosition(){
        return position;
    }

}
