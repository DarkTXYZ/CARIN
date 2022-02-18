package com.carin.APIDraft;

import org.springframework.stereotype.Service;

@Service
public class InputService {

    private static Input input = new Input(false, -1, -1);

    public static Input getInput() {
        return input;
    }

    public static void setInput(Input input) {
        InputService.input = input;
    }
}
