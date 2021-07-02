package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.*;
import com.moonjew.mochiclicker.entities.Cat;
import com.moonjew.mochiclicker.io.BackButton;
import com.moonjew.mochiclicker.io.UpgradeButton;

import java.util.Timer;

import static com.moonjew.mochiclicker.MochiClicker.FONT;

public class ShopState extends State{

    BackButton backButton;
    UpgradeButton testUpgradeButton;
    UpgradeButton foodUpgradeButton;
    UpgradeTree testUpgradeTree;
    UpgradeTree foodUpgradeTree;
    Room room;

    public ShopState(GameStateManager gsm, Room room) {
        super(gsm);
        backButton = new BackButton(new Texture("back-button.png"), new Rectangle(50, MochiClicker.HEIGHT-100, 100, 100), gsm);
        testUpgradeButton = new UpgradeButton(new Rectangle(75, 100, 250, 100));
        foodUpgradeButton = new UpgradeButton(new Rectangle(325, 100, 250, 100));
        this.room = room;
        restart();
    }

    public boolean hasUpgrade(Upgrade upgrade) {
        return testUpgradeTree.purchased(upgrade) || foodUpgradeTree.purchased(upgrade);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            float x = Gdx.input.getX();
            float y = MochiClicker.HEIGHT - Gdx.input.getY();
            if(backButton.getBounds().contains(x, y)){
                gsm.pop();
            }
            if(testUpgradeButton.getBounds().contains(x, y)){
                testUpgradeButton.onclick(); //useless
                if(testUpgradeTree.buyNext()) {
//                    room.getCat().hungerModifier += testUpgradeTree.getCurrentUpgrade();
                    testUpgradeButton.setUpgrade(testUpgradeTree.getNextUpgrade());
                }
            }

            if(foodUpgradeButton.getBounds().contains(x,y)){
                foodUpgradeButton.onclick();
                if(foodUpgradeTree.buyNext()) foodUpgradeButton.setUpgrade(foodUpgradeTree.getNextUpgrade());
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
    public void render(SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        FONT.draw(sb, "Shop", new Rectangle(250, MochiClicker.HEIGHT-50, MochiClicker.WIDTH, MochiClicker.HEIGHT), 5, 5);
        backButton.render(sb);

        testUpgradeButton.render(sb);
        foodUpgradeButton.render(sb);

        sb.end();
        sr.setAutoShapeType(true);
        sr.begin();
        sr.setColor(Color.BLACK);
        sr.rect(testUpgradeButton.getBounds().x, testUpgradeButton.getBounds().y, testUpgradeButton.getBounds().width, testUpgradeButton.getBounds().height);
        sr.rect(foodUpgradeButton.getBounds().x, foodUpgradeButton.getBounds().y, foodUpgradeButton.getBounds().width, foodUpgradeButton.getBounds().height);
        sr.end();
    }

    public void restart() {
        testUpgradeTree = new UpgradeTree(Upgrade.TEST_UPGRADES);
        foodUpgradeTree = new UpgradeTree(Upgrade.FOOD_UPGRADES);
        testUpgradeButton.setUpgrade(testUpgradeTree.getNextUpgrade());
        foodUpgradeButton.setUpgrade(foodUpgradeTree.getNextUpgrade());
    }

    @Override
    public void dispose() {

    }
}
