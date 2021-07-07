package com.moonjew.mochiclicker.room;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Decoration {
    private final Texture texture;
    private final float value; //What the decoration actually does: increases some value to help the player
    private final DecorationType type;

    public Decoration(Texture texture, float value, DecorationType type) {
        this.texture = texture;
        this.value = value;
        this.type = type;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getValue() {
        return value;
    }

    public DecorationType getType() {
        return type;
    }

    public enum DecorationType {
        BED, // Cat goes here when it sleeps, better bed increases how long cat stays awake
        TREE, // Cat can exercise on it, increases health
        WINDOW, // Increased scouting ability, cat has a greater chance to return with something after going outside
        CARPET, // Cat less likely to scream and complain
        PAINTING, // Cat has an appreciation for the fine arts, becomes happy looking at it
        FOOD_WATER_BOWL, // Stay fuller longer
        LITTER_BOX, // Increases health
        SPECIAL
    }
}
