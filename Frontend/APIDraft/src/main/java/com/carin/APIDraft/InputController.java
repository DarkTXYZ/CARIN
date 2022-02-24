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
    @GetMapping("/clickstate")
    public int getClickState() {
        return InputService.getClickState();
    }

    @CrossOrigin
    @PutMapping("/put/clickstate")
    public Input putClickState(@RequestBody Input input) {
        InputService.setClickState(input);
        return InputService.getInput();
    }

    @CrossOrigin
    @PutMapping("/put/clicktype")
    public Input putClickType(@RequestBody Input input) {
        InputService.setClickType(input);
        return InputService.getInput();
    }

    @CrossOrigin
    @PutMapping("/put/pos")
    public Input putPos(@RequestBody Input input) {
        InputService.setPos(input);
        return InputService.getInput();
    }

}
