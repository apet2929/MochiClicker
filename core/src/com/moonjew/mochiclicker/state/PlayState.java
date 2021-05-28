package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.Font;
import com.moonjew.mochiclicker.Room;
import com.moonjew.mochiclicker.entities.Cat;


public class PlayState extends State{
    Font font;
    Room[] rooms;
    ShapeRenderer renderer;
    int currentRoom;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        font = new Font(new Texture("font.png"));
        renderer = new ShapeRenderer();

        currentRoom = 0;
        rooms = new Room[2];
        rooms[0] = new Room(Color.PURPLE);
        rooms[1] = new Room(Color.ROYAL);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            moveRight();
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        rooms[currentRoom].update(deltaTime);
    }

    @Override
    public void render(SpriteBatch sb) {
        Rectangle rectangle = rooms[currentRoom].getRectangle();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(rooms[currentRoom].getColor());
        renderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        renderer.end();
        sb.begin();
        Cat cat = rooms[currentRoom].getCat();
        sb.draw(cat.getTexture(), cat.getPosition().x, cat.getPosition().y, cat.getPosition().width, cat.getPosition().height);
        sb.end();
//        font.test(sb);
    }

    @Override
    public void dispose() {

    }

    public void moveRight(){
        currentRoom++;
        if (currentRoom >= rooms.length) {
            currentRoom = 0;
        }
        System.out.println("Current room is now " + currentRoom);
    }
}
