package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.moonjew.mochiclicker.Font;
import com.moonjew.mochiclicker.entities.Cat;


public class PlayState extends State{
    public Cat test;
    Font font;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        test = new Cat(new Texture("testcat.jpg"), 50, 50, 300, 300);
        font = new Font(new Texture("font.png"));
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float deltaTime) {
        test.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch sb) {
//        sb.begin();
//        sb.draw(test.getTexture(), test.getPosition().x, test.getPosition().y, test.getPosition().width, test.getPosition().height);
//        sb.end();
        font.test(sb);
    }

    @Override
    public void dispose() {

    }
}
