package com.moonjew.mochiclicker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.moonjew.mochiclicker.entities.Cat;
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

    public Room(GameStateManager gsm) {
        this.rectangle = new Rectangle(20,20,MochiClicker.WIDTH-40, MochiClicker.HEIGHT-40);
        this.cat = genCat();
        this.shop = new ShopState(gsm, this);
        this.roomTexture = 0;
    }

    public Room(){
        this.rectangle = new Rectangle(20,20,MochiClicker.WIDTH-40, MochiClicker.HEIGHT-40);
        this.cat = null;
        this.shop = null;
    }

    public void update(float deltaTime){
        if(cat.getHealth() == 0){
            cat.alert = true;
        }
        float temp = cat.outsideTimer;

        cat.update(deltaTime); //cat update

        if(temp != -1 && cat.outsideTimer == -1){ //cat returned from outside
            catNip += 100;
            System.out.println("True");
        }
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
        } catch (GdxRuntimeException e){
            catTexture = new Texture("Paige.png");
        }
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
