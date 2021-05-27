package com.moonjew.mochiclicker;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.Locale;

public class Font {
    public static final int SPACING = 5;
    private static final int DEFAULT_WIDTH = 7;
    private static final int DEFAULT_HEIGHT = 7;
    TextureRegion[] chars;

    public Font(Texture source){
        chars = new TextureRegion[39]; // 39
        int cnt = 0;
        int y = 0;
        int x = 0;
        for(int i = 0; i < 39; i++){
            chars[i] = new TextureRegion(source, x * 7, y*7, 7, 7);
            x++;
            if(x >= 16){
                x = 0;
                y++;
            }
        }
        System.out.println(chars.length);
//        chars[0] = new TextureRegion(source, 0, 0, 7, 7);
    }

    public void test(SpriteBatch batch){
        batch.begin();
//        batch.draw(test, 0, 0, MochiClicker.WIDTH, MochiClicker.HEIGHT);
//        batch.draw(chars[0], 0, 0, chars[0].getRegionWidth() * 10, chars[0].getRegionHeight() * 10);
        for(int j = 0; j < 50; j++) {
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] != null) {
                    batch.draw(chars[i], i * 16, MochiClicker.HEIGHT - j * 16, 16, 16);
                } else {
                    System.out.println(i);
                }
            }
        }
        batch.end();
    }

    public void draw(SpriteBatch batch, String text, float x, float y, float xScale, float yScale) {
        float posX = x;
        char[] line = text.toUpperCase(Locale.ROOT).toCharArray();
        batch.begin();
        for(char c : line){
            if(c < 65) {
                //c is a number
                int letter = c - 48;
                System.out.println(letter);
                batch.draw(chars[letter], posX, y, DEFAULT_WIDTH * xScale, DEFAULT_HEIGHT * yScale);
            }
            else {
                //c is a letter
                int letter = c - 65 + 10;
                batch.draw(chars[letter], posX, y, xScale, yScale);
            }
            posX += DEFAULT_WIDTH * xScale + SPACING;

        }
        batch.end();
    }
    public void draw(SpriteBatch batch, String text, Rectangle bounds, float xScale, float yScale){
        float posX = bounds.x;
        float posY = bounds.y;
        char[] line = text.toUpperCase(Locale.ROOT).toCharArray();
        batch.begin();
        for(char c : line){
            if(c < 65) {
                //c is a number
                int letter = c - 48;
                batch.draw(chars[letter], posX, bounds.y, DEFAULT_WIDTH * xScale, DEFAULT_HEIGHT * yScale);
            }
            else {
                //c is a letter
                int letter = c - 65 + 10;
                batch.draw(chars[letter], posX, bounds.y, xScale, yScale);
            }
            posX += xScale + SPACING;
            if(posX + DEFAULT_WIDTH > bounds.x + bounds.width){ //wrap
                posX = bounds.x;
                posY += DEFAULT_HEIGHT * yScale;
            }
        }
        batch.end();
    }

}
