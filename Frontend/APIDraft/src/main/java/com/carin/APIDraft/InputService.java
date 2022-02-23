package com.carin.APIDraft;

import org.springframework.stereotype.Service;

@Service
public class InputService {

    private static Input input = new Input(0,null, -1,-1);

    public static Input getInput() {
        return input;
    }

    public static int getClickState() {
        return input.getClickState();
    }

    public static void setClickState(Input input) {
        InputService.input.setClickState(input.getClickState());
    }

    public static void setClickType(Input input) {
        InputService.input.setClickType(input.getClickType());
    }

    public static void setPos(Input input) {
        InputService.input.setPosX_placement(input.getPosX_placement());
        InputService.input.setPosY_placement(input.getPosY_placement());
    }
}
