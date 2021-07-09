package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.MochiClicker;
import com.moonjew.mochiclicker.room.OutsideRoom;

import static com.moonjew.mochiclicker.MochiClicker.FONT;

public class OutsideState extends State{
    OutsideRoom outsideRoom;
    public OutsideState(GameStateManager gsm, OutsideRoom room) {
        super(gsm);
        this.outsideRoom = room;
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
        FONT.drawMiddle(sb, "Outside", new Rectangle(0,0, 1000, 0).setCenter(MochiClicker.WIDTH/2, MochiClicker.HEIGHT*0.8f), 4, 4);
        if(outsideRoom.getCat() == null){
            FONT.drawMiddle(sb, "Cat has returned!", new Rectangle(0,MochiClicker.HEIGHT*0.6f, MochiClicker.WIDTH, 0), 4, 4);
        } else {
            FONT.drawMiddle(sb, "Time remaining " + outsideRoom.getCat().getTimeLeftOutside(), new Rectangle(0,MochiClicker.HEIGHT*0.6f, MochiClicker.WIDTH/4, 0), 2, 2);
        }
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
