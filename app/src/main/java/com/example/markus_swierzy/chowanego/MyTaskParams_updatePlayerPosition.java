package com.example.markus_swierzy.chowanego;

/**
 * Created by Wiktor on 27.03.2017.
 */

public class MyTaskParams_updatePlayerPosition {
    int ID_Gracza;
    double Latitude;
    double Longitude;

    MyTaskParams_updatePlayerPosition(int ID_Gracza, double Latitude, double Longitude) {
        this.ID_Gracza = ID_Gracza;
        this.Latitude = Latitude;
        this.Longitude = Longitude;

    }
}

