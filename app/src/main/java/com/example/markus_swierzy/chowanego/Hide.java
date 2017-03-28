package com.example.markus_swierzy.chowanego;

import android.app.FragmentManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by markus_swierzy on 2017-03-28.
 */

public class Hide  extends AppCompatActivity {

    private String strGameName = "";
    private String strLogin = "";
    private int nGameID = -1;
    private int nLoginID = -1;

    /*
        Zmienne timera
     */
    private TextView timerValue;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    private long HideTime = 100000L;

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    /*
        Zmienne kompasu
     */
    private ImageView image;
    private float currentDegree = 0f;
    private SensorManager mSensorManager;
    TextView tvHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide);

        Button btnSetLocation = (Button) findViewById(R.id.btnHideSetLocation);
        Button btnQuit = (Button) findViewById(R.id.btnHideQuit);

        TextView txtGameName = (TextView) findViewById(R.id.tvGameName);
        TextView txtLogin = (TextView) findViewById(R.id.txtGameLogin);

        strGameName = getIntent().getStringExtra("GameName");
        nGameID = getIntent().getIntExtra("GameID",-1);
        strLogin = getIntent().getStringExtra("Login");
        nLoginID = getIntent().getIntExtra("LoginID",-1);

        txtGameName.setText(strGameName);
        txtLogin.setText(strLogin);

        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(DownCount, 0);

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dolacz do gry!!!!
                FragmentManager fm = getFragmentManager();
                GameDialogQuit dialogFragment = new GameDialogQuit();
                Bundle args = new Bundle();
                args.putInt("GameID", nGameID);
                args.putString("GameName", strGameName);
                args.putString("Login", strLogin);
                args.putInt("LoginID", nLoginID);
                dialogFragment.setArguments(args);
                dialogFragment.show(fm, "Quit Game");
            }
        });

        btnSetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dolacz do gry!!!!
                FragmentManager fm = getFragmentManager();
                GameCatchedDialog dialogFragment = new GameCatchedDialog();
                Bundle args = new Bundle();
                args.putInt("GameID", nGameID);
                args.putString("GameName", strGameName);
                dialogFragment.setArguments(args);
                dialogFragment.show(fm, "Catched");
            }
        });
    }

    private void toast( String text )
    {
        Toast.makeText( Hide.this,
                String.format( "%s", text ), Toast.LENGTH_SHORT )
                .show();
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        GameDialogQuit dialogFragment = new GameDialogQuit();
        Bundle args = new Bundle();
        args.putInt("GameID", nGameID);
        args.putString("GameName", strGameName);
        args.putString("Login", strLogin);
        args.putInt("LoginID", nLoginID);
        dialogFragment.setArguments(args);
        dialogFragment.show(fm, "Quit Game");
    }

    /*
        Okno wyswietlane przy koncu czasu
    */
    public void EndOfTime(){
        FragmentManager fm = getFragmentManager();
        GameDialogEndOfTime dialogFragment = new GameDialogEndOfTime();
        Bundle args = new Bundle();
        args.putInt("GameID", nGameID);
        args.putString("GameName", strGameName);
        args.putString("Login", strLogin);
        args.putInt("LoginID", nLoginID);
        dialogFragment.setArguments(args);
        dialogFragment.show(fm, "End of Time");
    }

    /*
        Odliczanie zegara do gory
    */
    private Runnable UpCount = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }

    };

    /*
        Odliczanie zegara w dol
    */
    private Runnable DownCount = new Runnable() {

        public void run() {

            timeInMilliseconds = HideTime - (SystemClock.uptimeMillis() - startTime);

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }

    };
    @Override
    protected void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
    }

    @Override
    protected void onPause() {
        super.onPause();

        // to stop the listener and save battery
    }
}
