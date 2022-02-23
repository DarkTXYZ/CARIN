package com.carin.APIDraft;

import java.util.List;

public class GameData {
    private int m, n;
    private int state;
    private List<Integer> shopState, cost;
    private int currency;
    private List<Integer> posX, posY, hp, hpMax;
    private List<String> type;
    private int objective, objectiveMax;

    public GameData(int m, int n, int state, List<Integer> shopState, List<Integer> cost, int currency, List<Integer> posX, List<Integer> posY, List<Integer> hp, List<Integer> hpMax, List<String> type, int objective, int objectiveMax) {
        this.m = m;
        this.n = n;
        this.state = state;
        this.shopState = shopState;
        this.cost = cost;
        this.currency = currency;
        this.posX = posX;
        this.posY = posY;
        this.hp = hp;
        this.hpMax = hpMax;
        this.type = type;
        this.objective = objective;
        this.objectiveMax = objectiveMax;
    }

    public List<Integer> getCost() {
        return cost;
    }

    public void setCost(List<Integer> cost) {
        this.cost = cost;
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

    public List<Integer> getShopState() {
        return shopState;
    }

    public void setShopState(List<Integer> shopState) {
        this.shopState = shopState;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
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

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public List<Integer> getHp() {
        return hp;
    }

    public void setHp(List<Integer> hp) {
        this.hp = hp;
    }

    public List<Integer> getHpMax() {
        return hpMax;
    }

    public void setHpMax(List<Integer> hpMax) {
        this.hpMax = hpMax;
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
