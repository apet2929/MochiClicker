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

    public Room(Color color, GameStateManager gsm) {
        this.rectangle = new Rectangle(20,20,MochiClicker.WIDTH-40, MochiClicker.HEIGHT-40);
        this.color = color;
        this.shop = new ShopState(gsm, cat);
        this.cat = genCat();
    }

    public void update(float deltaTime, boolean focused){
        if(cat.getHealth() == 0){
            cat.alert = true;
        }

        if(focused) {
            cat.update(deltaTime);
        } else {
            if(cat.randomTick()) {
                System.out.println("True!! " + cat.toString());
                catNip+= cat.getLevel();
            }
        }
    }

    public void killCat(){
        this.cat = null;
        this.cat = genCat();
        this.shop.restart(this.cat);

    }

    public Cat genCat(){
        String name = Cat.randomName();
        Texture catTexture;
        if(name.equals("Paige")) {
            catTexture = new Texture("paige.png");
        } else catTexture = new Texture("elwood.png");
        Rectangle catFloor = new Rectangle(rectangle.x + rectangle.width/3, rectangle.y, rectangle.width*2/3, rectangle.height/2);

        return new Cat(name, catTexture, 0,0, 100, 70, catFloor);
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
