package com.moonjew.mochiclicker.io;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.MochiClicker;

public class MenuButton extends Button{
    boolean menu;
    public MenuButton(Rectangle bounds) {
        super(new Texture("menu-button.png"), bounds);
        menu = false;
    }

    public void reset(){
        this.getBounds().x = MochiClicker.WIDTH*0.9f;
        this.getBounds().y = MochiClicker.HEIGHT-64;
    }

    @Override
    public void onclick() {
        menu = !menu;
        if(menu) {
            this.getBounds().x = MochiClicker.WIDTH-MochiClicker.WIDTH*0.75f;
        }
        else {
            reset();
        }
    }
}
