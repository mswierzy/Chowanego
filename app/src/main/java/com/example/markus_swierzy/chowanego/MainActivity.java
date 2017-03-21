package com.example.markus_swierzy.chowanego;

import android.content.Intent;
import android.os.Handler;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // wyswietl ekran powitalny
        setContentView(R.layout.splashscreen);

        // Wystartuj ekran powitalny i glowne menu w opoznionym watku
        new Handler().postDelayed(new Thread(){
            @Override
            public void run(){
                Intent mainMenu = new Intent(MainActivity.this, CHMainMenu.class);
                MainActivity.this.startActivity(mainMenu);
                MainActivity.this.finish();
                overridePendingTransition(R.layout.fadein, R.layout.fadeout);
            }
        }, CHEngine.GAME_THREAD_DELAY);
    }
}
