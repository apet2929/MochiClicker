package com.moonjew.mochiclicker.io;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;


public class Meter {
    Texture texture;
    Color color;
    Rectangle fillRect;

    public Meter(Texture texture, Color color, Rectangle fillRect) {
        this.texture = texture;
        this.color = color;
        this.fillRect = fillRect;
    }
}
