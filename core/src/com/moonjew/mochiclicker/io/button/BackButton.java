package com.moonjew.mochiclicker.io.button;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.state.GameStateManager;

public class BackButton extends Button {
    private GameStateManager gsm;
    public BackButton(Texture texture, Rectangle bounds, GameStateManager gsm) {
        super(texture, bounds);
        this.gsm = gsm;
    }

    @Override
    public void onclick() {
        gsm.pop();
    }
}
