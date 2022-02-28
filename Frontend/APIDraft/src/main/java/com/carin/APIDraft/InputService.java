package com.carin.APIDraft;

import org.springframework.stereotype.Service;

@Service
public class InputService {

    private static final Input input = new Input(-1, -1, 0, "", -1, -1, 0, -1, -1, -1, -1, 0, 0);

    public static Input getInput() {
        return input;
    }

    public static int getSelectedX() {
        return input.getSelectedX();
    }

    public static int getSelectedY() {
        return input.getSelectedY();
    }

    public static void setSelected(Input input) {
        InputService.input.setSelectedX(input.getSelectedX());
        InputService.input.setSelectedY(input.getSelectedY());
    }

    public static int getPlaceState() {
        return input.getPlaceState();
    }

    public static void setPlaceState(Input input) {
        InputService.input.setPlaceState(input.getPlaceState());
    }

    public static String getJob() {
        return input.getJob();
    }

    public static void setJob(Input input) {
        InputService.input.setJob(input.getJob());
    }

    public static int getPosX_place() {
        return input.getPosX_place();
    }

    public static int getPosY_place() {
        return input.getPosY_place();
    }

    public static void setPosPlace(Input input) {
        InputService.input.setPosX_place(input.getPosX_place());
        InputService.input.setPosY_place(input.getPosY_place());
    }

    public static int getMoveState() {
        return input.getMoveState();
    }

    public static void setMoveState(Input input) {
        InputService.input.setMoveState(input.getMoveState());
    }

    public static int getOgX() {
        return input.getOgX();
    }

    public static int getOgY() {
        return input.getOgY();
    }

    public static void setOg(Input input) {
        InputService.input.setOgX(input.getOgX());
        InputService.input.setOgY(input.getOgY());
    }

    public static int getPosX_move() {
        return input.getPosX_move();
    }

    public static int getPosY_move() {
        return input.getPosY_move();
    }

    public static void setPosMove(Input input) {
        InputService.input.setPosX_move(input.getPosX_move());
        InputService.input.setPosY_move(input.getPosY_move());
    }

    public static int getPauseState() {
        return input.getPauseState();
    }

    public static void setPauseState(Input input) {
        InputService.input.setPauseState(input.getPauseState());
    }

    public static int getSpeedState() {
        return input.getSpeedState();
    }

    public static void setSpeedState(Input input) {
        InputService.input.setSpeedState(input.getSpeedState());
    }


}
