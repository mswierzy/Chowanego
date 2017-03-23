package com.example.markus_swierzy.chowanego;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
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

        lvCatchedList = (ListView) findViewById((R.id.lvWaitingCatched));
        lvToCatchList = (ListView) findViewById((R.id.lvWaitingToCatch));

        Button btnQuit = (Button) findViewById(R.id.btnWaitingQuit);

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

    public void NewGame(){
        FragmentManager fm = getFragmentManager();
        WaitingDialogNewGame dialogFragment = new WaitingDialogNewGame();
        Bundle args = new Bundle();
        args.putInt("GameID", nGameID);
        args.putString("GameName", strGameName);
        args.putString("Login", strLogin);
        args.putInt("LoginID", nLoginID);
        dialogFragment.setArguments(args);
        dialogFragment.show(fm, "New Game");
    }
}
