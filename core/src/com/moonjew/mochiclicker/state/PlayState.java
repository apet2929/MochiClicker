package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
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

    public PlayState(GameStateManager gsm) {
        super(gsm);
        font = new Font();
        renderer = new ShapeRenderer();

        currentRoom = 0;
        rooms = new RoomCarousel();
        rooms.addRoom(new Room(Color.PURPLE));
        rooms.addRoom(new Room(Color.ROYAL));

        catNip = 0;
        button = new ShopButton(new Rectangle(0, MochiClicker.HEIGHT-50, 100, 50), font, gsm);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            rooms.moveRight();
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
    }

    @Override
    public void render(SpriteBatch sb) {
        Rectangle rectangle = rooms.getCurrentRoom().getRectangle();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(rooms.getCurrentRoom().getColor());
        renderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        renderer.end();
        sb.begin();
        Cat cat = rooms.getCurrentRoom().getCat();
        sb.draw(cat.getTexture(), cat.getPosition().x, cat.getPosition().y, cat.getPosition().width, cat.getPosition().height);
        font.draw(sb, "Catnip " + catNip, new Rectangle(50, MochiClicker.HEIGHT-100, 200, 200), 2, 2);
        font.draw(sb, "Tired " + rooms.getCurrentRoom().getCat().getTired(),
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
