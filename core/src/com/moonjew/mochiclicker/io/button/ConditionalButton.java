package com.moonjew.mochiclicker.io.button;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.io.button.Button;

import static com.moonjew.mochiclicker.MochiClicker.FONT;

public class ConditionalButton extends Button
{
    public boolean willRender;
    public ConditionalButton(String text, Rectangle bounds) {
        super(text, bounds);
    }
    public ConditionalButton(Texture texture, Rectangle bounds) {
        super(texture, bounds);
    }

    @Override
    public void render(SpriteBatch sb) {
        if(willRender && text != null) {
            FONT.draw(sb, text, bounds, 2, 2);
        } else if(willRender && texture != null){
            sb.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    @Override
    public void onclick() {

    }
}
