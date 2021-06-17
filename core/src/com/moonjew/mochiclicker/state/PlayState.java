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
import com.moonjew.mochiclicker.RoomCarousel;
import com.moonjew.mochiclicker.Upgrade;
import com.moonjew.mochiclicker.io.*;
import com.moonjew.mochiclicker.MochiClicker;
import com.moonjew.mochiclicker.Room;
import com.moonjew.mochiclicker.entities.Cat;
import com.moonjew.mochiclicker.io.Button;

public class PlayState extends State{
    public static int catNip;
    RoomCarousel rooms;
    UI ui;
    int currentRoom;
    ShopButton shopButton;
    MenuButton menuButton;
    Button foodBowlButton;
    Button handButton;
    Button mouseButton;
    int transitioning; // 0 = not transitioning, 1 = right, -1 = left
    boolean menu;

    short currentTool; //0 = no tool, 1 = dustbin, 2 = toy bin, 3 = food bowl
    private static final short NO_TOOL = (short) 0;
    private static final short DUSTBIN_TOOL = (short) 1;
    private static final short HAND_TOOL = (short) 2;
    private static final short FOOD_BOWL_TOOL = (short) 3;
    private static final short MOUSE_TOY_TOOL = (short) 4;

    Cursor dustbinCursor;
    Cursor handCursor;
    Cursor foodBowlCursor;
    Cursor mouseCursor;

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
        foodBowlButton = new GenericButton(new Texture("food_bowl_button.png"), new Rectangle(MochiClicker.WIDTH / 2.0f + 40, 25, 64, 64));
        handButton = new GenericButton(new Texture("hand_button.png"), new Rectangle(MochiClicker.WIDTH / 2.0f - 40, 25, 64, 64));
        mouseButton = new GenericButton(new Texture("mouse_button.png"), new Rectangle(MochiClicker.WIDTH/2-43, 100, 20, 20));
        ui.addButtons(new Button[]{shopButton, menuButton, foodBowlButton, handButton, mouseButton}); //adding buttons to the UI

        cam.setToOrtho(false, MochiClicker.WIDTH, MochiClicker.HEIGHT);
        cam.position.x = 0;
        cam.position.y = 0;

        Pixmap foodBowlCursorTexture = new Pixmap(Gdx.files.internal("food_bowl.png"));
        int xHotSpot = foodBowlCursorTexture.getWidth() / 2;
        int yHotSpot = foodBowlCursorTexture.getHeight() / 2;
        foodBowlCursor = Gdx.graphics.newCursor(foodBowlCursorTexture, xHotSpot, yHotSpot);

        Pixmap handCursorTexture = new Pixmap(Gdx.files.internal("hand_button.png"));
        xHotSpot = foodBowlCursorTexture.getWidth() / 2;
        yHotSpot = foodBowlCursorTexture.getHeight() / 2;
        handCursor = Gdx.graphics.newCursor(handCursorTexture, xHotSpot, yHotSpot);

        Pixmap mouseCursorTexture = new Pixmap(Gdx.files.internal("mouse.png"));
        xHotSpot = mouseCursorTexture.getWidth() / 2;
        yHotSpot = mouseCursorTexture.getHeight() / 2;
        mouseCursor = Gdx.graphics.newCursor(mouseCursorTexture, xHotSpot, yHotSpot);
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
                    rooms.getCurrentRoom().getCat().eat();
                }
                else if(currentTool == HAND_TOOL){
                    rooms.getCurrentRoom().getCat().pet();
                }
                else if(currentTool == MOUSE_TOY_TOOL){
                    System.out.println("true");
                    rooms.getCurrentRoom().getCat().pet();
                    rooms.getCurrentRoom().getCat().pet();
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
            else if(handButton.getBounds().contains(x,y)){
                if(currentTool == HAND_TOOL) setCurrentTool(NO_TOOL);
                else setCurrentTool(HAND_TOOL);
            }
            else if(mouseButton.getBounds().contains(x,y)){
                if(currentTool == MOUSE_TOY_TOOL) setCurrentTool(NO_TOOL);
                else setCurrentTool(MOUSE_TOY_TOOL);
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
        cat.render(sb, cam);

        //TOP LAYER - UI

        ui.render(sb, sr, transitioning);

        sb.end();
        sr.end();
    }

    public void setCurrentTool(short currentTool) {
        System.out.println(currentTool);
        this.currentTool = currentTool;
        switch (currentTool){
            case NO_TOOL: {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                break;
            }
            case DUSTBIN_TOOL: {
                break;
            }
            case HAND_TOOL: {
                Gdx.graphics.setCursor(handCursor);
                break;
            }
            case FOOD_BOWL_TOOL: {
                Gdx.graphics.setCursor(foodBowlCursor);
                break;
            }
            case MOUSE_TOY_TOOL: {
                Gdx.graphics.setCursor(mouseCursor);
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
