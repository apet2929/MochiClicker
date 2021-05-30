package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.io.Button;
import com.moonjew.mochiclicker.io.Font;
import com.moonjew.mochiclicker.MochiClicker;

public class ShopState extends State{
    Font font;
    Button button;

    public ShopState(GameStateManager gsm) {
        super(gsm);
        font = new Font();

    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(SpriteBatch sb) {
        font.draw(sb, "Shop", new Rectangle(50, MochiClicker.HEIGHT-100, MochiClicker.WIDTH, MochiClicker.HEIGHT), 5, 5);
    }

    @Override
    public void dispose() {

    }
}
