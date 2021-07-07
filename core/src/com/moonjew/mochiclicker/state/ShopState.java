package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.*;
import com.moonjew.mochiclicker.io.BackButton;
import com.moonjew.mochiclicker.io.UpgradeButton;
import com.moonjew.mochiclicker.room.Room;
import com.moonjew.mochiclicker.upgrades.Upgrade;
import com.moonjew.mochiclicker.upgrades.UpgradeTree;
import com.moonjew.mochiclicker.upgrades.UpgradeType;

import static com.moonjew.mochiclicker.MochiClicker.FONT;

public class ShopState extends State{

    BackButton backButton;
    UpgradeButton healthUpgradeButton;
    UpgradeButton hungerUpgradeButton;
    UpgradeButton happinessUpgradeButton;
    UpgradeButton sleepUpgradeButton;

    UpgradeTree healthUpgradeTree;
    UpgradeTree hungerUpgradeTree;
    UpgradeTree happinessUpgradeTree;
    UpgradeTree sleepUpgradeTree;

    UpgradeButton bedUpgradeButton;
    UpgradeTree bedUpgradeTree;


    Room room;

    public ShopState(GameStateManager gsm, Room room) {
        super(gsm);
        backButton = new BackButton(new Texture("back-button.png"), new Rectangle(50, MochiClicker.HEIGHT-100, 100, 100), gsm);
        healthUpgradeButton = new UpgradeButton(new Rectangle(10, 100, 140, 100));
        hungerUpgradeButton = new UpgradeButton(new Rectangle(170, 100, 140, 100));
        happinessUpgradeButton = new UpgradeButton(new Rectangle(330, 100, 140, 100));
        sleepUpgradeButton = new UpgradeButton(new Rectangle(490, 100, 140, 100));
        bedUpgradeButton = new UpgradeButton(new Rectangle(10, 220, 140, 100));
        this.room = room;
        restart();
    }

    public boolean hasUpgrade(Upgrade upgrade) {
        return healthUpgradeTree.purchased(upgrade) || hungerUpgradeTree.purchased(upgrade);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            float x = Gdx.input.getX();
            float y = MochiClicker.HEIGHT - Gdx.input.getY();
            if(backButton.getBounds().contains(x, y)){
                gsm.pop();
            }
            else if(healthUpgradeButton.getBounds().contains(x, y)){
                healthUpgradeTree.buyNext(room.getCat(), healthUpgradeButton);
            }
            else if(hungerUpgradeButton.getBounds().contains(x,y)){
                hungerUpgradeTree.buyNext(room.getCat(), hungerUpgradeButton);
            }
            else if(sleepUpgradeButton.getBounds().contains(x,y)){
                sleepUpgradeTree.buyNext(room.getCat(), sleepUpgradeButton);
            }
            else if(happinessUpgradeButton.getBounds().contains(x,y)){
                happinessUpgradeTree.buyNext(room.getCat(), happinessUpgradeButton);
            }
            else if(bedUpgradeButton.getBounds().contains(x,y)){
                bedUpgradeTree.buyNext(room, bedUpgradeButton);
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
        FONT.drawMiddle(sb, "Shop", new Rectangle(0, MochiClicker.HEIGHT-100, MochiClicker.WIDTH, 0), 6, 6);
        backButton.render(sb);

        healthUpgradeButton.render(sb);
        hungerUpgradeButton.render(sb);
        sleepUpgradeButton.render(sb);
        happinessUpgradeButton.render(sb);
        bedUpgradeButton.render(sb);

        sb.end();
        sr.setAutoShapeType(true);
        sr.begin();
        sr.setColor(Color.BLACK);
        healthUpgradeButton.renderOutline(sr);
        happinessUpgradeButton.renderOutline(sr);
        hungerUpgradeButton.renderOutline(sr);
        sleepUpgradeButton.renderOutline(sr);
        bedUpgradeButton.renderOutline(sr);
        sr.end();
    }

    public void restart() {
        hungerUpgradeTree = new UpgradeTree(Upgrade.FOOD_UPGRADES, UpgradeType.HUNGER);
        healthUpgradeTree = new UpgradeTree(Upgrade.HEALTH_UPGRADES, UpgradeType.HEALTH);
        happinessUpgradeTree = new UpgradeTree(Upgrade.HAPPINESS_UPGRADES, UpgradeType.HAPPINESS);
        sleepUpgradeTree = new UpgradeTree(Upgrade.SLEEP_UPGRADES, UpgradeType.SLEEP);

        bedUpgradeTree = new UpgradeTree(Upgrade.BED_UPGRADES, UpgradeType.BED);

        healthUpgradeButton.setUpgrade(healthUpgradeTree.getNextUpgrade());
        hungerUpgradeButton.setUpgrade(hungerUpgradeTree.getNextUpgrade());
        happinessUpgradeButton.setUpgrade(happinessUpgradeTree.getNextUpgrade());
        sleepUpgradeButton.setUpgrade(sleepUpgradeTree.getNextUpgrade());

        bedUpgradeButton.setUpgrade(bedUpgradeTree.getNextUpgrade());
    }

    @Override
    public void dispose() {

    }
}
