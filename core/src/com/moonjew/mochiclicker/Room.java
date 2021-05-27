package com.moonjew.mochiclicker;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

public abstract class Room {
    HashMap<String, Integer> objects;

    public void render(SpriteBatch batch){
        //render all objects here
    }


}
