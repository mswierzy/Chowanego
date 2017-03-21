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
import android.widget.Toast;

public class CHMainMenu extends Activity {

    int x=0;

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
