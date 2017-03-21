package com.example.markus_swierzy.chowanego;

/**
 * Created by markus_swierzy on 2017-03-14.
 */

public class ConnectRow {
    private boolean bButton;
    private String GameName;
    private int Players;

    public ConnectRow(String GameName, int Players){
        this.bButton = false;
        this.GameName = GameName;
        this.Players = Players;
    }

    public String getGameName(){
        return this.GameName;
    }

    public int getPlayers(){
        return this.Players;
    }

    public boolean getButtonState(){
        return this.bButton;
    }

    public void setGameName(String name){
        this.GameName = name;
    }

    public void setPlayers(int PlayersCnt){
        this.Players = PlayersCnt;
    }

    public void setButtonState(boolean state){
        this.bButton = state;
    }
}