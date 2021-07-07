package com.moonjew.mochiclicker.upgrades;

import com.moonjew.mochiclicker.entities.Cat;
import com.moonjew.mochiclicker.io.UpgradeButton;
import com.moonjew.mochiclicker.room.Room;
import com.moonjew.mochiclicker.state.PlayState;

import java.util.Arrays;

public class UpgradeTree {

    public UpgradeType upgradeType;
    private int nextUpgrade;
    private Upgrade currentlyInUse;
    public final Upgrade[] upgrades;
    public boolean[] purchased;

    public UpgradeTree(Upgrade[] line, UpgradeType type) {
        this.upgrades = line;
        this.upgradeType = type;
        purchased = new boolean[upgrades.length];
        Arrays.fill(purchased, false);
    }

    public boolean purchased(Upgrade upgrade){
        int u = getUpgrade(upgrade);
        if(u != -1) {
            return purchased[getUpgrade(upgrade)];
        } else return false;
    }

    public boolean buyNext(Cat cat, UpgradeButton upgradeButton) {
        if(nextUpgrade < upgrades.length) {
            if (upgrades[nextUpgrade].COST <= PlayState.catNip) {
                PlayState.catNip -= upgrades[nextUpgrade].COST;
                purchased[nextUpgrade] = true;
                setCurrentlyInUse(upgrades[nextUpgrade]);

                cat.levelUp();
                float val = upgrades[nextUpgrade].VALUE;
                switch(this.upgradeType){
                    case HEALTH:
                        cat.maxHealth += val;
                        break;
                    case HUNGER:
                        cat.maxHunger += val;
                        break;
                    case SLEEP:
                        cat.maxTired += val;
                        break;
                    case HAPPINESS:
                        cat.maxHappiness += val;
                        break;
                }

                nextUpgrade++;
                upgradeButton.setUpgrade(getNextUpgrade());

                return true;
            } else {
                System.out.println("Not enough catnip");
            }
        } else {
            System.out.println("No more upgrades in this tree");
        }
        return false;
    }

    public void buyNext(Room room, UpgradeButton upgradeButton){
        if(nextUpgrade < upgrades.length) {
            if (upgrades[nextUpgrade].COST <= PlayState.catNip) {
                PlayState.catNip -= upgrades[nextUpgrade].COST;
                purchased[nextUpgrade] = true;
                setCurrentlyInUse(upgrades[nextUpgrade]);
                room.setDecoration(getNextUpgrade().DECORATION);

                nextUpgrade++;
                upgradeButton.setUpgrade(getNextUpgrade());
            }
        }
    }

    public void setCurrentlyInUse(Upgrade upgrade) {
        currentlyInUse = upgrade;
    }

    public Upgrade getCurrentUpgrade() {
        if(currentlyInUse != null){
            return currentlyInUse;
        } else System.out.println("Current upgrade not defined");
        return null;
    }

    public Upgrade getNextUpgrade() {
        if(nextUpgrade < upgrades.length) {
            return upgrades[nextUpgrade];
        }
        System.out.println("Next upgrade is out of bounds");
        return null;
    }

    public int getUpgrade(Upgrade upgrade) {
        for (int i = 0; i < upgrades.length; i++) {
            if(upgrade.equals(upgrades[i])){
                return i;
            }
        }
        return -1;
    }

}
