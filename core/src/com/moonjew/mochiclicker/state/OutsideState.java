package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.MochiClicker;
import com.moonjew.mochiclicker.io.Meter;
import com.moonjew.mochiclicker.room.OutsideRoom;

import static com.moonjew.mochiclicker.MochiClicker.FONT;

public class OutsideState extends State{
    OutsideRoom outsideRoom;
    Meter healthMeter;
    public OutsideState(GameStateManager gsm, OutsideRoom room) {
        super(gsm);
        this.outsideRoom = room;
        healthMeter = new Meter(new Texture("health_icon.png"), Color.valueOf("c32f2f"),
                new Rectangle(MochiClicker.WIDTH*0.15f, MochiClicker.HEIGHT*0.74f, 32, 32),
                new Rectangle(4, 5, 24, 21));
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            gsm.pop();
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        outsideRoom.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch sb, ShapeRenderer sr) {


        sb.setTransformMatrix(cam.combined);
        sb.begin();
        outsideRoom.render(sb, cam);
        sb.end();

        sr.begin(ShapeRenderer.ShapeType.Filled);
        healthMeter.fillMeter(sr, 22);
        sr.end();

        sb.begin();
        FONT.drawMiddle(sb, "Outside", new Rectangle(0,0, 1000, 0).setCenter(MochiClicker.WIDTH/2, MochiClicker.HEIGHT*0.8f), 4, 4);
        if(outsideRoom.getCat() == null){
            FONT.drawMiddle(sb, "Cat has returned!", new Rectangle(0,MochiClicker.HEIGHT*0.6f, MochiClicker.WIDTH, 0), 4, 4);
        } else {
            FONT.drawMiddle(sb, "Time remaining " + (int) (outsideRoom.getCat().getState().getTimeRemaining()), new Rectangle(0,MochiClicker.HEIGHT*0.6f, MochiClicker.WIDTH/4, 0), 2, 2);
        }
        healthMeter.render(sb);
        sb.end();


    }

    @Override
    public void dispose() {

    }
}
