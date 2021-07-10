package com.moonjew.mochiclicker.io.button;

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

    @Override
    public void render(SpriteBatch sb) {
        if(willRender) {
            FONT.draw(sb, text, bounds, 2, 2);
        }
    }

    @Override
    public void onclick() {

    }
}
