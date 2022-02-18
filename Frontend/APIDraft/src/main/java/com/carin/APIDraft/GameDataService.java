package com.carin.APIDraft;

import org.springframework.stereotype.Service;

@Service
public class GameDataService {

    private static GameData gameData = new GameData(-1, null, null, null);

    public static GameData getGameData() {
        return gameData;
    }

    public static void setGameData(GameData gameData) {
        GameDataService.gameData = gameData;
    }
}
