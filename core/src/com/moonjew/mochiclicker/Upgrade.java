package com.moonjew.mochiclicker;

public class Upgrade {
    public final String ID;
    public final int COST;
    public final String DESCRIPTION;

    //upgrades

    public static final Upgrade[] UPGRADES = genUpgrades();
    public static final Upgrade TEST = UPGRADES[0];


    public Upgrade(String ID, int COST, String DESCRIPTION) {
        this.ID = ID;
        this.COST = COST;
        this.DESCRIPTION = DESCRIPTION;
    }

    private static Upgrade[] genUpgrades(){
        return new Upgrade[]{
                new Upgrade("TEST", 10, "Test upgrade"),

        };
    }
}
