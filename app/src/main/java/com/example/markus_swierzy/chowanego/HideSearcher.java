package com.example.markus_swierzy.chowanego;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * Created by markus_swierzy on 2017-03-30.
 */

public class HideSearcher extends AppCompatActivity implements GameDialogQuit.OnCancelListener, GameDialogQuit.OnExitListener, DialogStartSearching.OnOkListener {

    private String strGameName = "";
    private String strLogin = "";
    private int nGameID = -1;
    private int nLoginID = -1;
    private long endHideTime=-1L;

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

    // aktualny czas
    long unixTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_searcher);

        res = getResources();

        Button btnQuit = (Button) findViewById(R.id.btnHideSearcherQuit);

        timerValue = (TextView) findViewById(R.id.tvHideSearcherTimer);
        TextView txtGameName = (TextView) findViewById(R.id.tvHideSearcherName);
        TextView txtLogin = (TextView) findViewById(R.id.txtHideSearcherLogin);

        strGameName = getIntent().getStringExtra("GameName");
        nGameID = getIntent().getIntExtra("GameID",-1);
        strLogin = getIntent().getStringExtra("Login");
        nLoginID = getIntent().getIntExtra("LoginID",-1);
        endHideTime = getIntent().getLongExtra("endHideTime",-1);

        txtGameName.setText(strGameName);
        txtLogin.setText(strLogin);

        /*unixTime = System.currentTimeMillis();
        HideTime = endHideTime - unixTime;
        if(HideTime < 0)
        {
            HideTime = 0;
        }*/
//TODO: MARKUS! tutaj zobacz to... mozesz nawet odkomentowac moje, ale zostawilem Twoje =10000L zebys zobaczyl ze nawet nazwy gry i loginu brakuje...
//TODO: Pobierz czas zakończenia chowania z bazy danych i wylicz na jego podstawie ilość milisekund do jego końca i potem zapisz do zmiennej HideTime
        HideTime = 10000L;

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
    }

    private void toast( String text )
    {
        Toast.makeText( HideSearcher.this,
                String.format( "%s", text ), Toast.LENGTH_SHORT )
                .show();
    }


    public void onExit() {

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
        Intent create = new Intent(HideSearcher.this, CHMainMenu.class);
        HideSearcher.this.startActivity(create);
        HideSearcher.this.finish();
        overridePendingTransition(R.layout.fadein, R.layout.fadeout);
    }

    public void onCancel() {

    }

    public void onOk(){
        customHandler.removeCallbacks(DownCount);
        customHandler.removeCallbacks(StartNewActivity);
        Intent create = new Intent(HideSearcher.this, GameSearcher.class);
        create.putExtra("GameID", nGameID);
        create.putExtra("GameName", strGameName);
        create.putExtra("Login", strLogin);
        create.putExtra("LoginID", nLoginID);
        HideSearcher.this.startActivity(create);
        HideSearcher.this.finish();
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
