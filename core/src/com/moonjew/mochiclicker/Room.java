package com.moonjew.mochiclicker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.entities.Cat;
import com.moonjew.mochiclicker.state.GameStateManager;
import com.moonjew.mochiclicker.state.PlayState;
import com.moonjew.mochiclicker.state.ShopState;

import static com.moonjew.mochiclicker.state.PlayState.catNip;

public class Room {
    Cat cat;
    Rectangle rectangle;
    Color color;
    ShopState shop;
    UpgradeTree testTree;

    public Room(Color color, GameStateManager gsm) {
        this.rectangle = new Rectangle(20,20,MochiClicker.WIDTH-40, MochiClicker.HEIGHT-40);
        String name = Cat.randomName();
        Texture catTexture;
        if(name.equals("Paige")){
            catTexture = new Texture("paige.png");
        } else catTexture = new Texture("elwood.png");
        Rectangle catFloor = new Rectangle(rectangle.x + rectangle.width/3, rectangle.y, rectangle.width*2/3, rectangle.height/2);
        this.cat = new Cat(name, catTexture, 0,0, 100, 70, catFloor);
        this.color = color;
        this.shop = new ShopState(gsm, cat);
    }

    public void update(float deltaTime, boolean focused){
        if(focused) {
            cat.update(deltaTime);
        } else {
            if(cat.randomTick()) {
                System.out.println("True!! " + cat.toString());
                catNip+= cat.getLevel();
            }
        }
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
