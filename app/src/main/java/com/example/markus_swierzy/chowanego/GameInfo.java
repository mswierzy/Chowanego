package com.example.markus_swierzy.chowanego;

/**
 * Created by markus_swierzy on 2017-03-14.
 */

public class GameInfo {

    private int GameID;
    private String GameName;        // Nazwa Gry
    private String Password;        // Haslo
    private String endSearchTime;   // Koniec czasu na ukrycie
    private String endHideTime;     // Koniec czasu na szukanie


    public GameInfo(int GameID, String GameName, String Password, String endHideTime, String endSearchTime){
        this.GameID = GameID;
        this.GameName = GameName;
        this.Password = Password;
        this.endHideTime = endHideTime;
        this.endSearchTime = endSearchTime;
    }

    public String getGameName(){
        return this.GameName;
    }

    public String getPassword() { return this.Password; }

    public int getGameID() { return this.GameID; }

    public String getEndSearchTime() { return this.endSearchTime; }

    public String getEndHideTime() { return this.endHideTime; }

    public void setGameName(String name){
        this.GameName = name;
    }

    public void setPassword(String Password){
        this.Password = Password;
    }

    public void setGameID(int GameID){
        this.GameID = GameID;
    }

    public void setEndSearchTime(String endSrchT) { this.endSearchTime = endSrchT; }

    public void setEndHideTime(String endHidT) { this.endHideTime = endHidT; }
}