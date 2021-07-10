package com.moonjew.mochiclicker.io.button;

import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.room.Room;
import com.moonjew.mochiclicker.state.GameStateManager;

public class ShopButton extends ConditionalButton{
    private Room room;
    private GameStateManager gsm;

    public ShopButton(Rectangle bounds, GameStateManager gsm, Room room) {
        super("Shop", bounds);
        this.room = room;
        this.gsm = gsm;
    }

    @Override
    public void onclick() {
        gsm.push(room.getShop());
    }
    public void setRoom(Room room){
        this.room = room;
    }
}
