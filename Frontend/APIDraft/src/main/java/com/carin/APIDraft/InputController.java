package com.carin.APIDraft;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "input")
public class InputController {

    @CrossOrigin
    @GetMapping
    public Input getInput() {
        return InputService.getInput();
    }

    @CrossOrigin
    @GetMapping("/state")
    public int getState() {
        return InputService.getState();
    }

    @CrossOrigin
    @PutMapping("/put/state")
    public Input putInputState(@RequestBody Input input) {
        InputService.setInputState(input);
        return InputService.getInput();
    }

    @CrossOrigin
    @PutMapping("/put/type")
    public Input putInputType(@RequestBody Input input) {
        InputService.setType(input);
        return InputService.getInput();
    }

    @CrossOrigin
    @PutMapping("/put/pos")
    public Input putInputPos(@RequestBody Input input) {
        InputService.setPos(input);
        return InputService.getInput();
    }

}
