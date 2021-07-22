package com.moonjew.mochiclicker.entities;

public class CatState {
    float maxTime; // How long the state will be active
    float timer; // How long the state has been active
    float timeModifier; // How fast time passes
    boolean finished; // If the state has finished
    CatStateType type;

    public enum CatStateType {
        DEFAULT,
        SLEEPING,
        DYING,
        IDLE,
        EATING,
        OUTSIDE
    }

    public static CatState IDLE = new CatState(CatStateType.IDLE);
    public static CatState DEFAULT = new CatState(CatStateType.DEFAULT);
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

    public float getTimeRemaining(){
        return maxTime - timer;
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
        timer += deltaTime * timeModifier;
        if(timer > maxTime){
            timer = 0;
            finished = true;
            return true;
        }
        return false;
    }

    public CatStateType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "CatState{" +
                "finished=" + finished +
                ", type=" + type +
                '}';
    }
}
