package com.carin.APIDraft;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "gamedata")
public class GameDataController {

    @CrossOrigin
    @GetMapping
    public GameData getGameData() {
        return GameDataService.getGameData();
    }

    @CrossOrigin
    @PutMapping("/put")
    public GameData putGameData(@RequestBody GameData gameData) {
        GameDataService.setGameData(gameData);
        return GameDataService.getGameData();
    }
}
