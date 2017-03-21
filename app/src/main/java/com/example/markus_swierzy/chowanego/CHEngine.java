package com.example.markus_swierzy.chowanego;

import android.view.View;

/**
 * Created by markus_swierzy on 2017-03-07.
 */

public class CHEngine {
    public static final int GAME_THREAD_DELAY = 1000;
    public static final int MENU_BUTTON_ALPHA = 0;
    public static final boolean HAPTIC_BUTTON_FEEDBACK = true;

    // zamknij watki gry i wyjdz z niej
    public boolean onExit(View v){
        try{
            return true;
        }catch(Exception e){
            return false;
        }
    }
}