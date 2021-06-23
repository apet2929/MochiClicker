package com.moonjew.mochiclicker.io;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.moonjew.mochiclicker.MochiClicker;

import java.util.ArrayList;
import java.util.Locale;

public class Font {
    public static final int SPACING_X = 0;
    public static final int SPACING_Y = 4;
    private static final int DEFAULT_WIDTH = 7;
    private static final int DEFAULT_HEIGHT = 7;
    TextureRegion[] chars;
    Texture source;

    public Font(){
        source = new Texture("font.png");
        chars = new TextureRegion[40]; // 39
        int cnt = 0;
        int y = 0;
        int x = 0;
        for(int i = 0; i < chars.length; i++){
            chars[i] = new TextureRegion(source, x * 7, y*7, 7, 7);
            x++;
            if(x >= 16){
                x = 0;
                y++;
            }
        }
        System.out.println(chars.length);
    }

    //Don't use this one, it sucks
    @Deprecated
    public void draw(SpriteBatch batch, String text, float x, float y, float xScale, float yScale) {
        float posX = x;
        char[] line = text.toUpperCase(Locale.ROOT).toCharArray();
        for(char c : line) {
            if (c < 65) {
                //c is a number
                int letter = c - 48;
                System.out.println(letter);
                batch.draw(chars[letter], posX, y, DEFAULT_WIDTH * xScale, DEFAULT_HEIGHT * yScale);
            } else {
                //c is a letter
                int letter = c - 65 + 10;
                batch.draw(chars[letter], posX, y, xScale, yScale);
            }
            posX += DEFAULT_WIDTH * xScale + SPACING_X;

        }
    }

    //Use this one instead
    public void draw(SpriteBatch batch, String text, Rectangle bounds, float xScale, float yScale){
        float posX = bounds.x;
        float posY = bounds.y;
        char[] line = text.toUpperCase(Locale.ROOT).toCharArray();

        int letter = 0;
        for(char c : line){
            letter = getLetter(c);
            try {
                batch.draw(chars[letter], posX, posY, DEFAULT_WIDTH * xScale, DEFAULT_HEIGHT * yScale);

                posX += DEFAULT_WIDTH * (xScale + SPACING_X);
                if (posX + (DEFAULT_WIDTH * xScale) > bounds.x + bounds.width) { //wrap
                    posX = bounds.x;
                    posY -= DEFAULT_HEIGHT * (yScale + SPACING_Y);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }

        }
    }

    //To draw something in the middle, I need to get the width of the total string
    //I can do this by taking each of the chars and adding their widths
    //Then, I just get the offset by subtracting the text width from the width of the bounds and dividing by 2
    //This does not account for word wrapping... Maybe I remove the rect and just have multiple .drawMiddle calls?
    //Or maybe I get the total width and the # of times it wraps is just width / rect width, and use that?
    //We'll see
    public void drawMiddle(SpriteBatch batch, String text, Rectangle rect, float xScale, float yScale){

        String[] words = text.toUpperCase(Locale.ROOT).split(" ");
        ArrayList<String> lines = new ArrayList<>();
        lines.add("");
        float currentLineWidth = 0; //Holds the current line's width
        Array<Float> lineWidths = new Array<>(); //Holds the width of every line
        int wraps = 0; // # of wraps

        for(int i = 0; i < words.length; i++){
            char[] word = words[i].toCharArray();
            char[] nextWord = null;
            if(i != words.length-1) {
                nextWord = words[i + 1].toCharArray();
            }

            float tempWidth = currentLineWidth + word.length * (DEFAULT_WIDTH * xScale); //accounting for the space at the end of the word
            tempWidth += DEFAULT_WIDTH * xScale + SPACING_X;

            if(tempWidth <= rect.width || lines.get(wraps).equals("")){ //Current word can fit
                currentLineWidth = tempWidth;
                String curLine = lines.get(wraps);
                lines.set(wraps, curLine + words[i] + " "); //add current word to list

                //Check if next word can fit, if not then wrap
                if(nextWord != null) {
                    tempWidth += nextWord.length * (DEFAULT_WIDTH * xScale);
                    tempWidth += DEFAULT_WIDTH * xScale + SPACING_X;
                    if (tempWidth >= rect.width) { //Word after current word CAN'T fit
                        lineWidths.add(currentLineWidth);
                        lines.add("");
                        currentLineWidth = 0;
                        wraps++;
                    }
                }
            } else { //current word CAN'T FIT, wrap
                i--; //make sure we don't skip the current word on the next line
                lineWidths.add(currentLineWidth);
                lines.add("");
                currentLineWidth = 0;
                wraps++;
            }
        }

        //Accounting for end of words, when currentLineWidth is not >= rect.width
        lineWidths.add(currentLineWidth);
        wraps++;

        float posX;
        float posY = rect.y + rect.height - (DEFAULT_HEIGHT * yScale);
        for(int i = 0; i < wraps; i++){
            float difference = rect.width - lineWidths.get(i);
            difference /= 2;
            posX = rect.x + difference;
            char[] word = lines.get(i).toCharArray();
            for(char c : word){
                int letter = getLetter(c);
                batch.draw(chars[letter], posX, posY, DEFAULT_WIDTH * xScale, DEFAULT_HEIGHT * yScale);
                posX += DEFAULT_WIDTH * xScale + SPACING_X;
            }
//            batch.draw(chars[getLetter(' ')], posX, posY, DEFAULT_WIDTH * xScale, DEFAULT_HEIGHT * yScale);
            posY -= DEFAULT_HEIGHT * (yScale + SPACING_Y);
        }
    }

    public int getLetter(char c){
        if(48 <= c && c <= 57) {
            //c is a number
            return c - 48;
        }
        else if(c >= 65 && c <= 90){
            //c is a letter
            return c - 65 + 10;
        }
        else {
            switch(c){
                case ' ' : {
                    return 39;
                }
                case 46: {
                    return 36; //period
                }
                case 33: {
                    return 38; //exclamation mark
                }
                case 63 : {
                    return 37; //question mark
                }
                default : {
                    System.out.println("Letter of " + c + " is not valid!");
                }
            }
        }
        System.out.println("Huh? How did this happen? Font class");
        return -1;
    }



    public void dispose(){
        source.dispose();
    }
}
