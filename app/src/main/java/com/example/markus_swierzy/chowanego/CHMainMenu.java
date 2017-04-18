package com.example.markus_swierzy.chowanego;

/**
 * Created by markus_swierzy on 2017-03-07.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CHMainMenu extends Activity {

    int x=0;

    GPSTracker gps;
    public static double latitude;
    public static double longitude;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CHEngine engine = new CHEngine();

        // ustawia opcje przyciskow
        Button start = (Button) findViewById(R.id.btnStart);
        Button exit = (Button) findViewById(R.id.btnExit);
        Button create = (Button) findViewById(R.id.btnCreate);
        Button info = (Button) findViewById(R.id.btnInfo);

        //start.getBackground().setAlpha(CHEngine.MENU_BUTTON_ALPHA);
        start.setHapticFeedbackEnabled(CHEngine.HAPTIC_BUTTON_FEEDBACK);

        //exit.getBackground().setAlpha(CHEngine.MENU_BUTTON_ALPHA);
        exit.setHapticFeedbackEnabled(CHEngine.HAPTIC_BUTTON_FEEDBACK);

        create.setHapticFeedbackEnabled(CHEngine.HAPTIC_BUTTON_FEEDBACK);

        info.setHapticFeedbackEnabled(CHEngine.HAPTIC_BUTTON_FEEDBACK);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dolacz do gry!!!!
                Intent Connect = new Intent(CHMainMenu.this, Connect.class);
                CHMainMenu.this.startActivity(Connect);
                CHMainMenu.this.finish();
                overridePendingTransition(R.layout.fadein, R.layout.fadeout);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean clean = false;
                clean = engine.onExit(v);
                if(clean){
                    int pid = Process.myPid();
                    Process.killProcess(pid);
                }
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Stworz gre!!!!
                Intent create = new Intent(CHMainMenu.this, Create.class);
                CHMainMenu.this.startActivity(create);
                CHMainMenu.this.finish();
                overridePendingTransition(R.layout.fadein, R.layout.fadeout);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Info
                Intent info = new Intent(CHMainMenu.this, Info.class);
                CHMainMenu.this.startActivity(info);
                CHMainMenu.this.finish();
                //overridePendingTransition(R.layout.fadein, R.layout.fadeout);
            }
        });


        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                /*Float latitudePos = ekran_akt_pozycji.latitudePos;
                                Float longitudePos = ekran_akt_pozycji.longitudePos;
                                TextView textView_act_pos = (TextView) findViewById(R.id.textView_act_pos);
                                textView_act_pos.setText("\n " + latitudePos + " " + longitudePos);
                                */

                                gps = new GPSTracker(CHMainMenu.this);

                                // check if GPS enabled
                                if(gps.canGetLocation()){

                                    latitude = gps.getLatitude();
                                    longitude = gps.getLongitude();

                                    // tymczasowo, usunac pozniej tez z layoutu na koniec jak bedzie wszystko dzialalo!!!!!
                                    TextView textView_tmp = (TextView) findViewById(R.id.textView_tmp);
                                    textView_tmp.setText("\n " + latitude + " " + longitude);

                                }else{
                                    gps.showSettingsAlert();
                                }

                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

    }

    @Override
    public void onBackPressed(){
        if(x == 0){
            Context context = getApplicationContext();
            String mystring = getResources().getString(R.string.txtPressExit);
            CharSequence text = mystring;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            x++;
        }else{
            System.exit(0);
        }
        Log.d("Chowanego","OnBackPressed Caled");
    }
}
