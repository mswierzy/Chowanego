package com.example.markus_swierzy.chowanego;

/**
 * Created by markus_swierzy on 2017-03-23.
 */

public class UserInfo {
    String strLogin;
    double dblDistance;
    int nUserID;
    double dblLatitude;
    double dblLongitude;

    public UserInfo(String strLogin, double dblDistance, int nUserID, double dblLatitude, double dblLongitude) {
        this.strLogin = strLogin;
        this.dblDistance = dblDistance;
        this.nUserID = nUserID;
        this.dblLatitude = dblLatitude;
        this.dblLongitude = dblLongitude;
    }
}
