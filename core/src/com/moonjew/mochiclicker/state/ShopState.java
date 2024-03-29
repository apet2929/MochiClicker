package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.*;
import com.moonjew.mochiclicker.io.button.BackButton;
import com.moonjew.mochiclicker.io.button.UpgradeButton;
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
    UpgradeButton[] upgrades;

    UpgradeButton bedUpgradeButton;
    UpgradeTree bedUpgradeTree;
    Texture upgradesIcon;


    Room room;
    Texture shopBackground;

    public ShopState(GameStateManager gsm, Room room) {
        super(gsm);
        backButton = new BackButton(new Texture("back_arrow.png"), new Rectangle(50, MochiClicker.HEIGHT-105, 80, 80), gsm);
        healthUpgradeButton = new UpgradeButton(new Rectangle(112, 270, 120, 90));
        hungerUpgradeButton = new UpgradeButton(new Rectangle(250, 270, 120, 90));
        happinessUpgradeButton = new UpgradeButton(new Rectangle(395, 270, 120, 90));
        sleepUpgradeButton = new UpgradeButton(new Rectangle(112, 160, 120, 90));
        bedUpgradeButton = new UpgradeButton(new Rectangle(253, 160, 120, 90));
        this.room = room;
        shopBackground = new Texture("shop_bg.png");
        upgradesIcon = new Texture("shop_unit.png");
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
        sb.draw(shopBackground, 0, 0, 640, 480);
        int boxes = 3;
        int spacing = 15;
        int margin = (MochiClicker.WIDTH - (boxes * 126 + spacing * (boxes - 1)))/2;
        int upgradeIndex = 0;
        for(int i = 0; i < boxes; i++) {
            int xCoord = margin + i * (MochiClicker.WIDTH - margin * 2)/boxes;
            sb.draw(upgradesIcon, xCoord, 265, 126, 96);

            if(upgradeIndex < upgrades.length) {
                upgrades[upgradeIndex].resize(xCoord, 265, 126, 96);
                upgradeIndex++;
            }

        }
        for(int i = 0; i < boxes; i++) {
            int xCoord = margin + i * (MochiClicker.WIDTH - margin * 2)/boxes;
            sb.draw(upgradesIcon, xCoord, 155, 126, 96);
            if(upgradeIndex < upgrades.length) {
                upgrades[upgradeIndex].resize(xCoord, 155, 126, 96);
                upgradeIndex++;
            }
        }
        FONT.drawMiddle(sb,"Shop", new Rectangle(0, MochiClicker.HEIGHT-100, MochiClicker.WIDTH, 0), 6, 6);
        backButton.render(sb);
        for(UpgradeButton button: upgrades){
            button.render(sb);
        }
        sb.end();

//        sr.setAutoShapeType(true);
//        sr.begin();
//        sr.setColor(Color.BLACK);
//        healthUpgradeButton.renderOutline(sr);
//        happinessUpgradeButton.renderOutline(sr);
//        hungerUpgradeButton.renderOutline(sr);
//        sleepUpgradeButton.renderOutline(sr);
//        bedUpgradeButton.renderOutline(sr);
//        sr.end();
    }

    public void restart() {
        hungerUpgradeTree = new UpgradeTree(Upgrade.FOOD_UPGRADES, UpgradeType.HUNGER);
        healthUpgradeTree = new UpgradeTree(Upgrade.HEALTH_UPGRADES, UpgradeType.HEALTH);
        happinessUpgradeTree = new UpgradeTree(Upgrade.HAPPINESS_UPGRADES, UpgradeType.HAPPINESS);
        sleepUpgradeTree = new UpgradeTree(Upgrade.SLEEP_UPGRADES, UpgradeType.SLEEP);

        bedUpgradeTree = new UpgradeTree(Upgrade.BED_UPGRADES, UpgradeType.BED);

        upgrades = new UpgradeButton[]{hungerUpgradeButton, healthUpgradeButton, happinessUpgradeButton, sleepUpgradeButton, bedUpgradeButton};

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
