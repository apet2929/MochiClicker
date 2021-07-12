package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.MochiClicker;
import com.moonjew.mochiclicker.io.Animation;
import com.moonjew.mochiclicker.io.HappyMeter;
import com.moonjew.mochiclicker.io.Meter;
import com.moonjew.mochiclicker.io.button.ConditionalButton;
import com.moonjew.mochiclicker.io.button.GenericButton;
import com.moonjew.mochiclicker.io.button.SidebarButton;

import java.awt.*;

import static com.moonjew.mochiclicker.MochiClicker.FONT;
import static com.moonjew.mochiclicker.state.PlayState.catNip;

public class TutorialState extends State{
    Rectangle text_box = new Rectangle(50, 25, 450, 100);
    Animation mochiIdle = new Animation(new TextureRegion(new Texture("mochi_idle.png")), 14, 2.0f);
    String[] lines = new String[]{
            "This is one of our rooms. As you can see ", "its kind of empty.",
            "Lets bring Paige in.", "Your job is to take care of Paige and all the other kitties we bring in.",
            "This is her hunger meter. It will show you if shes hungry or not.",
            "Oh no! Shes hungry. Lets feed her.",
            "Click on this to change your cursor to the feeding setting.",
            "Then click on Paige to feed her.", "She likes to move around though so you have to catch her to feed her.",
            "Great! Now shes full.", "This is her happiness meter.", "But look! Shes not happy.",
            "With the happiness tool menu you have options for what to give Paige.",
            "You can pet her give her treats or toys."
    };
    int currentLine;
    int drawUI;
    Meter healthMeter, hungerMeter;
    HappyMeter happyMeter;
    GenericButton skipTutorialButton;
    ConditionalButton sidebarButton, foodBowlButton, handButton, mouseButton;
    ConditionalButton[] ui;
    boolean renderTextBox = true;
    public TutorialState(GameStateManager gsm) {
        super(gsm);
        currentLine = 0;
        drawUI = 0;

        happyMeter = new HappyMeter(new Texture("happiness_icon.png"), new Rectangle(MochiClicker.WIDTH*0.06f, MochiClicker.HEIGHT*0.72f, 32, 32));
        healthMeter = new Meter(new Texture("health_icon.png"), Color.valueOf("c32f2f"),
                new Rectangle(MochiClicker.WIDTH*0.1f, MochiClicker.HEIGHT*0.77f, 32, 32),
                new Rectangle(4, 5, 24, 21));
        hungerMeter = new Meter(new Texture("hunger_icon.png"), Color.valueOf("7591ff"),
                new Rectangle(MochiClicker.WIDTH*0.14f, MochiClicker.HEIGHT*0.72f, 32, 32),
                new Rectangle(10,10,21,14));

        sidebarButton = new ConditionalButton(new Texture("menu-button.png"), new Rectangle(MochiClicker.WIDTH-100, MochiClicker.HEIGHT-100, 32, 32));
        foodBowlButton = new ConditionalButton(new Texture("food_bowl_button.png"), new Rectangle(MochiClicker.WIDTH / 2.0f + 40, 25, 64, 64));
        handButton = new ConditionalButton(new Texture("hand_button.png"), new Rectangle(MochiClicker.WIDTH / 2.0f - 40, 25, 64, 64));
        mouseButton = new ConditionalButton(new Texture("mouse_button.png"), new Rectangle(MochiClicker.WIDTH/2-43, 100, 20, 20));
        skipTutorialButton = new GenericButton("Skip", new Rectangle(30, MochiClicker.HEIGHT-75,50, 50));
        ui = new ConditionalButton[]{sidebarButton, foodBowlButton, handButton, mouseButton};
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
        if(Gdx.input.justTouched()){
            float x = Gdx.input.getX();
            float y = MochiClicker.HEIGHT - Gdx.input.getY();
            if(text_box.contains(x, y) && renderTextBox){
                //advance text
                currentLine++;

                if(currentLine == 5){
                    sidebarButton.willRender = true;
                }
                if(currentLine == 6){
                    foodBowlButton.willRender = true;
                    renderTextBox = false;
                }
                if(currentLine == 7){
                    handButton.willRender = true;
                    renderTextBox = false;
                }


                if(currentLine == lines.length){
                    exitTutorial();
                    currentLine = 0;
                }
            } else if(skipTutorialButton.getBounds().contains(x, y)){
                exitTutorial();
            } else if(handButton.getBounds().contains(x,y) || foodBowlButton.getBounds().contains(x,y)){
                renderTextBox = true;
            }
        }
    }

    void exitTutorial(){
        gsm.set(new PlayState(gsm));
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        mochiIdle.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch sb, ShapeRenderer sr) {
        sr.setAutoShapeType(true);
        //background render
        sb.begin();
        sb.draw(new Texture("room.png"),20,20, MochiClicker.WIDTH-40, MochiClicker.HEIGHT-40);
        sb.end();

        //cats & UI
        doUIMeters(sr);

        sb.begin();
        sb.draw(mochiIdle.getFrame(), 425, 25, 200, 200);
        doUI(sb);
        sb.end();

        //text
        sb.begin();
        if(renderTextBox) {
            sb.draw(new Texture("tutorial_box.png"), text_box.x, text_box.y, text_box.width, text_box.height);
            FONT.drawMiddle(sb, lines[currentLine], text_box, 2, 2);
        }
        sb.end();
    }
    public void doUI(SpriteBatch sb) {
        skipTutorialButton.render(sb);
        if(currentLine >= 2){
            happyMeter.render(sb, 0);
        }
        if(currentLine >= 3){
            hungerMeter.render(sb);
        }
        if(currentLine >= 4){
            healthMeter.render(sb);
        } if(currentLine >= 7){
            FONT.draw(sb, "Catnip 0", new Rectangle(50, MochiClicker.HEIGHT - 50, 200, 200), 2, 2);
        } if(currentLine >= 8){
            FONT.draw(sb, "Catnip 100", new Rectangle(50, MochiClicker.HEIGHT - 50, 200, 200), 2, 2);
        }
        for(ConditionalButton conditionalButton : ui){
            conditionalButton.render(sb);
        }
    }
    public void doUIMeters(ShapeRenderer sr){
        sr.begin();
        if(currentLine >= 3) hungerMeter.fillMeter(sr, 13);
        if(currentLine >= 4) healthMeter.fillMeter(sr, 22);
        sr.end();
    }

    @Override
    public void dispose() {
        mochiIdle.dispose();
    }

}
