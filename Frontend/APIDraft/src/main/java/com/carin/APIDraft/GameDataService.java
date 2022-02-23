package com.carin.APIDraft;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;

@Service
public class GameDataService {

    private static GameData gameData = new GameData(0,0,0,null,null,0,null,null,null,null,null,0,0);

    public static GameData getGameData() {
        return gameData;
    }

    public static void setGameData(GameData gameData) {
        GameDataService.gameData = gameData;
    }
}
