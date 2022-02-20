package com.carin.APIDraft;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;

@Service
public class GameDataService {

    private static GameData gameData = new GameData(-1,new LinkedList<>(),new LinkedList<>(),new LinkedList<>(), Arrays.asList(0,0,0),-1,-1,5,5);

    public static GameData getGameData() {
        return gameData;
    }

    public static void setGameData(GameData gameData) {
        GameDataService.gameData = gameData;
    }
}
