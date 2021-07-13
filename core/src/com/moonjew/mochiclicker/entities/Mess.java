package com.moonjew.mochiclicker.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Mess {
    public static final int NUM_MESS_TEXTURES = 3;
    public static int MAX_SIZE = 5;

    Texture texture;
    Rectangle bounds;
    int size;

    public Mess(Rectangle spawnPosition){
        int messTexture = (int) (Math.random()*NUM_MESS_TEXTURES);
        switch (messTexture){
            case 1:
                this.texture = new Texture("poo.png");
                break;
            case 2:
                this.texture = new Texture("corpse.png");
                break;
            default:
                this.texture = new Texture("vomit.png");
                break;
        }
        double posX = Math.random()*spawnPosition.width + spawnPosition.x;
        double posY = Math.random()*spawnPosition.height + spawnPosition.y;
        this.size = (int) (Math.random() * MAX_SIZE);
        this.bounds = new Rectangle((float) posX, (float)posY, texture.getWidth() * size/2.0f, texture.getHeight() * size/2.0f);
    }

    public void render(SpriteBatch sb){
        sb.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }
    public void clean(int value){
        this.size -= value;
        this.bounds.width = texture.getWidth() * size/2.0f;
        this.bounds.height = texture.getHeight() * size/2.0f;
        if(this.size <= 0){
            this.dispose();
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getSize() {
        return size;
    }

    public void dispose(){
        this.texture.dispose();
    }
}
