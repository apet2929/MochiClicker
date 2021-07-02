package com.moonjew.mochiclicker.io;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MenuButton extends Button{
    private boolean menu;
    public MenuButton(String text, Rectangle bounds) {
        super(text, bounds);
    }

    @Override
    public void onclick() {

    }
    @Override
    public void render(SpriteBatch sb){
        if(menu){
            super.render(sb);
        }
    }

    public void setMenu(boolean menu){
        this.menu = menu;
    }
}
