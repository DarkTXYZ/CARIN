package com.carin.APIDraft;

import javax.persistence.ElementCollection;
import java.util.List;
import java.util.Map;

public class GameData {
    private int state;
    private List<Integer> posX,posY,type,shopState;
    private int objective , objectiveMax;
    private int m,n;

    public GameData(int state, List<Integer> posX, List<Integer> posY, List<Integer> type, List<Integer> shopState, int objective, int objectiveMax, int m, int n) {
        this.state = state;
        this.posX = posX;
        this.posY = posY;
        this.type = type;
        this.shopState = shopState;
        this.objective = objective;
        this.objectiveMax = objectiveMax;
        this.m = m;
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<Integer> getPosX() {
        return posX;
    }

    public void setPosX(List<Integer> posX) {
        this.posX = posX;
    }

    public List<Integer> getPosY() {
        return posY;
    }

    public void setPosY(List<Integer> posY) {
        this.posY = posY;
    }

    public List<Integer> getType() {
        return type;
    }

    public void setType(List<Integer> type) {
        this.type = type;
    }

    public List<Integer> getShopState() {
        return shopState;
    }

    public void setShopState(List<Integer> shopState) {
        this.shopState = shopState;
    }

    public int getObjective() {
        return objective;
    }

    public void setObjective(int objective) {
        this.objective = objective;
    }

    public int getObjectiveMax() {
        return objectiveMax;
    }

    public void setObjectiveMax(int objectiveMax) {
        this.objectiveMax = objectiveMax;
    }
}
