package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.*;
import com.moonjew.mochiclicker.entities.Mess;
import com.moonjew.mochiclicker.io.*;
import com.moonjew.mochiclicker.io.button.*;
import com.moonjew.mochiclicker.room.Decoration;
import com.moonjew.mochiclicker.room.Room;
import com.moonjew.mochiclicker.room.RoomCarousel;

import java.util.ListIterator;

import static com.moonjew.mochiclicker.state.ToolType.*;

public class PlayState extends State {
    public static int catNip;
    RoomCarousel rooms;
    UI ui;
    int currentRoom;
    ShopButton shopButton;
    SidebarButton sidebarButton;

    ConditionalButton[] menuButtons;
    ConditionalButton healYesButton, healNoButton;
    ConditionalButton sendCatToMainRoomButton;
    ConditionalButton buyCatYesButton, buyCatNoButton;

    Button foodBowlButton;
//    Button handButton;
    ToolButtonTree handButtonTree;
    Button mouseButton;
    Button cleaningButton;
    int transitioning; // 0 = not transitioning, 1 = right, -1 = left
    boolean menu;

    ToolType currentTool; //0 = no tool, 1 = dustbin, 2 = toy bin, 3 = food bowl

    Cursor cleaningCursor;
    Cursor handCursor;
    Cursor foodBowlCursor;
    Cursor mouseCursor;

//    SoundEffect mrow = new SoundEffect("mrow.wav", 1, 1.0f, 0);
    Music music;
    Texture background;

    public PlayState(GameStateManager gsm) {
        super(gsm);

        ui = new UI();
        currentTool = NO_TOOL;

        currentRoom = 0;
        rooms = new RoomCarousel();
        Room firstRoom = new Room(gsm);
        firstRoom.genCat();
        rooms.addRoom(firstRoom);
        rooms.addRoom(new Room(gsm));

        transitioning = 0;
        ui.setCat(rooms.getCurrentRoom().getCat());

        catNip = 500;

//        shopButton = new ShopButton(new Rectangle(MochiClicker.WIDTH * 0.84f, MochiClicker.HEIGHT * .79f, 100, 50), gsm, rooms.getCurrentRoom());
        shopButton = new ShopButton(new Rectangle(MochiClicker.WIDTH -100, MochiClicker.HEIGHT - 150, 100, 50), gsm, rooms.getCurrentRoom());
        sendCatToMainRoomButton = new ConditionalButton("Send Cat To Main Room", new Rectangle(MochiClicker.WIDTH -100, MochiClicker.HEIGHT - 300, 100, 50));
        menuButtons = new ConditionalButton[]{shopButton, sendCatToMainRoomButton};

        sidebarButton = new SidebarButton(new Rectangle(MochiClicker.WIDTH-50, MochiClicker.HEIGHT-50, 32, 32));
        foodBowlButton = new Button(new Texture("food_bowl_button.png"), new Rectangle(MochiClicker.WIDTH / 2.0f + 40, 25, 64, 64));
//        handButton = new Button(new Texture("hand_button.png"), new Rectangle(MochiClicker.WIDTH / 2.0f - 40, 25, 64, 64));
//        mouseButton = new Button(new Texture("mouse_button.png"), new Rectangle(MochiClicker.WIDTH/2-43, 100, 20, 20));
        cleaningButton = new Button(new Texture("cleaning_button.png"), new Rectangle(MochiClicker.WIDTH / 2.0f - 120, 25, 64, 64));
        healYesButton = new ConditionalButton("Yes", new Rectangle(MochiClicker.WIDTH/2.0f-75, 125, 50, 50));
        healNoButton = new ConditionalButton("No", new Rectangle(MochiClicker.WIDTH/2.0f, 125, 50, 50));
        buyCatYesButton = new ConditionalButton(new Texture("yes_button.png"), new Rectangle(MochiClicker.WIDTH/3.0f + 100, MochiClicker.HEIGHT/2.5f, 200, 50));

        handButtonTree = new ToolButtonTree(HAND_TOOL, new Button(new Texture("hand_button.png"), new Rectangle(MochiClicker.WIDTH / 2.0f - 40, 25, 64, 64)),
                new ConditionalButton[]{
                        new ConditionalButton(new Texture("hand_button.png"), new Rectangle(MochiClicker.WIDTH/2-40, 100, 20, 20)),
                        new ConditionalButton(new Texture("mouse_button.png"), new Rectangle(MochiClicker.WIDTH/2-15, 100, 20, 20)),
                });

        ui.addButtons(new Button[]{shopButton, sidebarButton, foodBowlButton, cleaningButton, healYesButton, healNoButton, sendCatToMainRoomButton, buyCatYesButton, handButtonTree.getMainButton()}); //adding buttons to the UI
        ui.addButtons(handButtonTree.getButtons());
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

        Pixmap mouseCursorTexture = new Pixmap(Gdx.files.internal("mouse_toy.png"));
        xHotSpot = mouseCursorTexture.getWidth() / 2;
        yHotSpot = mouseCursorTexture.getHeight() / 2;
        mouseCursor = Gdx.graphics.newCursor(mouseCursorTexture, xHotSpot, yHotSpot);

        Pixmap cleaningCursorTexture = new Pixmap(Gdx.files.internal("mop.png"));
        xHotSpot = cleaningCursorTexture.getWidth() / 2;
        yHotSpot = cleaningCursorTexture.getHeight() / 2;
        cleaningCursor = Gdx.graphics.newCursor(cleaningCursorTexture, xHotSpot, yHotSpot);

        music = Gdx.audio.newMusic(Gdx.files.internal("audio/soundtrack_final.wav"));
        music.setLooping(true);
        music.setPosition(0.0f);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            transitioning = 1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            transitioning = -1;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            rooms.getCurrentRoom().getCat().sendOutside();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            gsm.push(new MainRoomState(gsm, rooms.getMainRoom()));
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
            music.play();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            rooms.sendCatOutside();
            gsm.push(new OutsideState(gsm, rooms.getOutsideRoom()));
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }

        if(Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = MochiClicker.HEIGHT - Gdx.input.getY();
            if(rooms.getCurrentRoom().getCat() != null) {
                if (rooms.getCurrentRoom().getCat().touch(x, y) && !rooms.getCurrentRoom().getCat().isSleeping()) {
                    // hit cat
                    if (currentTool.equals(NO_TOOL)) {
                        catNip += rooms.getCurrentRoom().getCat().getLevel();
                    } else if (currentTool.equals(FOOD_BOWL_TOOL)) {
                        rooms.getCurrentRoom().getCat().eat();
                    } else if (currentTool.equals(HAND_TOOL)) {
                        rooms.getCurrentRoom().getCat().pet();
                    } else if (currentTool.equals(MOUSE_TOY_TOOL)) {
                        rooms.getCurrentRoom().getCat().pet();
                        rooms.getCurrentRoom().getCat().pet();
                    }
                }
            }
            else {
                if (buyCatYesButton.getBounds().contains(x, y)){
                    if(catNip >= 50) {
                        catNip -= 50;
                        rooms.getCurrentRoom().genCat();
                    }
                }
            }
            if (currentTool.equals(CLEANING_TOOL)){
                ListIterator<Mess> messListIterator = rooms.getCurrentRoom().getMessList().listIterator();
                while(messListIterator.hasNext()){
                    Mess mess = messListIterator.next();
                    if(mess.getBounds().contains(x, y)){
                        mess.clean((int) rooms.getCurrentRoom().getDecorationValue(Decoration.DecorationType.LITTER_BOX));
                        if(mess.getSize() <= 0){
                            messListIterator.remove();
                            rooms.getCurrentRoom().getCat().increaseHealth();
                        }
                    }
                }
            }
            if(sidebarButton.getBounds().contains(x,y)){
                System.out.println("Sidebar button pressed");
                setMenu(!menu);
                setCurrentTool(NO_TOOL);
            }
            if(menu) {
                if (shopButton.getBounds().contains(x, y) && menu) {
                    shopButton.setRoom(rooms.getCurrentRoom());
                    shopButton.onclick();
                    setMenu(!menu);
                }
                else if (sendCatToMainRoomButton.getBounds().contains(x, y)) {
                    rooms.sendCatToMainRoom();
                }
            }
            else if(foodBowlButton.getBounds().contains(x,y)){
                if(currentTool.equals( FOOD_BOWL_TOOL)) setCurrentTool(NO_TOOL);
                else setCurrentTool(FOOD_BOWL_TOOL);
            }
            else if(cleaningButton.getBounds().contains(x, y)){
                if(currentTool.equals(CLEANING_TOOL)) setCurrentTool(NO_TOOL);
                else setCurrentTool(CLEANING_TOOL);
            }
            ToolType type = handButtonTree.onclick(x,y);
            if(type != null) {
                setCurrentTool(type);
            }
//            else if(handButton.getBounds().contains(x,y)){
//                if(currentTool.equals( HAND_TOOL)) setCurrentTool(NO_TOOL);
//                else setCurrentTool(HAND_TOOL);
//            }
//            else if(mouseButton.getBounds().contains(x,y)){
//                if(currentTool.equals( MOUSE_TOY_TOOL)) setCurrentTool(NO_TOOL);
//                else setCurrentTool(MOUSE_TOY_TOOL);
             else if(healYesButton.willRender && healYesButton.getBounds().contains(x,y)){
                rooms.getCurrentRoom().getCat().heal();
                ui.dyingCat = rooms.isCatDying();
                setAlert(false);
            }
            else if(healNoButton.willRender && healNoButton.getBounds().contains(x,y)){
                rooms.getCurrentRoom().killCat();
                ui.dyingCat = rooms.isCatDying();
                setAlert(false);
            }

            Gdx.app.setLogLevel(Application.LOG_DEBUG);
            Gdx.app.debug("POSITION", "X touched: " + x + " Y touched: " + y);

        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        rooms.update(deltaTime);
        ui.dyingCat = rooms.isCatDying();
        if(rooms.getCurrentRoom().getCat() != null){
            setAlert(rooms.getCurrentRoom().getCat().equals(ui.dyingCat)); //in the same room as the dying cat
        }

        buyCatYesButton.willRender = rooms.getCurrentRoom().getCat() == null;

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
        background = new Texture("basicbg.png");
        sb.begin();
        sb.draw(background, 0, 0, MochiClicker.WIDTH, MochiClicker.HEIGHT);
        sb.setProjectionMatrix(cam.combined);
        sb.end();
        sr.setProjectionMatrix(cam.combined);
        sr.setAutoShapeType(true);



        //BOTTOM LAYER - BACKGROUNDS

        sb.begin();
        rooms.renderBackgrounds(sb, cam, transitioning);
        sb.end();

        //MIDDLE LAYER - ENTITIES, EFFECTS

        sb.begin();
        rooms.renderForeground(sb, cam);
        sb.end();

        //TOP LAYER - UI

        sr.begin(ShapeRenderer.ShapeType.Filled);
        ui.render(rooms.getCurrentRoom().getCat(), sr);
        sr.end();

        sb.begin();
        ui.render(rooms.getCurrentRoom().getCat(), sb, transitioning);
        sb.end();


    }

    public void setCurrentTool(ToolType currentTool) {
        System.out.println(currentTool);
        this.currentTool = currentTool;
        switch (currentTool){
            case NO_TOOL: {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                break;
            }
            case CLEANING_TOOL: {
                Gdx.graphics.setCursor(cleaningCursor);
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

    public void setMenu(boolean menu) {
        this.menu = menu;
        if(menu){
            cam.position.x = -100;
            sidebarButton.getBounds().x = MochiClicker.WIDTH - 150;

        } else {
            cam.position.x = 0;
            sidebarButton.getBounds().x = MochiClicker.WIDTH - 50;
        }
        for (ConditionalButton menuButton : menuButtons) {
            menuButton.willRender = menu;
        }
        ui.menu = menu;
    }

    public void setAlert(boolean alert){
        healYesButton.willRender = alert;
        healNoButton.willRender = alert;

    }

    @Override
    public void dispose() {
        rooms.dispose();
        foodBowlCursor.dispose();
        handCursor.dispose();
        cleaningCursor.dispose();
        mouseCursor.dispose();
    }
}
