package com.moonjew.mochiclicker.upgrades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.moonjew.mochiclicker.room.Decoration;

public class Upgrade {
    public final int COST;
    public final String DESCRIPTION;
    public final UpgradeType UPGRADE_TYPE;
    public final Decoration DECORATION;
    public final int VALUE;

    //upgrades
    public static final Upgrade[] UPGRADES = genUpgrades();
    public static final Upgrade TEST = UPGRADES[0];
    public static final Upgrade TEST2 = UPGRADES[1];
    public static final Upgrade TEST3 = UPGRADES[2];
    public static final Upgrade TEST4 = UPGRADES[3];

    public static final Upgrade FOOD = UPGRADES[4];
    public static final Upgrade FOOD2 = UPGRADES[5];
    public static final Upgrade FOOD3 = UPGRADES[6];
    public static final Upgrade FOOD4 = UPGRADES[7];

    public static final int MAX_LEVEL = UPGRADES.length;

    //upgrade trees
    public static final Upgrade[] TEST_UPGRADES = new Upgrade[]{UPGRADES[0], UPGRADES[1], UPGRADES[2], UPGRADES[3]};
    public static final Upgrade[] FOOD_UPGRADES = new Upgrade[]{UPGRADES[4], UPGRADES[5], UPGRADES[6], UPGRADES[7]};
    public static final Upgrade[] HEALTH_UPGRADES = new Upgrade[]{UPGRADES[8], UPGRADES[9], UPGRADES[10], UPGRADES[11]};
    public static final Upgrade[] HAPPINESS_UPGRADES = new Upgrade[]{UPGRADES[12], UPGRADES[13], UPGRADES[14], UPGRADES[15]};
    public static final Upgrade[] SLEEP_UPGRADES = new Upgrade[]{UPGRADES[16], UPGRADES[17], UPGRADES[18], UPGRADES[19]};

    public static final Upgrade[] BED_UPGRADES = new Upgrade[]{UPGRADES[20]};
    public Upgrade(UpgradeType UPGRADE_TYPE, int COST, String DESCRIPTION) {
        this.UPGRADE_TYPE = UPGRADE_TYPE;
        this.COST = COST;
        this.DESCRIPTION = DESCRIPTION;
        this.VALUE = -1;
        this.DECORATION = null;
    }
    public Upgrade(UpgradeType UPGRADE_TYPE, int COST, String DESCRIPTION, int VALUE){
        this.UPGRADE_TYPE = UPGRADE_TYPE;
        this.COST = COST;
        this.DESCRIPTION = DESCRIPTION;
        this.VALUE = VALUE;
        this.DECORATION = null;
    }
    public Upgrade(UpgradeType UPGRADE_TYPE, int COST, String DESCRIPTION, Decoration DECORATION){
        this.UPGRADE_TYPE = UPGRADE_TYPE;
        this.COST = COST;
        this.DESCRIPTION = DESCRIPTION;
        this.VALUE = -1;
        this.DECORATION = DECORATION;
    }

    private static Upgrade[] genUpgrades(){
        return new Upgrade[]{
                new Upgrade(UpgradeType.TEST, 10, "Test upgrade"),
                new Upgrade(UpgradeType.TEST, 20, "Test 2"),
                new Upgrade(UpgradeType.TEST, 30, "Test 3"),
                new Upgrade(UpgradeType.TEST, 40, "Test 4"),

                new Upgrade(UpgradeType.HUNGER, 10, "Food 1", 5),
                new Upgrade(UpgradeType.HUNGER, 20, "Food 2", 10),
                new Upgrade(UpgradeType.HUNGER, 30, "Food 3", 15),
                new Upgrade(UpgradeType.HUNGER, 40, "Food 4", 20),

                new Upgrade(UpgradeType.HEALTH, 10, "Health 1", 5),
                new Upgrade(UpgradeType.HEALTH, 20, "Health 2", 10),
                new Upgrade(UpgradeType.HEALTH, 30, "Health 3", 15),
                new Upgrade(UpgradeType.HEALTH, 40, "Health 4", 20),

                new Upgrade(UpgradeType.HAPPINESS, 10, "Happy 1", 5),
                new Upgrade(UpgradeType.HAPPINESS, 20, "Happy 2", 10),
                new Upgrade(UpgradeType.HAPPINESS, 30, "Happy 3", 15),
                new Upgrade(UpgradeType.HAPPINESS, 40, "Happy 4", 20),

                new Upgrade(UpgradeType.SLEEP, 10, "Sleep 1", 5),
                new Upgrade(UpgradeType.SLEEP, 20, "Sleep 2", 10),
                new Upgrade(UpgradeType.SLEEP, 30, "Sleep 3", 15),
                new Upgrade(UpgradeType.SLEEP, 40, "Sleep 4", 20),

                new Upgrade(UpgradeType.BED, 5, "Get a good nights rest", new Decoration(new Texture(Gdx.files.internal("testcat.jpg")), 20, Decoration.DecorationType.BED, 64, 64))
        };
    }

}