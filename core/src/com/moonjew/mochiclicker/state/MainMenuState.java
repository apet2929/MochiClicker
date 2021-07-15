package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.MochiClicker;

import static com.moonjew.mochiclicker.MochiClicker.FONT;


public class MainMenuState extends State{
    Texture img;
    Texture menuButton;
    public MainMenuState(GameStateManager gsm) {
        super(gsm);
        img = new Texture("menubg.png");
        menuButton = new Texture("menu_button.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new TutorialState(gsm));
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb, ShapeRenderer sr) {
        sb.begin();
        sb.draw(img, 0, 0, 640, 480);
        FONT.draw(sb, "Mochis", new Rectangle(MochiClicker.WIDTH / 4 + 30, MochiClicker.HEIGHT - 100, MochiClicker.WIDTH, MochiClicker.HEIGHT), 6.0f, 6.0f);
        FONT.draw(sb, "Foster Home", new Rectangle(MochiClicker.WIDTH / 5 - 40, MochiClicker.HEIGHT - 160, MochiClicker.WIDTH, MochiClicker.HEIGHT), 6.0f, 6.0f);
        int buttons = 2;
        int xCoord;
        int yCoord;
        int spacing = 60;
        int buttonWidth = 128;
        int margin = (MochiClicker.WIDTH - (buttons * buttonWidth + spacing * (buttons - 1)))/2;
        int rows = 2;
        int count = 0;
        String[] buttonTitles = {"Play", "Settings", "Quit", "Credits"};
        for(int j = 0; j < rows; j++) {
            for (int i = 0; i < buttons; i++) {
                xCoord = margin + i * (buttonWidth + spacing);
                yCoord = 220 - j * (buttonWidth / 2 + 20);
                sb.draw(menuButton, xCoord, yCoord, buttonWidth, buttonWidth / 2);
                FONT.drawMiddle(sb, buttonTitles[count], new Rectangle(xCoord,yCoord,buttonWidth,buttonWidth * .35f), 2,2);
                count++;
            }
        }
        sb.end();
    }

    @Override
    public void dispose() {
    }

}
