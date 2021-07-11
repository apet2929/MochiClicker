package com.moonjew.mochiclicker.room;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.MochiClicker;
import com.moonjew.mochiclicker.entities.Cat;
import com.moonjew.mochiclicker.state.PlayState;

import java.util.ArrayList;
import java.util.List;

public class OutsideRoom extends Room{
    int roomTexture;
    Rectangle rectangle;
    Room regularRoom;

    public OutsideRoom(int roomTexture) {
        super();
        this.roomTexture = roomTexture;
        this.rectangle = new Rectangle(20,20, MochiClicker.WIDTH-40, MochiClicker.HEIGHT-40);
        initDecorations();
    }

    public void update(float deltaTime) {
        if(cat != null) {
            if(cat.updateOutside(deltaTime)){
                regularRoom.cat = cat;
                cat = null;
            }
        }
    }

    public void render(SpriteBatch sb, Camera cam){
        sb.draw(RoomCarousel.roomTextures[roomTexture], rectangle.x + cam.position.x, rectangle.y + cam.position.y, rectangle.width, rectangle.height);
        if(cat != null) cat.render(sb, cam);
    }

    public void sendCatOutside(Room regularRoom) {
        this.regularRoom = regularRoom;
        this.cat = regularRoom.getCat();
    }
}

