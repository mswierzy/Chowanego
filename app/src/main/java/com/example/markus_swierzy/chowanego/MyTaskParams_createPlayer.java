package com.example.markus_swierzy.chowanego;

/**
 * Created by Wiktor on 27.03.2017.
 */

public class MyTaskParams_createPlayer {
    String Login;
    int ID_Gry;
    double Latitude;
    double Longitude;

    MyTaskParams_createPlayer(String Login, int ID_Gry, double Latitude, double Longitude) {
        this.Login = Login;
        this.ID_Gry = ID_Gry;
        this.Latitude = Latitude;
        this.Longitude = Longitude;

    }
}

