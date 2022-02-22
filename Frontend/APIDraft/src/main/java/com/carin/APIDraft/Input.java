package com.carin.APIDraft;

public class Input {
    private int state;
    private boolean isBuy;
    private String type;
    private int posX_placement, posY_placement;

    public Input(int state, boolean isBuy, String type, int posX_placement, int posY_placement) {
        this.state = state;
        this.isBuy = isBuy;
        this.type = type;
        this.posX_placement = posX_placement;
        this.posY_placement = posY_placement;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
