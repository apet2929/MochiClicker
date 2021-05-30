package com.moonjew.mochiclicker.io;

import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.state.GameStateManager;
import com.moonjew.mochiclicker.state.ShopState;

public class ShopButton extends Button{
    private GameStateManager gsm;
    public ShopButton(Rectangle bounds, Font font, GameStateManager gsm) {
        super("Shop", bounds, font);
        this.gsm = gsm;
    }

    @Override
    public void onclick() {
        gsm.push(new ShopState(gsm));
    }

}
