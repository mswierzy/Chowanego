package com.example.markus_swierzy.chowanego;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by markus_swierzy on 2017-03-28.
 */

public class RecreateGame extends Activity implements GameDialogQuit.OnCancelListener, GameDialogQuit.OnExitListener, WaitingDialogNewGame.OnCancelListener, WaitingDialogNewGame.OnPlayListener {

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
        setContentView(R.layout.activity_recreate_game);

        strGameName = getIntent().getStringExtra("GameName");
        nGameID = getIntent().getIntExtra("GameID",-1);
        strLogin = getIntent().getStringExtra("Login");
        nLoginID = getIntent().getIntExtra("LoginID",-1);

        Button btnQuit = (Button) findViewById(R.id.btnRecreateQuit);
        Button btnNewGame = (Button) findViewById(R.id.btnRecreateNewGame);

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                GameDialogQuit dialogFragment = new GameDialogQuit();
                dialogFragment.show(fm, "Quit Game");
            }
        });
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

    };

    public void onPlay(){

        int nSearcherLoginID = -1;
        if (nLoginID == nSearcherLoginID){
            Intent create = new Intent(RecreateGame.this, GameSearcher.class);
            create.putExtra("GameID", nGameID);
            create.putExtra("GameName", strGameName);
            create.putExtra("Login", strLogin);
            create.putExtra("LoginID", nLoginID);
            RecreateGame.this.startActivity(create);
            RecreateGame.this.finish();
            overridePendingTransition(R.layout.fadein, R.layout.fadeout);
        }else{
            Intent create = new Intent(RecreateGame.this, Hide.class);
            create.putExtra("GameID", nGameID);
            create.putExtra("GameName", strGameName);
            create.putExtra("Login", strLogin);
            create.putExtra("LoginID", nLoginID);
            RecreateGame.this.startActivity(create);
            RecreateGame.this.finish();
            overridePendingTransition(R.layout.fadein, R.layout.fadeout);
        }
    }

    public void onExit() {
//TODO: Wylogowanie użytkownika z bazy danych graczy
        Intent create = new Intent(RecreateGame.this, CHMainMenu.class);
        RecreateGame.this.startActivity(create);
        RecreateGame.this.finish();
        overridePendingTransition(R.layout.fadein, R.layout.fadeout);
    }

    public void onCancel() {

    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        GameDialogQuit dialogFragment = new GameDialogQuit();
        dialogFragment.show(fm, "Quit Game");
    }
}
