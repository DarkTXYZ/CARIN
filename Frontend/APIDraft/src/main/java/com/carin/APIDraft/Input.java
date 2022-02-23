package com.carin.APIDraft;

public class Input {

    private int clickState;
    private String clickType;
    private int posX_placement, posY_placement;

    public Input(int clickState, String clickType, int posX_placement, int posY_placement) {
        this.clickState = clickState;
        this.clickType = clickType;
        this.posX_placement = posX_placement;
        this.posY_placement = posY_placement;
    }

    public int getClickState() {
        return clickState;
    }

    public void setClickState(int clickState) {
        this.clickState = clickState;
    }

    public String getClickType() {
        return clickType;
    }

    public void setClickType(String clickType) {
        this.clickType = clickType;
    }

    public int getPosX_placement() {
        return posX_placement;
    }

    public void setPosX_placement(int posX_placement) {
        this.posX_placement = posX_placement;
    }

    public int getPosY_placement() {
        return posY_placement;
    }

    public void setPosY_placement(int posY_placement) {
        this.posY_placement = posY_placement;
    }
}
