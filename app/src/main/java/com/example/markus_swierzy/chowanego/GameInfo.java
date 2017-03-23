package com.example.markus_swierzy.chowanego;

/**
 * Created by markus_swierzy on 2017-03-14.
 */

public class GameInfo {

    private String GameName;    // Nazwa Gry
    private int GameID;
    private int PlayersCnt;     // Ilosc Graczy
    private int Status;         // 0 - Chowanie, 1 - Szukanie, 2 - Oczekiwanie na nowa Gre
    private boolean IsPassword; // 0 - brak Hasla, 1 - Haslo
    private String Password;    // Haslo

    public GameInfo(String GameName, int PlayersCnt, int Status, boolean IsPassword, String Password, int GameID){
        this.GameName = GameName;
        this.PlayersCnt = PlayersCnt;
        this.Status = Status;
        this.IsPassword = IsPassword;
        this.Password = Password;
        this.GameID = GameID;
    }

    public String getGameName(){
        return this.GameName;
    }

    public int getPlayersCnt(){
        return this.PlayersCnt;
    }

    public int getStatus() { return this.Status; }

    public boolean getIsPassword(){ return this.IsPassword; }

    public String getPassword() { return this.Password; }

    public int getGameID() { return this.GameID; }

    public void setGameName(String name){
        this.GameName = name;
    }

    public void setPlayersCnt(int PlayersCnt){
        this.PlayersCnt = PlayersCnt;
    }

    public void setStatus(int Status){
        this.Status = Status;
    }

    public void setIsPassword(boolean IsPassword){
        this.IsPassword = IsPassword;
    }

    public void setPassword(String Password){
        this.Password = Password;
    }

    public void setGameID(int GameID){
        this.GameID = GameID;
    }

    public String getStatusString(){
        switch (this.Status){
            case 0:
                return "Chowanie";
            case 1:
                return "Szukanie";
            case 2:
                return "Oczekiwanie";
            default:
                return "Error";
        }

    }
}