package com.moonjew.mochiclicker.io;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.MochiClicker;
import com.moonjew.mochiclicker.entities.Cat;
import com.moonjew.mochiclicker.io.button.Button;

import java.util.ArrayList;

import static com.moonjew.mochiclicker.MochiClicker.FONT;
import static com.moonjew.mochiclicker.state.PlayState.catNip;

//This class will be used to handle the rendering of all UI items
public class UI {

    public boolean menu;  //Are we currently in a menu?
    private Cat cat;  //The current room's cat, used for the health, hunger, sleep, and happiness meters
    ArrayList<Button> buttons;
    public Cat dyingCat;
    Texture menuTexture;
    HappyMeter happyMeter;
    Meter hungerMeter;
    Meter healthMeter;

    public UI() {
        menu = false;
        buttons = new ArrayList<>();
        menuTexture = new Texture("sidebar.png");
        happyMeter = new HappyMeter(new Texture("happiness_icon.png"), new Rectangle(MochiClicker.WIDTH*0.06f, MochiClicker.HEIGHT*0.72f, 32, 32));
        healthMeter = new Meter(new Texture("health_icon.png"), Color.valueOf("c32f2f"),
                new Rectangle(MochiClicker.WIDTH*0.1f, MochiClicker.HEIGHT*0.77f, 32, 32),
                new Rectangle(4, 5, 24, 21));
        hungerMeter = new Meter(new Texture("hunger_icon.png"), Color.valueOf("7591ff"),
                new Rectangle(MochiClicker.WIDTH*0.14f, MochiClicker.HEIGHT*0.72f, 32, 32),
                new Rectangle(10,10,21,14));
    }

    public void render(Cat cat, SpriteBatch sb, int transitioning) { // Called only by the PlayState
        if(cat != null) {
            //Cat status
//            FONT.draw(sb, getUIText(transitioning, "Tired", (float) cat.getTired()), new Rectangle(50, MochiClicker.HEIGHT - 100, 200, 200), 2, 2);
//            FONT.draw(sb, getUIText(transitioning, "Happiness", cat.getHappiness()), new Rectangle(50, MochiClicker.HEIGHT - 125, 200, 200), 2, 2);
//            FONT.draw(sb, getUIText(transitioning, "Hunger", cat.getHunger()), new Rectangle(50, MochiClicker.HEIGHT - 150, 200, 200), 2, 2);
//            FONT.draw(sb, getUIText(transitioning, "Health", (int) Math.ceil(cat.getHealth())), new Rectangle(50, MochiClicker.HEIGHT - 175, 200, 200), 2, 2);
            FONT.draw(sb, "State " + cat.getState().getType(), new Rectangle(50, MochiClicker.HEIGHT - 200, 200, 200), 2, 2);

            happyMeter.render(sb, 3 - Math.round(cat.getHappiness()/33.0f));
            healthMeter.render(sb);
            hungerMeter.render(sb);

        } else {
            FONT.drawMiddle(sb, "You have no cat.   Buy one? 50 Catnip",
                    new Rectangle(0,0,MochiClicker.WIDTH*0.8f, MochiClicker.HEIGHT).setCenter(MochiClicker.WIDTH/2.0f, MochiClicker.HEIGHT/1.4f), 4, 4);
        }
        //Catnip counter
        FONT.draw(sb, "Catnip " + catNip, new Rectangle(50, MochiClicker.HEIGHT - 50, 200, 200), 2, 2);

        if (menu) {
//            sr.setColor(Color.BLACK);
//            sr.rect(MochiClicker.WIDTH - 100, 0, 100, MochiClicker.HEIGHT); //To be replaced with menu sprite
            sb.draw(menuTexture, MochiClicker.WIDTH - 100, 0, 100, MochiClicker.HEIGHT);
            if(cat != null) FONT.drawMiddle(sb, cat.getName(), new Rectangle(MochiClicker.WIDTH - 100, MochiClicker.HEIGHT - 50, 100, 0), 2, 2);
        }

        //Buttons
        for (Button button : buttons) {
            button.render(sb);
        }

        if (dyingCat != null) {
            FONT.drawMiddle(sb, dyingCat.getName() + " is dying! Heal?", new Rectangle(60, -200, MochiClicker.WIDTH - 60, MochiClicker.HEIGHT), 6, 6);
        }
    }

    public void render(Cat cat, ShapeRenderer sr){
        if(cat != null) {
            healthMeter.fillMeter(sr, (int) (cat.getHealth() / cat.maxHealth * 23));
            hungerMeter.fillMeter(sr, (int) ((cat.maxHunger - cat.getHunger()) / cat.maxHunger * 14));
            sr.setColor(Color.BLUE);
            sr.set(ShapeRenderer.ShapeType.Line);
            sr.rect(MochiClicker.WIDTH / 2 - 18, 100, 20, 20);
            sr.rect(MochiClicker.WIDTH / 2 + 7, 100, 20, 20);
        }
    }

    private String getUIText(int transitioning, String valName, float val){
        // If transitioning, "Tired: ???" will be returned
        String uiText = valName + " ";
        if(transitioning == 0) {
            uiText += (int)(Math.abs(val));
        } else {
            uiText += "???";
        }
        return uiText;
    }

    private String getTiredText(int transitioning) { //Returns the cats current tired level unless transitioning.
        // If transitioning, "Tired: ???" will be returned
        String tiredText = "Tired ";
        if(transitioning == 0) {
            tiredText += (int)(cat.getTired());
        } else {
            tiredText += "???";
        }
        return tiredText;
    }

    public void setCat(Cat cat) { //Called after transitioning to a new room
        this.cat = cat;
    }

    public void addButton(Button button){ //Adds button to the rendering list
        buttons.add(button);
    }

    public void addButtons(Button[] buttons){ //Adds multiple buttons at a time to the rendering list
        for (Button button : buttons) {
            addButton(button);
        }
    }

    public void removeButton(Button button) { //Removes a button from the rendering list
        buttons.remove(button);
    }
}
