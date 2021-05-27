package com.moonjew.mochiclicker.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
    public void update(float deltaTime){
        states.peek().update(deltaTime);
    }
    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }

}
