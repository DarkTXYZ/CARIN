package com.carin.APIDraft;

public class Input {

    private int clickState;

    private String job;
    private int posX_place, posY_place;

    private int pauseMoveState;
    private int ogX, ogY, posX, posY;

    private int speedState;

    private int pauseState;

    public Input(int clickState, String job, int posX_place, int posY_place, int pauseMoveState, int ogX, int ogY, int posX, int posY, int speedState, int pauseState) {
        this.clickState = clickState;
        this.job = job;
        this.posX_place = posX_place;
        this.posY_place = posY_place;
        this.pauseMoveState = pauseMoveState;
        this.ogX = ogX;
        this.ogY = ogY;
        this.posX = posX;
        this.posY = posY;
        this.speedState = speedState;
        this.pauseState = pauseState;
    }

    public int getClickState() {
        return clickState;
    }

    public void setClickState(int clickState) {
        this.clickState = clickState;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getPosX_place() {
        return posX_place;
    }

    public void setPosX_place(int posX_place) {
        this.posX_place = posX_place;
    }

    public int getPosY_place() {
        return posY_place;
    }

    public void setPosY_place(int posY_place) {
        this.posY_place = posY_place;
    }

    public int getPauseMoveState() {
        return pauseMoveState;
    }

    public void setPauseMoveState(int pauseMoveState) {
        this.pauseMoveState = pauseMoveState;
    }

    public int getOgX() {
        return ogX;
    }

    public void setOgX(int ogX) {
        this.ogX = ogX;
    }

    public int getOgY() {
        return ogY;
    }

    public void setOgY(int ogY) {
        this.ogY = ogY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getSpeedState() {
        return speedState;
    }

    public void setSpeedState(int speedState) {
        this.speedState = speedState;
    }

    public int getPauseState() {
        return pauseState;
    }

    public void setPauseState(int pauseState) {
        this.pauseState = pauseState;
    }
}
