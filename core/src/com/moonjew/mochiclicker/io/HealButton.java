package com.moonjew.mochiclicker.io;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.io.FileOutputStream;

import static com.moonjew.mochiclicker.MochiClicker.FONT;

public class HealButton extends Button
{
    public boolean willRender;
    public HealButton(String text, Rectangle bounds) {
        super(text, bounds);
    }

    @Override
    public void render(SpriteBatch sb) {
        if(willRender) {
//            super.renderBackgrounds(sb);
//            System.out.println("True");
            FONT.draw(sb, text, bounds, 2, 2);
        }
    }

    @Override
    public void onclick() {

    }
}
