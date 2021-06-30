package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.MainRoom;
import com.moonjew.mochiclicker.MochiClicker;

import static com.moonjew.mochiclicker.MochiClicker.FONT;

public class MainRoomState extends State{
    MainRoom mainRoom;
    public MainRoomState(GameStateManager gsm, MainRoom mainRoom) {
        super(gsm);
        this.mainRoom = mainRoom;
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            gsm.pop();
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        mainRoom.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch sb, ShapeRenderer sr) {
        sb.setTransformMatrix(cam.combined);
        sb.begin();
        mainRoom.render(sb, cam);
        FONT.drawMiddle(sb, "Main Room", new Rectangle(0,0, 1000, 0).setCenter(MochiClicker.WIDTH/2, MochiClicker.HEIGHT*0.8f), 4, 4);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
