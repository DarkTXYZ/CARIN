package com.carin.APIDraft;

import org.springframework.stereotype.Service;

@Service
public class InputService {

    private static Input input = new Input(0,false, null,-1, -1);

    public static Input getInput() {
        return input;
    }

    public static int getState() {
        return input.getState();
    }

    public static void setInputState(Input input) {
        InputService.input.setState(input.getState());
    }

    public static void setType(Input input) {
        InputService.input.setType(input.getType());
    }

    public static void setPos(Input input) {
        InputService.input.setPosX_placement(input.getPosX_placement());
        InputService.input.setPosY_placement(input.getPosY_placement());
    }
}
