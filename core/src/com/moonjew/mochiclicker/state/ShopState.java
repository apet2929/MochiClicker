package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.Upgrade;
import com.moonjew.mochiclicker.UpgradeTree;
import com.moonjew.mochiclicker.entities.Cat;
import com.moonjew.mochiclicker.io.BackButton;
import com.moonjew.mochiclicker.MochiClicker;
import com.moonjew.mochiclicker.io.UpgradeButton;

import static com.moonjew.mochiclicker.MochiClicker.FONT;

public class ShopState extends State{
    BackButton backButton;
    UpgradeButton testUpgradeButton;
    UpgradeTree upgrades;
    Cat cat;

    public ShopState(GameStateManager gsm, Cat cat) {
        super(gsm);
        backButton = new BackButton(new Texture("back-button.png"), new Rectangle(50, MochiClicker.HEIGHT-100, 100, 100), gsm);
        upgrades = new UpgradeTree(new Upgrade[]{Upgrade.TEST, Upgrade.TEST2});
        testUpgradeButton = new UpgradeButton(new Rectangle(100, 80, 400, 100), Upgrade.TEST);
        this.cat = cat;
    }

    public void buyUpgrade(Upgrade upgrade){
        if(PlayState.catNip >= upgrade.COST && !upgrades.purchased(upgrade)) {
            upgrades.buyNext();
            cat.levelUp();
        }
    }

    public boolean hasUpgrade(Upgrade upgrade){
        return upgrades.purchased(upgrade);
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
                testUpgradeButton.onclick();
                upgrades.buyNext();
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
        if(upgrades.purchased(Upgrade.TEST)){
            testUpgradeButton.render(sb);
            FONT.draw(sb, upgrades.getCurrentUpgrade().DESCRIPTION, testUpgradeButton.getBounds(), 3, 3);
        }
        sb.end();
        sr.setAutoShapeType(true);
        sr.begin();
        sr.setColor(Color.BLACK);
        sr.rect(testUpgradeButton.getBounds().x, testUpgradeButton.getBounds().y, testUpgradeButton.getBounds().width, testUpgradeButton.getBounds().height);
        sr.end();
    }

    @Override
    public void dispose() {

    }
}
