package com.moonjew.mochiclicker.io.button;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import static com.moonjew.mochiclicker.MochiClicker.FONT;

public class Button {
    Rectangle bounds;
    String text;
    Texture texture;
    public Button(String text, Rectangle bounds) {
        this.text = text;
        this.bounds = bounds;
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
            FONT.drawMiddle(sb, text, bounds.x, bounds.y, bounds.width, bounds.width*0.35f, 2, 2);
        }
    }
    public void resize(float x, float y, float width, float height){
        bounds = new Rectangle(x, y, width, height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public String getText() {
        return text;
    }

    public void onclick(){

    }


}
