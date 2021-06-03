package com.moonjew.mochiclicker.io;

import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.Upgrade;
import com.moonjew.mochiclicker.entities.Cat;
import com.moonjew.mochiclicker.state.GameStateManager;
import com.moonjew.mochiclicker.state.ShopState;

public class UpgradeButton extends Button{
    Upgrade upgrade;
    public UpgradeButton(Rectangle bounds, Font font, Upgrade upgrade) {
        super("Shop", bounds, font);
        this.upgrade = upgrade;
    }

    public Upgrade getUpgrade() {
        return upgrade;
    }


    @Override
    public void onclick() {

    }
}
