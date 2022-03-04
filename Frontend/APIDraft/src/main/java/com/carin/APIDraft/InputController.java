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

    // Selected XY
    @CrossOrigin
    @GetMapping("/selectedx")
    public int getSelectedX() {
        return InputService.getSelectedX();
    }


    @CrossOrigin
    @GetMapping("/selectedy")
    public int getSelectedY() {
        return InputService.getSelectedY();
    }

    @CrossOrigin
    @PutMapping("/put/selected")
    public Input putSelectedY(@RequestBody Input input) {
        InputService.setSelected(input);
        return InputService.getInput();
    }

    // Place State
    @CrossOrigin
    @GetMapping("/placestate")
    public int getPlaceState() {
        return InputService.getPlaceState();
    }

    @CrossOrigin
    @PutMapping("/put/placestate")
    public Input getPlaceState(@RequestBody Input input) {
        InputService.setPlaceState(input);
        return InputService.getInput();
    }

    // Job
    @CrossOrigin
    @GetMapping("/job")
    public int getJob() {
        return InputService.getJob();
    }

    @CrossOrigin
    @PutMapping("/put/job")
    public Input putJob(@RequestBody Input input) {
        InputService.setJob(input);
        return InputService.getInput();
    }

    // Pos Place XY
    @CrossOrigin
    @GetMapping("/posplacex")
    public int getPosX_Place() {
        return InputService.getPosX_place();
    }

    @CrossOrigin
    @GetMapping("/posplacey")
    public int getPosY_Place() {
        return InputService.getPosY_place();
    }

    @CrossOrigin
    @PutMapping("/put/posplace")
    public Input putPosPlace(@RequestBody Input input) {
        InputService.setPosPlace(input);
        return InputService.getInput();
    }

    // Move State
    @CrossOrigin
    @GetMapping("/movestate")
    public int getMoveState() {
        return InputService.getMoveState();
    }

    @CrossOrigin
    @PutMapping("/put/movestate")
    public Input putMoveState(@RequestBody Input input) {
        InputService.setMoveState(input);
        return InputService.getInput();
    }

    // Og XY
    @CrossOrigin
    @GetMapping("/ogx")
    public int getOgX() {
        return InputService.getOgX();
    }

    @CrossOrigin
    @GetMapping("/ogy")
    public int getOgY() {
        return InputService.getOgY();
    }

    @CrossOrigin
    @PutMapping("/put/og")
    public Input putOg(@RequestBody Input input) {
        InputService.setOg(input);
        return InputService.getInput();
    }

    // Pos Move XY
    @CrossOrigin
    @GetMapping("/posmovex")
    public int getPosX_move() {
        return InputService.getPosX_move();
    }

    @CrossOrigin
    @GetMapping("/posmovey")
    public int getPosY_move() {
        return InputService.getPosY_move();
    }

    @CrossOrigin
    @PutMapping("/put/posmove")
    public Input putPosMove(@RequestBody Input input) {
        InputService.setPosMove(input);
        return InputService.getInput();
    }

    // Speed state
    @CrossOrigin
    @GetMapping("/speedstate")
    public int getSpeedState() {
        return InputService.getSpeedState();
    }

    @CrossOrigin
    @PutMapping("/put/speedstate")
    public Input putSpeedState(@RequestBody Input input) {
        InputService.setSpeedState(input);
        return InputService.getInput();
    }

    // Pause State
    @CrossOrigin
    @GetMapping("/pausestate")
    public int getPauseState() {
        return InputService.getPauseState();
    }

    @CrossOrigin
    @PutMapping("/put/pausestate")
    public Input putPauseState(@RequestBody Input input) {
        InputService.setPauseState(input);
        return InputService.getInput();
    }
}
