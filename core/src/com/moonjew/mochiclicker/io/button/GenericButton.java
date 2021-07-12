package com.moonjew.mochiclicker.io.button;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.io.button.Button;

public class GenericButton extends Button {

    public GenericButton(Texture texture, Rectangle bounds) {
        super(texture, bounds);
    }
    public GenericButton(String text, Rectangle bounds){
        super(text, bounds);
    }

    @Override
    public void onclick() {

    }
}
