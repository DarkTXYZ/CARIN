package com.carin.APIDraft;

public class Input {
    private boolean isBuy;
    private int posX_placement, posY_placement;

    public Input(boolean isBuy, int posX_placement, int posY_placement) {
        this.isBuy = isBuy;
        this.posX_placement = posX_placement;
        this.posY_placement = posY_placement;
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
}
