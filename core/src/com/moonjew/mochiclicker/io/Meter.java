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

    public Meter(Texture texture, Color color, Rectangle textureRect, Rectangle fillRect) {
        this.texture = texture;
        this.color = color;
        this.fillRect = fillRect;
        this.textureRect = textureRect;
    }

    public void fillMeter(ShapeRenderer sr, int fillHeight){
        sr.setColor(color);
        sr.set(ShapeRenderer.ShapeType.Filled);
        sr.rect(textureRect.x + fillRect.x, textureRect.y + fillRect.y, fillRect.width, fillHeight);
        sr.set(ShapeRenderer.ShapeType.Line);
    }
    public void render(SpriteBatch sb){
        sb.draw(texture, textureRect.x, textureRect.y, textureRect.width, textureRect.height);
    }
}
