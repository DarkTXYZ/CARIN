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
    @PutMapping("/put/job")
    public Input putJob(@RequestBody Input input) {
        InputService.setJob(input);
        return InputService.getInput();
    }

    @CrossOrigin
    @PutMapping("/put/pos")
    public Input putPos(@RequestBody Input input) {
        InputService.setPos(input);
        return InputService.getInput();
    }

    @CrossOrigin
    @PutMapping("/put/pausestate")
    public Input putPauseState(@RequestBody Input input) {
        InputService.setPauseState(input);
        return InputService.getInput();
    }

    @CrossOrigin
    @GetMapping("/pausestate")
    public int getPauseState() {
        return InputService.getPauseState();
    }

    @CrossOrigin
    @PutMapping("/put/speedstate")
    public Input putSpeedState(@RequestBody Input input) {
        InputService.setSpeedState(input);
        return InputService.getInput();
    }

    @CrossOrigin
    @GetMapping("/speedstate")
    public int getSpeedState() {
        return InputService.getSpeedState();
    }


}
