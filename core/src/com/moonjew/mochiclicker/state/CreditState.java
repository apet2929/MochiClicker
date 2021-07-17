package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.moonjew.mochiclicker.MochiClicker;

import static com.moonjew.mochiclicker.MochiClicker.FONT;

public class CreditState extends State{
    Texture img;
    protected CreditState(GameStateManager gsm) {
        super(gsm);
        img = new Texture("menubg.png");


    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.pop();
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb, ShapeRenderer sr) {

        sb.begin();
        sb.draw(img, 0, 0, MochiClicker.WIDTH, MochiClicker.HEIGHT);
        FONT.drawMiddle(sb, "Credits", 0,150, MochiClicker.WIDTH, MochiClicker.HEIGHT, 4, 4);
        FONT.draw(sb, "Art       CeaShell", new Rectangle(50, MochiClicker.HEIGHT/2, MochiClicker.WIDTH, MochiClicker.HEIGHT), 4, 4);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
