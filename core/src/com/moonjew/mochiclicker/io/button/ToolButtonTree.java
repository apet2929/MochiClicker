package com.moonjew.mochiclicker.io.button;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.state.ToolType;

public class ToolButtonTree {
    ToolType type;
    boolean focused;
    Button mainButton;
    ConditionalButton[] buttons;

    public ToolButtonTree(ToolType type, Button mainButton, ConditionalButton[] buttons) {
        this.type = type;
        this.mainButton = mainButton;
        this.buttons = buttons;
    }

    public ToolType onclick(float x, float y){
        if(mainButton.getBounds().contains(x,y)){
            focused = !focused;
            for(ConditionalButton button : buttons){
                button.willRender = focused;
            }
            return ToolType.NO_TOOL;
        } else if(focused) {
            for(int i = 0; i < buttons.length; i++){
                if(buttons[i].getBounds().contains(x,y)) return ToolType.values()[type.ordinal() + i];
            }
        }
        return null;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public boolean isFocused() {
        return focused;
    }

    public Button getMainButton() {
        return mainButton;
    }

    public Button[] getButtons() {
        return buttons;
    }
}
