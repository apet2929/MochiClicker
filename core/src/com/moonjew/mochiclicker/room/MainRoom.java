package com.moonjew.mochiclicker.room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.MochiClicker;
import com.moonjew.mochiclicker.entities.Cat;
import com.moonjew.mochiclicker.room.Room;
import com.moonjew.mochiclicker.room.RoomCarousel;
import com.moonjew.mochiclicker.state.PlayState;

import java.util.ArrayList;
import java.util.List;

public class MainRoom extends Room {

    int roomTexture;
    Rectangle rectangle;
    List<Cat> cats;

    public MainRoom(int roomTexture) {
        super();
        this.roomTexture = roomTexture;
        this.cats = new ArrayList<>();
        this.rectangle = new Rectangle(20,20, MochiClicker.WIDTH-40, MochiClicker.HEIGHT-40);
        Cat bob = new Cat("Bob", new Texture("Tiger.png"), new Texture("tiger_sleep.png"), new Texture("paige_idle.png"), 100, 70, this);
        bob.sendToMainRoom();
        addCat(new Cat("Bob", new Texture("Tiger.png"), new Texture("tiger_sleep.png"), new Texture("paige_idle.png"), 100, 70, this));
        initDecorations();
    }

    public void update(float deltaTime) {
        for(Cat cat : cats){
            cat.update(deltaTime);
            if(cat.randomTick()) PlayState.catNip+= cat.getLevel();
        }
    }

    public void addCat(Cat cat){
        cats.add(cat);
    }

    public void render(SpriteBatch sb, Camera cam){
        sb.draw(RoomCarousel.roomTextures[roomTexture], rectangle.x + cam.position.x, rectangle.y + cam.position.y, rectangle.width, rectangle.height);
        for(Cat cat: cats) {
            cat.render(sb, cam);
        }
    }

}
