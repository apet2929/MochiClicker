package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.moonjew.mochiclicker.RoomCarousel;
import com.moonjew.mochiclicker.Upgrade;
import com.moonjew.mochiclicker.io.*;
import com.moonjew.mochiclicker.MochiClicker;
import com.moonjew.mochiclicker.Room;
import com.moonjew.mochiclicker.entities.Cat;
import com.moonjew.mochiclicker.io.Button;


import static com.moonjew.mochiclicker.MochiClicker.FONT;

public class PlayState extends State{
    public static int catNip;
    RoomCarousel rooms;
    UI ui;
    int currentRoom;
    ShopButton shopButton;
    MenuButton menuButton;
    Button foodBowlButton;
    int transitioning; // 0 = not transitioning, 1 = right, -1 = left
    boolean menu;

    short currentTool; //0 = no tool, 1 = dustbin, 2 = toy bin, 3 = food bowl
    private static final short NO_TOOL = (short) 0;
    private static final short DUSTBIN_TOOL = (short) 1;
    private static final short TOY_BIN_TOOL = (short) 2;
    private static final short FOOD_BOWL_TOOL = (short) 3;

    Cursor dustbinCursor;
    Cursor toyBinCursor;
    Cursor foodBowlCursor;

    public PlayState(GameStateManager gsm) {
        super(gsm);

        ui = new UI();

        currentRoom = 0;
        rooms = new RoomCarousel();
        rooms.addRoom(new Room(Color.PURPLE, gsm));
        rooms.addRoom(new Room(Color.ROYAL, gsm));
        rooms.addRoom(new Room(Color.RED, gsm));
        transitioning = 0;
        ui.setCat(rooms.getCurrentRoom().getCat());

        catNip = 0;

        shopButton = new ShopButton(new Rectangle(MochiClicker.WIDTH-100, MochiClicker.HEIGHT-100, 100, 50), gsm, rooms.getCurrentRoom());
        menuButton = new MenuButton(new Rectangle(MochiClicker.WIDTH-50, MochiClicker.HEIGHT-50, 32, 32));
        foodBowlButton = new GenericButton(new Texture("food_bowl_button.png"), new Rectangle(MochiClicker.WIDTH / 2.0f + 25, 25, 64, 64));
        ui.addButtons(new Button[]{shopButton, menuButton, foodBowlButton}); //adding buttons to the UI

        cam.setToOrtho(false, MochiClicker.WIDTH, MochiClicker.HEIGHT);
        cam.position.x = 0;
        cam.position.y = 0;

        Pixmap foodBowlCursorTexture = new Pixmap(Gdx.files.internal("food_bowl.png"));
        int xHotSpot = foodBowlCursorTexture.getWidth() / 2;
        int yHotSpot = foodBowlCursorTexture.getHeight() / 2;
        foodBowlCursor = Gdx.graphics.newCursor(foodBowlCursorTexture, xHotSpot, yHotSpot);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            transitioning = 1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            transitioning = -1;
        }
        if(Gdx.input.justTouched()){
            float x = Gdx.input.getX();
            float y = MochiClicker.HEIGHT - Gdx.input.getY();
            if(rooms.getCurrentRoom().getCat().getPosition().contains(x, y) && !rooms.getCurrentRoom().getCat().isSleeping()){
                // hit cat
                if(currentTool == NO_TOOL) {
                    catNip++;
                    if (rooms.getCurrentRoom().hasUpgrade(Upgrade.TEST)) {
                        catNip += 2;
                    }
                }
                else if (currentTool == FOOD_BOWL_TOOL){
                    System.out.println("Testing");
                }
            }
            if(shopButton.getBounds().contains(x, y) && menu){
                System.out.println("True");
                shopButton.setRoom(rooms.getCurrentRoom());
                shopButton.onclick();
                setMenu(!menu);
            }
            else if(menuButton.getBounds().contains(x,y)){
                setMenu(!menu);
                setCurrentTool(NO_TOOL);
            }
            else if(foodBowlButton.getBounds().contains(x,y)){
                if(currentTool == FOOD_BOWL_TOOL) setCurrentTool(NO_TOOL);
                else setCurrentTool(FOOD_BOWL_TOOL);
            }

            Gdx.app.setLogLevel(Application.LOG_DEBUG);
            Gdx.app.debug("POSITION", "X touched: " + x + " Y touched: " + y);
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        rooms.update(deltaTime);
        if(transitioning == 1) {
            cam.position.x -= 1000 * deltaTime;
            if(cam.position.x < -rooms.getCurrentRoom().getRectangle().width - 50){
                transitioning = 0;
                rooms.moveRight();
                cam.position.x = 0;
                cam.position.y = 0;
                ui.setCat(rooms.getCurrentRoom().getCat());
            }
        }
        if(transitioning == -1) {
            cam.position.x += 1000 * deltaTime;
            if(cam.position.x > rooms.getCurrentRoom().getRectangle().width + 50){
                transitioning = 0;
                rooms.moveLeft();
                cam.position.x = 0;
                cam.position.y = 0;
                ui.setCat(rooms.getCurrentRoom().getCat());
            }
        }
    }

    @Override
    public void render(SpriteBatch sb, ShapeRenderer sr) {
        sb.setProjectionMatrix(cam.combined);
        sr.setProjectionMatrix(cam.combined);
        sr.setAutoShapeType(true);

        Rectangle rectangle = rooms.getCurrentRoom().getRectangle();
        Texture background = new Texture("room.png");
        sr.begin(ShapeRenderer.ShapeType.Line);
        sb.begin();

        //BOTTOM LAYER - BACKGROUNDS

        sb.draw(background, cam.position.x + rectangle.x,  cam.position.y + rectangle.y, rectangle.width, rectangle.height);

        if(transitioning == 1){ //to the right
            Rectangle rightRectangle = rooms.getRightRoom().getRectangle();
            sb.draw(background, cam.position.x + rightRectangle.x + rectangle.width + 50, cam.position.y + rightRectangle.y, rightRectangle.width, rightRectangle.height);
        }
        else if(transitioning == -1){ //to the right
            Rectangle leftRectangle = rooms.getLeftRoom().getRectangle();
            sb.draw(background, cam.position.x + leftRectangle.x - rectangle.width - 50, cam.position.y + leftRectangle.y, leftRectangle.width, leftRectangle.height);
        }

        //MIDDLE LAYER - ENTITIES, EFFECTS

        Cat cat = rooms.getCurrentRoom().getCat();
        sb.draw(cat.getTexture(), cam.position.x + cat.getPosition().x, cam.position.y + cat.getPosition().y, cat.getPosition().width, cat.getPosition().height);

        //TOP LAYER - UI

        ui.render(sb, sr, transitioning);

        sb.end();
        sr.end();
    }

    public void setCurrentTool(short currentTool) {
        if(currentTool >= 0 && currentTool <= 3){
            this.currentTool = currentTool;
        }
        switch (currentTool){
            case NO_TOOL: {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                break;
            }
            case DUSTBIN_TOOL: {
                break;
            }
            case TOY_BIN_TOOL: {
                break;
            }
            case FOOD_BOWL_TOOL: {
                Gdx.graphics.setCursor(foodBowlCursor);
                break;
            }
            default: throw new IllegalArgumentException();
        }
    }

    public void setMenu(boolean menu){
        this.menu = menu;
        if(menu){
            cam.position.x = -100;
            menuButton.getBounds().x = MochiClicker.WIDTH - 150;

        } else {
            cam.position.x = 0;
            menuButton.getBounds().x = MochiClicker.WIDTH - 50;
        }
        shopButton.setMenu(menu);
        ui.menu = menu;
    }

    @Override
    public void dispose() {
        rooms.dispose();
        foodBowlCursor.dispose();
    }
}
