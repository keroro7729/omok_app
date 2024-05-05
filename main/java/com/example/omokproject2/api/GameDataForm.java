package com.example.omokproject2.api;

public class GameDataForm {
    private long blackNum, whiteNum;
    private String gameLog;
    private int gameState;

    public GameDataForm(long blackNum, long whiteNum, String gameLog, int gameState){
        this.blackNum = blackNum;
        this.whiteNum = whiteNum;
        this.gameLog = gameLog;
        this.gameState = gameState;
    }
}
