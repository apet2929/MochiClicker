package com.moonjew.mochiclicker;

public class Upgrade {
    public final String ID;
    public final int COST;
    public final String DESCRIPTION;

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


    //upgrade trees
    public static final Upgrade[] TEST_UPGRADES = new Upgrade[]{UPGRADES[0], UPGRADES[1], UPGRADES[2], UPGRADES[3]};
    public static final Upgrade[] FOOD_UPGRADES = new Upgrade[]{UPGRADES[4], UPGRADES[5], UPGRADES[6], UPGRADES[7]};


    public Upgrade(String ID, int COST, String DESCRIPTION) {
        this.ID = ID;
        this.COST = COST;
        this.DESCRIPTION = DESCRIPTION;
    }

    private static Upgrade[] genUpgrades(){
        return new Upgrade[]{
                new Upgrade("TEST", 10, "Test upgrade"),
                new Upgrade("TEST", 20, "Test 2"),
                new Upgrade("TEST", 30, "Test 3"),
                new Upgrade("TEST", 40, "Test 4"),

                new Upgrade("FOOD", 10, "Food 1"),
                new Upgrade("FOOD", 20, "Food 2"),
                new Upgrade("FOOD", 30, "Food 3"),
                new Upgrade("FOOD", 40, "Food 4"),

        };
    }

}