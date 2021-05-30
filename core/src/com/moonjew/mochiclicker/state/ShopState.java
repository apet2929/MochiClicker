package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.io.BackButton;
import com.moonjew.mochiclicker.io.Font;
import com.moonjew.mochiclicker.MochiClicker;

public class ShopState extends State{
    Font font;
    BackButton backButton;

    public ShopState(GameStateManager gsm) {
        super(gsm);
        font = new Font();
        backButton = new BackButton(new Texture("testcat.jpg"), new Rectangle(MochiClicker.WIDTH/2, MochiClicker.HEIGHT/2, 200, 200), gsm);

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            float x = Gdx.input.getX();
            float y = MochiClicker.HEIGHT - Gdx.input.getY();
            if(backButton.getBounds().contains(x, y)){
                gsm.pop();
            }
            Gdx.app.setLogLevel(Application.LOG_DEBUG);
            Gdx.app.debug("POSITION", "X touched: " + x + " Y touched: " + y);
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        font.draw(sb, "Shop", new Rectangle(50, MochiClicker.HEIGHT-100, MochiClicker.WIDTH, MochiClicker.HEIGHT), 5, 5);
        backButton.render(sb);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
