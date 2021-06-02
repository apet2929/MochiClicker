package com.moonjew.mochiclicker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.entities.Cat;
import com.moonjew.mochiclicker.state.GameStateManager;
import com.moonjew.mochiclicker.state.ShopState;

import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    Cat cat;
    Rectangle rectangle;
    Color color;
    boolean focused;
    ShopState shop;

    public Room(Color color, GameStateManager gsm) {
        this.rectangle = new Rectangle(20,20,MochiClicker.WIDTH-40, MochiClicker.HEIGHT-40);
        this.cat = new Cat(new Texture("testcat.jpg"), 0,0, 100, 100, rectangle);
        this.color = color;
        this.shop = new ShopState(gsm);
        this.shop.buyUpgrade(Upgrade.TEST);
    }

    public boolean hasUpgrade(Upgrade upgrade){
        return shop.hasUpgrade(upgrade);
    }
    public ShopState getShop(){
        return shop;
    }
    public void update(float deltaTime, boolean focused){
        cat.update(deltaTime);
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
