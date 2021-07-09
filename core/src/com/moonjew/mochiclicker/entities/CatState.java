package com.moonjew.mochiclicker.entities;

public class CatState {
    float maxTime;
    float timer;
    float timeModifier;
    boolean finished;
    CatStateType type;

    public enum CatStateType {
        DEFAULT,
        SLEEPING,
        DYING,
        IDLE,
        EATING,
        OUTSIDE
    }

    public CatState(CatStateType type, float maxTime, float timeModifier) {
        this.type = type;
        this.maxTime = maxTime;
        this.timer = 0;
        this.timeModifier = timeModifier;
        this.finished = false;
    }
    public CatState(CatStateType type, float maxTime) {
        this.type = type;
        this.maxTime = maxTime;
        this.timer = 0;
        this.timeModifier = 1;
        this.finished = false;
    }
    public CatState(CatStateType type) {
        this.type = type;
        this.maxTime = Integer.MAX_VALUE;
        this.timer = 0;
        this.timeModifier = 0;
        this.finished = false;
    }

    public boolean isFinished() {
        return finished;
    }

    public float getTimer() {
        return timer;
    }

    public void setMaxTime(float maxTime) {
        this.maxTime = maxTime;
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }

    public boolean update(float deltaTime){
        timer += deltaTime;
        if(timer > maxTime){
            timer = 0;
            finished = true;
            return true;
        }
        return false;
    }

}
