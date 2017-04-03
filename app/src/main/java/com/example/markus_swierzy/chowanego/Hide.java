package com.example.markus_swierzy.chowanego;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
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

import java.util.concurrent.ExecutionException;

/**
 * Created by markus_swierzy on 2017-03-28.
 */

public class Hide  extends AppCompatActivity implements GameDialogQuit.OnCancelListener, GameDialogQuit.OnExitListener, DialogStartSearching.OnOkListener {

    private String strGameName = "";
    private String strLogin = "";
    private int nGameID = -1;
    private int nLoginID = -1;
    private long endHideTime=-1L;
    private long endSearchTime=-1L;

    private double Latitude = CHMainMenu.latitude;
    private double Longitude = CHMainMenu.longitude;

    private Resources res;
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

    String strNewLocation;

    // aktualny czas
    private long unixTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide);

        res = getResources();

        Button btnSetLocation = (Button) findViewById(R.id.btnHideSetLocation);
        Button btnQuit = (Button) findViewById(R.id.btnHideQuit);

        timerValue = (TextView) findViewById(R.id.tvHideTimer);
        TextView txtGameName = (TextView) findViewById(R.id.tvHideName);
        TextView txtLogin = (TextView) findViewById(R.id.txtHideLogin);

        strGameName = getIntent().getStringExtra("GameName");
        nGameID = getIntent().getIntExtra("GameID",-1);
        strLogin = getIntent().getStringExtra("Login");
        nLoginID = getIntent().getIntExtra("LoginID",-1);
        endHideTime = getIntent().getLongExtra("endHideTime",-1);
        endSearchTime = getIntent().getLongExtra("endSearchTime", -1);

        txtGameName.setText(strGameName);
        txtLogin.setText(strLogin);

        unixTime = System.currentTimeMillis();
        HideTime = endHideTime - unixTime;
        if(HideTime < 0)
        {
            HideTime = 0;
        }

        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(DownCount, 0);
        customHandler.postDelayed(StartNewActivity, HideTime);

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                GameDialogQuit dialogFragment = new GameDialogQuit();
                dialogFragment.show(fm, "Quit Game");
            }
        });

        btnSetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyTaskParams_updatePlayerPosition args = new MyTaskParams_updatePlayerPosition(nLoginID, Latitude, Longitude);
                UpdatePlayerPosition newPlayerPosition = new UpdatePlayerPosition(Latitude, Longitude, nLoginID);

                try {
                    newPlayerPosition.execute(args).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                if (newPlayerPosition.getSuccess() == 1 ) {
                    strNewLocation = Latitude+" "+Longitude;
                }
                else
                {
                    toast("Błąd aktualizacji pozycji");
                }

                toast(res.getString(R.string.txtNewLocation) + ": " + strNewLocation);
            }
        });
    }

    private void toast( String text )
    {
        Toast.makeText( Hide.this,
                String.format( "%s", text ), Toast.LENGTH_SHORT )
                .show();
    }

    public void onExit() {
    // Wylogowanie użytkownika z bazy danych graczy

        MyTaskParams_deletePlayer args = new MyTaskParams_deletePlayer(nLoginID);
        DeletePlayer delPlayer = new DeletePlayer(nLoginID);

        try {
            delPlayer.execute(args).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (delPlayer.getSuccess() == 1 ) {
            toast(delPlayer.getMessage());
        }
        else
        {
            toast(delPlayer.getMessage());
        }

        customHandler.removeCallbacks(DownCount);
        customHandler.removeCallbacks(StartNewActivity);
        Intent create = new Intent(Hide.this, CHMainMenu.class);
        Hide.this.startActivity(create);
        Hide.this.finish();
        overridePendingTransition(R.layout.fadein, R.layout.fadeout);
    }

    public void onCancel() {

    }

    public void onOk(){
        customHandler.removeCallbacks(DownCount);
        customHandler.removeCallbacks(StartNewActivity);
        Intent create = new Intent(Hide.this, Game.class);
        create.putExtra("GameID", nGameID);
        create.putExtra("GameName", strGameName);
        create.putExtra("Login", strLogin);
        create.putExtra("LoginID", nLoginID);
        create.putExtra("endSearchTime", endSearchTime);
        Hide.this.startActivity(create);
        Hide.this.finish();
        overridePendingTransition(R.layout.fadein, R.layout.fadeout);
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        GameDialogQuit dialogFragment = new GameDialogQuit();
        dialogFragment.show(fm, "Quit Game");
    }

    public void EndOfTime(){
        FragmentManager fm = getFragmentManager();
        DialogStartSearching dialogFragment = new DialogStartSearching();
        dialogFragment.show(fm, "Start searching");
    }
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
            if(timeInMilliseconds < 0){
                customHandler.removeCallbacks(this);
            }else{
                customHandler.postDelayed(this, 100);
            }
        }

    };

    private Runnable StartNewActivity = new Runnable() {

        public void run() {
            //EndOfTime();
            EndOfTime();
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
