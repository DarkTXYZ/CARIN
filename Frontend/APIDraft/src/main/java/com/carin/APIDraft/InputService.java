package com.carin.APIDraft;

import org.springframework.stereotype.Service;

@Service
public class InputService {

    private static Input input = new Input(0,"",-1,-1,0,-1,-1,-1,-1,0,0);

    public static Input getInput() {
        return input;
    }

    public static int getClickState() {
        return input.getClickState();
    }

    public static void setClickState(Input input) {
        InputService.input.setClickState(input.getClickState());
    }

    public static void setJob(Input input) {
        InputService.input.setJob(input.getJob());
    }

    public static void setPos(Input input) {
        InputService.input.setPosX_place(input.getPosX_place());
        InputService.input.setPosY_place(input.getPosY_place());
    }

    public static void setPauseState(Input input) {
        InputService.input.setPauseState(input.getPauseState());
    }

    public static int getPauseState() {
        return input.getPauseState();
    }

    public static void setSpeedState(Input input) {
        InputService.input.setSpeedState(input.getSpeedState());
    }

    public static int getSpeedState() {
        return input.getSpeedState();
    }

}
