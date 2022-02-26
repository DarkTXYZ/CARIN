package com.carin.APIDraft;

public class Input {

    private int selectedX, selectedY;

    private int placeState;
    private String job;
    private int posX_place, posY_place;

    private int moveState;
    private int ogX, ogY, posX_move, posY_move;

    private int speedState;

    private int pauseState;

    public Input(int selectedX, int selectedY, int placeState, String job, int posX_place, int posY_place, int moveState, int ogX, int ogY, int posX_move, int posY_move, int speedState, int pauseState) {
        this.selectedX = selectedX;
        this.selectedY = selectedY;
        this.placeState = placeState;
        this.job = job;
        this.posX_place = posX_place;
        this.posY_place = posY_place;
        this.moveState = moveState;
        this.ogX = ogX;
        this.ogY = ogY;
        this.posX_move = posX_move;
        this.posY_move = posY_move;
        this.speedState = speedState;
        this.pauseState = pauseState;
    }

    public int getSelectedX() {
        return selectedX;
    }

    public void setSelectedX(int selectedX) {
        this.selectedX = selectedX;
    }

    public int getSelectedY() {
        return selectedY;
    }

    public void setSelectedY(int selectedY) {
        this.selectedY = selectedY;
    }

    public int getPlaceState() {
        return placeState;
    }

    public void setPlaceState(int placeState) {
        this.placeState = placeState;
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

    public int getMoveState() {
        return moveState;
    }

    public void setMoveState(int moveState) {
        this.moveState = moveState;
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

    public int getPosX_move() {
        return posX_move;
    }

    public void setPosX_move(int posX_move) {
        this.posX_move = posX_move;
    }

    public int getPosY_move() {
        return posY_move;
    }

    public void setPosY_move(int posY_move) {
        this.posY_move = posY_move;
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
