package com.carin.APIDraft;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "input")
public class InputController {

    @GetMapping
    public Input getInput() {
        return InputService.getInput();
    }

    @PutMapping("/put")
    public Input putInput(@RequestBody Input input) {
        InputService.setInput(input);
        return InputService.getInput();
    }

}
