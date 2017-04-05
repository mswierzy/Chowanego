package com.example.markus_swierzy.chowanego;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by markus_swierzy on 2017-03-19.
 */

public class Waiting extends Activity implements GameDialogQuit.OnCancelListener, GameDialogQuit.OnExitListener, GameDialogEndOfTime.OnOkListener {

    private String strGameName = "";
    private int nGameID = -1;
    private String strLogin = "";
    private int nLoginID = -1;
    private String strLoginSearcher = "Temp Searcher";
    private long endSearchTime=-1L;

    /*
        Zmienne timera
     */
    private TextView timerValue;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    private long SearchTime = 100000L;

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    // aktualny czas
    private long unixTime;

    /*
        Zmienne listy
     */

    ListView lvCatchedList;
    ListView lvToCatchList;

    ArrayList<String> CatchedList;
    ArrayList<String> ToCatchList;

    private ArrayAdapter<String> CatchedAdapter;
    private ArrayAdapter<String> ToCatchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        strGameName = getIntent().getStringExtra("GameName");
        nGameID = getIntent().getIntExtra("GameID",-1);
        strLogin = getIntent().getStringExtra("Login");
        nLoginID = getIntent().getIntExtra("LoginID",-1);
        endSearchTime = getIntent().getLongExtra("endSearchTime",-1);

        TextView tvGameName = (TextView) findViewById(R.id.tvWaitingGameName);
        TextView tvSearcher = (TextView) findViewById(R.id.tvWaitingSearcher);
        timerValue = (TextView) findViewById(R.id.tvWaitingTimer);

        lvCatchedList = (ListView) findViewById((R.id.lvWaitingCatched));
        lvToCatchList = (ListView) findViewById((R.id.lvWaitingToCatch));

        Button btnQuit = (Button) findViewById(R.id.btnWaitingQuit);

        unixTime = System.currentTimeMillis();
        SearchTime = endSearchTime - unixTime;
        if(SearchTime < 0)
        {
            SearchTime = 0;
        }

        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(DownCount, 0);
        customHandler.postDelayed(StartNewActivity, SearchTime);
        customHandler.postDelayed(RefreshList, 1000);


        CatchedList = new ArrayList<String>();
        ToCatchList = new ArrayList<String>();

        GetCatched lista_zlapanych = new GetCatched(CatchedList, nGameID);
        try {
            lista_zlapanych.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        CatchedList = lista_zlapanych.getList();


        GetToCatch lista_do_zlapania = new GetToCatch(ToCatchList, nGameID);
        try {
            lista_do_zlapania.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ToCatchList = lista_do_zlapania.getList();


        CatchedAdapter = new ArrayAdapter<String>(this, R.layout.waiting_list_item, CatchedList);
        ToCatchAdapter = new ArrayAdapter<String>(this, R.layout.waiting_list_item, ToCatchList);

        lvCatchedList.setAdapter(CatchedAdapter);
        lvToCatchList.setAdapter(ToCatchAdapter);


        GetLoginSearcher login_szukajacego = new GetLoginSearcher(nGameID);
        try {
            login_szukajacego.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        strLoginSearcher = login_szukajacego.getSearcherLogin();

        tvGameName.setText(strGameName);
        tvSearcher.setText(strLoginSearcher);

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                GameDialogQuit dialogFragment = new GameDialogQuit();
                dialogFragment.show(fm, "Quit Game");
            }
        });
    };

    private void OpenRecreateGameActivity(){
        Intent create = new Intent(Waiting.this, RecreateGame.class);
        create.putExtra("GameID", nGameID);
        create.putExtra("GameName", strGameName);
        create.putExtra("Login", strLogin);
        create.putExtra("LoginID", nLoginID);
        Waiting.this.startActivity(create);
        Waiting.this.finish();
        overridePendingTransition(R.layout.fadein, R.layout.fadeout);
    }

    private void toast( String text )
    {
        Toast.makeText( Waiting.this,
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
        customHandler.removeCallbacks(RefreshList);
        Intent create = new Intent(Waiting.this, CHMainMenu.class);
        Waiting.this.startActivity(create);
        Waiting.this.finish();
        overridePendingTransition(R.layout.fadein, R.layout.fadeout);
    }

    public void onCancel() {

    }

    public void onOk(){
        customHandler.removeCallbacks(DownCount);
        customHandler.removeCallbacks(RefreshList);
        Intent create = new Intent(Waiting.this, RecreateGame.class);
        create.putExtra("GameID", nGameID);
        create.putExtra("GameName", strGameName);
        create.putExtra("Login", strLogin);
        create.putExtra("LoginID", nLoginID);
        Waiting.this.startActivity(create);
        Waiting.this.finish();
        overridePendingTransition(R.layout.fadein, R.layout.fadeout);
    }

    private Runnable DownCount = new Runnable() {

        public void run() {

            timeInMilliseconds = SearchTime - (SystemClock.uptimeMillis() - startTime);

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            if(timeInMilliseconds < 0){
                /*
                Koniec czasu gry
                 */
                FragmentManager fm = getFragmentManager();
                GameDialogEndOfTime dialogFragment = new GameDialogEndOfTime();
                Bundle args = new Bundle();
                args.putInt("GameID", nGameID);
                args.putString("GameName", strGameName);
                args.putString("Login", strLogin);
                args.putInt("LoginID", nLoginID);
                dialogFragment.setArguments(args);
                dialogFragment.show(fm, "End of Time");
                customHandler.removeCallbacks(this);
            }else{
                customHandler.postDelayed(this, 100);
            }
        }

    };

    private Runnable StartNewActivity = new Runnable() {

        public void run() {
            //EndOfTime();
            OpenRecreateGameActivity();
        }

    };

    private Runnable RefreshList = new Runnable() {

        public void run() {

            CatchedList.clear();
            ToCatchList.clear();

            GetCatched lista_zlapanych = new GetCatched(CatchedList, nGameID);
            try {
                lista_zlapanych.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            CatchedList = lista_zlapanych.getList();


            GetToCatch lista_do_zlapania = new GetToCatch(ToCatchList, nGameID);
            try {
                lista_do_zlapania.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            ToCatchList = lista_do_zlapania.getList();

            CatchedAdapter.notifyDataSetChanged();
            ToCatchAdapter.notifyDataSetChanged();

            if (CatchedList.isEmpty()){
                OpenRecreateGameActivity();
            } else {
                customHandler.postDelayed(this, 1000);
            }
        }

    };

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        GameDialogQuit dialogFragment = new GameDialogQuit();
        dialogFragment.show(fm, "Quit Game");
    }

}
