package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.Font;
import com.moonjew.mochiclicker.MochiClicker;
import com.moonjew.mochiclicker.entities.Cat;


public class PlayState extends State{
    public Cat test;
    Font font;
    ShapeRenderer renderer;
    public PlayState(GameStateManager gsm) {
        super(gsm);
        test = new Cat(new Texture("testcat.jpg"), 50, 50, 300, 300);
        font = new Font(new Texture("font.png"));
        renderer = new ShapeRenderer();
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
//        font.draw(sb, "testete ststesthaj!.?", new Rectangle(0, 200, 500, 500), 10, 10);
    }

    @Override
    public void dispose() {

    }
}
