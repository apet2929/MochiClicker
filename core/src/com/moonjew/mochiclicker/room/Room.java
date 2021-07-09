package com.moonjew.mochiclicker.room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.moonjew.mochiclicker.MochiClicker;
import com.moonjew.mochiclicker.entities.Cat;
import com.moonjew.mochiclicker.entities.CatState;
import com.moonjew.mochiclicker.state.GameStateManager;
import com.moonjew.mochiclicker.state.ShopState;
import com.moonjew.mochiclicker.upgrades.Upgrade;

import static com.moonjew.mochiclicker.state.PlayState.catNip;

public class Room {

    int roomTexture;
    Cat cat;
    Rectangle rectangle;
    Color color;
    ShopState shop;
    Decoration[] decorations;

    public static final Vector2[] decorationPositions = {
            new Vector2(MochiClicker.WIDTH*0.8f, 10), // Bed
            new Vector2(MochiClicker.WIDTH/5, MochiClicker.HEIGHT/2.5f), // Tree
            new Vector2(MochiClicker.WIDTH/4, MochiClicker.HEIGHT*0.6f), // Window
            new Vector2(MochiClicker.WIDTH/2 - 192, MochiClicker.HEIGHT/2 - 384), // Carpet
            new Vector2(MochiClicker.WIDTH/24, MochiClicker.HEIGHT/2), // Painting
            new Vector2(MochiClicker.WIDTH*0.01f, MochiClicker.HEIGHT*0.05f), // Food/Water Bowl
            new Vector2(MochiClicker.WIDTH*0.65f, MochiClicker.HEIGHT/2.5f), // Litter Box
            new Vector2(MochiClicker.WIDTH/2-120, MochiClicker.HEIGHT/3) // Special
    };

    public Room(GameStateManager gsm) {
        this.rectangle = new Rectangle(20,20, MochiClicker.WIDTH-40, MochiClicker.HEIGHT-40);
        this.cat = genCat();
        this.shop = new ShopState(gsm, this);
        this.roomTexture = 0;
        initDecorations();
    }

    public Room(){
        this.rectangle = new Rectangle(20,20,MochiClicker.WIDTH-40, MochiClicker.HEIGHT-40);
        this.cat = null;
        this.shop = null;
    }

    public void update(float deltaTime){
        if(cat != null) {
            if (cat.getHealth() == 0) {
                cat.setState(new CatState(CatState.CatStateType.DYING));
            }
            cat.update(deltaTime);
        }
    }

    public void renderDecorations(SpriteBatch sb, Camera cam){
        for(Decoration decoration : decorations){
            if(decoration != null){
                sb.draw(decoration.getTexture(), rectangle.x + cam.position.x + Room.decorationPositions[decoration.getType().ordinal()].x,
                        rectangle.y + cam.position.y + Room.decorationPositions[decoration.getType().ordinal()].y,
                        decoration.width, decoration.height);
            }
        }
    }

    void initDecorations(){
        decorations = new Decoration[Decoration.DecorationType.values().length];
        decorations[Decoration.DecorationType.BED.ordinal()] = new Decoration(new Texture(Gdx.files.internal("pumpkin_bed.png")), 5, Decoration.DecorationType.BED, 96, 96);
        decorations[Decoration.DecorationType.TREE.ordinal()] = new Decoration(new Texture(Gdx.files.internal("cat-tree.png")), 5, Decoration.DecorationType.TREE, 96, 192);
        decorations[Decoration.DecorationType.WINDOW.ordinal()] = new Decoration(new Texture(Gdx.files.internal("window.png")), 5, Decoration.DecorationType.WINDOW, 128, 128);
        decorations[Decoration.DecorationType.CARPET.ordinal()] = new Decoration(new Texture(Gdx.files.internal("carpet.png")), 5, Decoration.DecorationType.CARPET, 384, 384);
        decorations[Decoration.DecorationType.PAINTING.ordinal()] = new Decoration(new Texture(Gdx.files.internal("painting.png")), 5, Decoration.DecorationType.PAINTING, 96, 96);
        decorations[Decoration.DecorationType.FOOD_WATER_BOWL.ordinal()] = new Decoration(new Texture(Gdx.files.internal("food_bowl.png")), 5, Decoration.DecorationType.FOOD_WATER_BOWL, 48, 48);
        decorations[Decoration.DecorationType.LITTER_BOX.ordinal()] = new Decoration(new Texture(Gdx.files.internal("litterbox.png")), 5, Decoration.DecorationType.LITTER_BOX, 96, 96);
        decorations[Decoration.DecorationType.SPECIAL.ordinal()] = new Decoration(new Texture(Gdx.files.internal("mouse_toy.png")), 5, Decoration.DecorationType.SPECIAL, 96, 96);
    }

    public void setDecoration(Decoration decoration){
        decorations[decoration.getType().ordinal()] = decoration;
    }

    public void killCat(){
        this.cat = null;
        this.cat = genCat();
        this.shop.restart();
    }

    public Cat genCat(){
        String name = Cat.randomName();
        Texture catTexture;
        try {
            catTexture = new Texture(name + ".png");
        } catch (GdxRuntimeException e) {
            catTexture = new Texture("Paige.png");
        }

        return new Cat(name, catTexture, 0,0, 100, 70, this);
    }

    public float getDecoration(Decoration.DecorationType type){
        return decorations[type.ordinal()].getValue();
    }

    public Decoration[] getDecorations(){
        return decorations;
    }
    public boolean hasUpgrade(Upgrade upgrade){
        return shop.hasUpgrade(upgrade);
    }
    public ShopState getShop(){
        return shop;
    }
    public Color getColor(){
        return color;
    }
    public Rectangle getRectangle(){
        return rectangle;
    }
    public Cat getCat(){
        return cat;
    }
    public void dispose(){
        cat.dispose();
    }

}
