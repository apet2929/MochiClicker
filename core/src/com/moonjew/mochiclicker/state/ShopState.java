package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.Upgrade;
import com.moonjew.mochiclicker.io.BackButton;
import com.moonjew.mochiclicker.io.Font;
import com.moonjew.mochiclicker.MochiClicker;

import java.util.ArrayList;
import java.util.HashMap;

public class ShopState extends State{
    Font font;
    BackButton backButton;
    HashMap<Upgrade, Boolean> upgrades;

    public ShopState(GameStateManager gsm) {
        super(gsm);
        font = new Font();
        backButton = new BackButton(new Texture("testcat.jpg"), new Rectangle(MochiClicker.WIDTH/2, MochiClicker.HEIGHT/2, 200, 200), gsm);
        upgrades = new HashMap<>();
        for(int i = 0; i < Upgrade.values().length; i++){
            System.out.println(Upgrade.values()[i]);
            upgrades.put(Upgrade.values()[i], false);
        }
    }

    public void buyUpgrade(Upgrade upgrade){
        upgrades.put(upgrade, true);
    }
    public boolean hasUpgrade(Upgrade upgrade){
        return upgrades.get(upgrade);
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
        if(upgrades.get(Upgrade.TEST)){
            font.draw(sb, "Test Upgrade Got", new Rectangle(50, MochiClicker.HEIGHT-400, MochiClicker.WIDTH, MochiClicker.HEIGHT), 5, 5);
        }
        sb.end();
    }


    @Override
    public void dispose() {

    }
}
