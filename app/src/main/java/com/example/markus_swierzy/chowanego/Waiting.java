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

import java.util.ArrayList;

/**
 * Created by markus_swierzy on 2017-03-19.
 */

public class Waiting extends Activity {

    private String strGameName = "";
    private int nGameID = -1;
    private String strLogin = "";
    private int nLoginID = -1;
    private String strLoginSearcher = "Temp Searcher";

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

        TextView tvGameName = (TextView) findViewById(R.id.tvWaitingGameName);
        TextView tvSearcher = (TextView) findViewById(R.id.tvWaitingSearcher);
        timerValue = (TextView) findViewById(R.id.tvWaitingTimer);

        lvCatchedList = (ListView) findViewById((R.id.lvWaitingCatched));
        lvToCatchList = (ListView) findViewById((R.id.lvWaitingToCatch));

        Button btnQuit = (Button) findViewById(R.id.btnWaitingQuit);
//TODO: Pobierz czas zakończenia szukania z bazy danych i wylicz na jego podstawie ilość milisekund do jego końca i potem zapisz do zmiennej SearchTime
        SearchTime = 20000L;

        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(DownCount, 0);
        /*
            Wyciagnij z bazy strLoginSearcher
         */

        tvGameName.setText(strGameName);
        tvSearcher.setText(strLoginSearcher);

        CatchedList = new ArrayList<String>();
        ToCatchList = new ArrayList<String>();

        CatchedList.add("Marek");
        CatchedList.add("Michal");
        CatchedList.add("Olek");
        CatchedList.add("Ania");

        ToCatchList.add("Konrad");
        ToCatchList.add("Stasiu");
        ToCatchList.add("Marcin");

        CatchedAdapter = new ArrayAdapter<String>(this, R.layout.waiting_list_item, CatchedList);
        ToCatchAdapter = new ArrayAdapter<String>(this, R.layout.waiting_list_item, ToCatchList);

        lvCatchedList.setAdapter(CatchedAdapter);
        lvToCatchList.setAdapter(ToCatchAdapter);

        /*

            Aktualizacja listy

            CatchedList.clear();
            CatchedList.add( "Zenon" );
            CatchedAdapter.notifyDataSetChanged();
         */

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


    };

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

}
