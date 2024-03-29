package com.moonjew.mochiclicker.room;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.moonjew.mochiclicker.entities.Cat;
import com.moonjew.mochiclicker.entities.Mess;

import java.util.ArrayList;

public class RoomCarousel {
    public static final Texture[] roomTextures = genRoomTextures();

    private MainRoom mainRoom;
    private OutsideRoom outsideRoom;
    public ArrayList<Room> rooms;
    private int currentRoom;

    public RoomCarousel() {
        rooms = new ArrayList<>();
        currentRoom = 0;
        this.mainRoom = new MainRoom(2);
        this.outsideRoom = new OutsideRoom(1);
    }

    public void update(final float deltaTime) {
        mainRoom.update(deltaTime);
        outsideRoom.update(deltaTime);
        for(Room room : rooms) {
            room.update(deltaTime);
        }
    }

    public Cat isCatDying() { //returns first room with a dying cat
        for(Room room : rooms) {
            if (room.getCat() != null) {
                if (room.getCat().isDying()) {
                    return room.getCat();
                }
            }
        }
        return null;
    }

    public void sendCatToMainRoom() {
        mainRoom.addCat(getCurrentRoom().getCat());
        getCurrentRoom().getCat().sendToMainRoom();
        getCurrentRoom().killCat();
    }
    public void sendCatOutside(){
        outsideRoom.sendCatOutside(getCurrentRoom());
        getCurrentRoom().getCat().sendOutside();
    }

    public void renderBackgrounds(SpriteBatch sb, Camera cam, int transitioning){
        Rectangle rectangle = getCurrentRoom().getRectangle();

        sb.draw(roomTextures[getCurrentRoom().roomTexture], cam.position.x + rectangle.x, cam.position.y + rectangle.y,
                rectangle.width, rectangle.height);

        getCurrentRoom().renderDecorations(sb, cam);

        if (transitioning == 1) { //to the right
            Rectangle rightRectangle = getRightRoom().getRectangle();
            sb.draw(roomTextures[getRightRoom().roomTexture], cam.position.x + rightRectangle.x + rectangle.width + 50,
                    cam.position.y + rightRectangle.y, rightRectangle.width, rightRectangle.height);
        } else if (transitioning == -1) { //to the right
            Rectangle leftRectangle = getLeftRoom().getRectangle();
            sb.draw(roomTextures[getLeftRoom().roomTexture], cam.position.x + leftRectangle.x - rectangle.width - 50,
                    cam.position.y + leftRectangle.y, leftRectangle.width, leftRectangle.height);
        }
    }

    public void renderForeground(SpriteBatch sb, Camera cam){
        if(getCurrentRoom().cat != null) {
            if(!getCurrentRoom().cat.isOutside()) getCurrentRoom().cat.render(sb, cam);
        }
        for(Mess mess : getCurrentRoom().messList){
            mess.render(sb);
        }
    }


    public OutsideRoom getOutsideRoom() {
        return outsideRoom;
    }

    public Room getCurrentRoom(){
        return rooms.get(currentRoom);
    }

    public Room getRightRoom() {
        moveRight();
        Room temp = getCurrentRoom();
        moveLeft();
        return temp;
    }
    public Room getLeftRoom() {
        moveLeft();
        Room temp = getCurrentRoom();
        moveRight();
        return temp;
    }

    public void moveLeft(){
        currentRoom--;
        if(currentRoom < 0){
            currentRoom = rooms.size()-1;
        }
    }

    public MainRoom getMainRoom() {
        return mainRoom;
    }

    public void moveRight(){
        currentRoom++;
        if(currentRoom >= rooms.size()){
            currentRoom = 0;
        }
    }

    public void addRoom(Room room){
        rooms.add(room);
    }

    public void dispose() {
        for(Room room : rooms){
            room.dispose();
        }
    }

    private static Texture[] genRoomTextures(){
        return new Texture[]{
                new Texture("catroom.png"),
                new Texture("outside.png"),
                new Texture("mainroom.png")
        };
    }
}
