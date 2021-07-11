package com.moonjew.mochiclicker.io;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;


public class Meter {
    Texture texture;
    Color color;
    Rectangle fillRect;
    Rectangle textureRect;
    public int fillHeight;

    public Meter(Texture texture, Color color, Rectangle textureRect, Rectangle fillRect) {
        this.texture = texture;
        this.color = color;
        this.fillRect = fillRect;
        this.textureRect = textureRect;
    }

    public void render(SpriteBatch sb, ShapeRenderer sr){
        sr.setColor(color);
        sr.rect(fillRect.x, fillRect.y, fillRect.width, fillHeight);
        sb.draw(texture, textureRect.x, textureRect.y, textureRect.width, textureRect.height);
    }
}
