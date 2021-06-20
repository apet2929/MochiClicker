package com.moonjew.mochiclicker;

import com.badlogic.gdx.Gdx;
import com.moonjew.mochiclicker.state.PlayState;
import java.util.Arrays;

public class UpgradeTree {
    private int currentUpgrade;
    private Upgrade currentlyInUse;
    public final Upgrade[] upgrades;
    public boolean[] purchased;

    public UpgradeTree(Upgrade[] line){
        this.upgrades = line;
        purchased = new boolean[upgrades.length];
        Arrays.fill(purchased, false);
    }

    public boolean purchased(Upgrade upgrade){
        return purchased[getUpgrade(upgrade)];
    }

    public void buyNext(){
        currentUpgrade++;
        PlayState.catNip -= upgrades[currentUpgrade].COST;
        purchased[currentUpgrade] = true;
        setCurrentlyInUse(upgrades[currentUpgrade]);
    }

    public void setCurrentlyInUse(Upgrade upgrade){
        currentlyInUse = upgrade;
    }

    public Upgrade getCurrentUpgrade() {
        return currentlyInUse;
    }

    public int getUpgrade(Upgrade upgrade){
        for (int i = 0; i < upgrades.length; i++) {
            if(upgrade.equals(upgrades[i])){
                return i;
            }
        }
        Gdx.app.debug("BUG", "Upgrade of " + upgrade.toString() + " is not valid");
        throw new IllegalArgumentException();
    }

}
