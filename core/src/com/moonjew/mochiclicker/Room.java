package com.moonjew.mochiclicker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.entities.Cat;

import java.util.HashMap;

public class Room {
    Cat cat;
    Rectangle rectangle;
    Color color;
    boolean focused;

    public Room(Color color) {
        this.rectangle = new Rectangle(20,20,MochiClicker.WIDTH-40, MochiClicker.HEIGHT-40);
        this.cat = new Cat(new Texture("testcat.jpg"), 0,0, 100, 100, rectangle);
        this.color = color;
    }

    public void update(float deltaTime){
        cat.update(deltaTime);
    }
    public Color getColor(){
        return color;
    }
    public Rectangle getRectangle(){
        return rectangle;
    }
    public Cat getCat(){
        return cat;
    }
}
