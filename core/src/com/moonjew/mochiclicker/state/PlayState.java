package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.moonjew.mochiclicker.RoomCarousel;
import com.moonjew.mochiclicker.io.Button;
import com.moonjew.mochiclicker.io.Font;
import com.moonjew.mochiclicker.MochiClicker;
import com.moonjew.mochiclicker.Room;
import com.moonjew.mochiclicker.entities.Cat;
import com.moonjew.mochiclicker.io.ShopButton;

public class PlayState extends State{
    Font font;
    RoomCarousel rooms;
    ShapeRenderer renderer;
    int currentRoom;
    int catNip;
    Button button;
    int transitioning; // 0 = not transitioning, 1 = right, -1 = left

    public PlayState(GameStateManager gsm) {
        super(gsm);
        font = new Font();
        renderer = new ShapeRenderer();

        currentRoom = 0;
        rooms = new RoomCarousel();
        rooms.addRoom(new Room(Color.PURPLE));
        rooms.addRoom(new Room(Color.ROYAL));

        catNip = 0;
        button = new ShopButton(new Rectangle(50, MochiClicker.HEIGHT-50, 100, 50), font, gsm);
        transitioning = 0;
        cam.setToOrtho(false, MochiClicker.WIDTH, MochiClicker.HEIGHT);
        cam.position.x = 0;
        cam.position.y = 0;
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
                catNip++;
            }

            if(button.getBounds().contains(x, y)){
                System.out.println("True");
                button.onclick();
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
            cam.position.x -= 100 * deltaTime;
            if(cam.position.x < -rooms.getCurrentRoom().getRectangle().width - 50){
                transitioning = 0;
                rooms.moveRight();
                cam.position.x = 0;
                cam.position.y = 0;
            }
        }
        if(transitioning == -1) {
            cam.position.x += 100 * deltaTime;
            if(cam.position.x > rooms.getCurrentRoom().getRectangle().width + 50){
                transitioning = 0;
                rooms.moveLeft();
                cam.position.x = 0;
                cam.position.y = 0;
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        renderer.setProjectionMatrix(cam.combined);
        Rectangle rectangle = rooms.getCurrentRoom().getRectangle();

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(rooms.getCurrentRoom().getColor());
        renderer.rect(cam.position.x + rectangle.x, cam.position.y + rectangle.y, rectangle.width, rectangle.height);
        if(transitioning == 1){
            renderer.setColor(rooms.getRightRoom().getColor());
            Rectangle rightRectangle = rooms.getRightRoom().getRectangle();
            renderer.rect(cam.position.x + rightRectangle.x + rectangle.width + 50, cam.position.y + rightRectangle.y, rightRectangle.width, rightRectangle.height);
        }
        if(transitioning == -1){
            renderer.setColor(rooms.getLeftRoom().getColor());
            Rectangle leftRectangle = rooms.getLeftRoom().getRectangle();
            renderer.rect(cam.position.x + leftRectangle.x - rectangle.width - 50, cam.position.y + leftRectangle.y, leftRectangle.width, leftRectangle.height);
        }
        renderer.end();

        sb.begin();
        Cat cat = rooms.getCurrentRoom().getCat();
        sb.draw(cat.getTexture(), cam.position.x + cat.getPosition().x, cam.position.y + cat.getPosition().y, cat.getPosition().width, cat.getPosition().height);

        font.draw(sb, "Catnip " + catNip, new Rectangle(50, MochiClicker.HEIGHT - 100, 200, 200), 2, 2);
        font.draw(sb, "Tired " + (int) (rooms.getCurrentRoom().getCat().getTired()),
                new Rectangle(50, MochiClicker.HEIGHT-200, 200, 200), 2, 2);
        button.render(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        font.dispose();
        rooms.dispose();
    }
}
