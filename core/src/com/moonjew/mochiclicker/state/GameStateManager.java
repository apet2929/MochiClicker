package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Stack;

public class GameStateManager {

    private Stack<State> states;

    public GameStateManager() {
        this.states = new Stack<>();
    }

    public void push(State state){
        this.states.push(state);
    }
    public void pop(){
        states.pop().dispose();
    }
    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }
    public void update(float deltaTime) {
        states.peek().update(deltaTime);
        try {
            Thread.sleep((long) (1000 / 60 - Gdx.graphics.getDeltaTime()));
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void render(SpriteBatch sb, ShapeRenderer sr){
        states.peek().render(sb, sr);
    }

}
