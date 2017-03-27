package com.example.markus_swierzy.chowanego;

/**
 * Created by Wiktor on 27.03.2017.
 */

public class MyTaskParams {
    String Nazwa_gry;
    String Haslo_gry;
    String Login;
    String Czas_szukania;
    String Czas_ukrycia;
    String Latitude;
    String Longitude;

    MyTaskParams(String Nazwa_gry, String Haslo_gry, String Login, String Czas_szukania, String Czas_ukrycia, String Latitude, String Longitude) {
        this.Nazwa_gry = Nazwa_gry;
        this.Haslo_gry = Haslo_gry;
        this.Login = Login;
        this.Czas_szukania = Czas_szukania;
        this.Czas_ukrycia = Czas_ukrycia;
        this.Latitude = Latitude;
        this.Longitude = Longitude;

    }
}

