package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.MochiClicker;

import static com.moonjew.mochiclicker.MochiClicker.FONT;

public class MainMenuState extends State{
    Texture img;
    ShapeRenderer sr;
    public MainMenuState(GameStateManager gsm, ShapeRenderer sr) {
        super(gsm);
        img = new Texture("testcat.jpg");
        this.sr = sr;
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm, sr));
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        FONT.draw(sb, "Main Menu", new Rectangle((MochiClicker.WIDTH/2) - 150, MochiClicker.HEIGHT-50, MochiClicker.WIDTH, MochiClicker.HEIGHT), 5, 5);
        sb.draw(img, MochiClicker.WIDTH/2 - 640/2, MochiClicker.HEIGHT/2 - 640/2, 640, 640);
        sb.end();
    }

    @Override
    public void dispose() {
    }

}
