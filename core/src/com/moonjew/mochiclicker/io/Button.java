package com.moonjew.mochiclicker.io;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Button {
    private Rectangle bounds;
    private String text;
    private Font font;
    private Texture texture;
    public Button(String text, Rectangle bounds, Font font) {
        this.text = text;
        this.bounds = bounds;
        this.font = new Font();

    }
    public Button(Texture texture, Rectangle bounds){
        this.texture = texture;
        this.bounds = bounds;
    }

    public void render(SpriteBatch sb){
        if(texture != null){
            sb.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
        }
        if(text != null){
            font.draw(sb, text, bounds, 2, 2);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public abstract void onclick();

}
