package com.moonjew.mochiclicker.io;

import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.Upgrade;

public class UpgradeButton extends Button{
    Upgrade upgrade;
    public UpgradeButton(Rectangle bounds,  Upgrade upgrade) {
        super((String) null, bounds);
        this.upgrade = upgrade;
    }

    public Upgrade getUpgrade() {
        return upgrade;
    }

    @Override
    public void onclick() {

    }
}