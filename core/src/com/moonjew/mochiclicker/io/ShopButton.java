package com.moonjew.mochiclicker.io;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.Room;
import com.moonjew.mochiclicker.entities.Cat;
import com.moonjew.mochiclicker.state.GameStateManager;
import com.moonjew.mochiclicker.state.ShopState;

public class ShopButton extends Button{
    private Room room;
    private GameStateManager gsm;
    private boolean menu;

    public ShopButton(Rectangle bounds, GameStateManager gsm, Room room) {
        super("Shop", bounds);
        this.room = room;
        this.gsm = gsm;
    }

    @Override
    public void render(SpriteBatch sb) {
        if(menu) {
            super.render(sb);
        }
    }

    @Override
    public void onclick() {
        gsm.push(room.getShop());
    }
    public void setRoom(Room room){
        this.room = room;
    }
    public void setMenu(boolean menu) {
        this.menu = menu;
    }
}
