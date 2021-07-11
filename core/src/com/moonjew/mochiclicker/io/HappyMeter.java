package com.moonjew.mochiclicker.io;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class HappyMeter {
    Rectangle textureRect;
    Texture sourceTexture;
    TextureRegion[] textures;
    public HappyMeter(Texture sourceTexture, Rectangle textureRect){
        this.textureRect = textureRect;
        this.sourceTexture = sourceTexture;
        this.textures = new TextureRegion[4];
        int width = sourceTexture.getWidth()/4;
        int height = sourceTexture.getHeight();
        for(int i = 0; i < 4; i++){
            this.textures[i] = new TextureRegion(sourceTexture, width*i, 0, width, height);
        }
    }


    public void render(SpriteBatch sb, int index){
        sb.draw(textures[index], textureRect.x, textureRect.y, textureRect.width, textureRect.height);
    }

}
