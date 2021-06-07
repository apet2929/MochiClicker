package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.Upgrade;
import com.moonjew.mochiclicker.entities.Cat;
import com.moonjew.mochiclicker.io.BackButton;
import com.moonjew.mochiclicker.MochiClicker;
import com.moonjew.mochiclicker.io.UpgradeButton;
import java.util.HashMap;

import static com.moonjew.mochiclicker.MochiClicker.FONT;

public class ShopState extends State{
    BackButton backButton;
    UpgradeButton testUpgradeButton;
    HashMap<Upgrade, Boolean> upgrades;
    ShapeRenderer renderer;
    Cat cat;

    public ShopState(GameStateManager gsm, ShapeRenderer renderer, Cat cat) {
        super(gsm);
        this.renderer = renderer;
        backButton = new BackButton(new Texture("back-button.png"), new Rectangle(50, MochiClicker.HEIGHT-100, 100, 100), gsm);
        upgrades = new HashMap<>();
        for(int i = 0; i < Upgrade.UPGRADES.length; i++){
            upgrades.put(Upgrade.UPGRADES[i], false);
        }
        testUpgradeButton = new UpgradeButton(new Rectangle(100, 80, 400, 100), Upgrade.TEST);
        this.cat = cat;
    }

    public void buyUpgrade(Upgrade upgrade){
        if(PlayState.catNip >= upgrade.COST && !upgrades.get(upgrade)) {
            upgrades.put(upgrade, true);
            PlayState.catNip -= upgrade.COST;
            cat.levelUp();
        }
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
            if(testUpgradeButton.getBounds().contains(x, y)){
                testUpgradeButton.onclick();
                buyUpgrade(testUpgradeButton.getUpgrade());
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
        if(upgrades.get(Upgrade.TEST)){
            testUpgradeButton.render(sb);
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
