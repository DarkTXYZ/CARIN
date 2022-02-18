package com.carin.APIDraft;

import javax.persistence.ElementCollection;
import java.util.List;
import java.util.Map;

public class GameData {
    private int state;
    private List<Integer> pos;

    @ElementCollection
    private Map<String, Integer> bindings;

    @ElementCollection
    private List<List<Integer>> order;

    public GameData(int state, List<Integer> pos, Map<String, Integer> bindings, List<List<Integer>> order) {
        this.state = state;
        this.pos = pos;
        this.bindings = bindings;
        this.order = order;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<Integer> getPos() {
        return pos;
    }

    public void setPos(List<Integer> pos) {
        this.pos = pos;
    }

    public Map<String, Integer> getBindings() {
        return bindings;
    }

    public void setBindings(Map<String, Integer> bindings) {
        this.bindings = bindings;
    }

    public List<List<Integer>> getOrder() {
        return order;
    }

    public void setOrder(List<List<Integer>> order) {
        this.order = order;
    }
}
