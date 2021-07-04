package com.moonjew.mochiclicker.io;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.upgrades.Upgrade;

import static com.moonjew.mochiclicker.MochiClicker.FONT;

public class UpgradeButton extends Button{
    Upgrade upgrade;
    public UpgradeButton(Rectangle bounds,  Upgrade upgrade) {
        super((String) null, bounds);
        this.upgrade = upgrade;
    }
    public UpgradeButton(Rectangle bounds){
        super((String) null, bounds);
        this.upgrade = null;
    }

    public void setUpgrade(Upgrade upgrade) {
        this.upgrade = upgrade;
    }

    public Upgrade getUpgrade() {
        return upgrade;
    }

    @Override
    public void onclick() {

    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if(upgrade != null) {
            FONT.drawMiddle(sb, upgrade.DESCRIPTION + " COST " + upgrade.COST, bounds, 2, 2);
        }
    }

    public void renderOutline(ShapeRenderer sr){
        sr.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}