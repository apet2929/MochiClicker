package com.moonjew.mochiclicker;

import java.util.ArrayList;
import java.util.Queue;

public class RoomCarousel {
    public ArrayList<Room> rooms;
    private int currentRoom;

    public RoomCarousel(){
        rooms = new ArrayList<>();
        currentRoom = 0;
    }

    public Room getCurrentRoom(){
        return rooms.get(currentRoom);
    }

    public void moveLeft(){
        currentRoom--;
        if(currentRoom < 0){
            currentRoom = rooms.size()-1;
        }
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



}
