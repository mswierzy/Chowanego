package com.example.markus_swierzy.chowanego;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class OldConnect extends AppCompatActivity {

    private ListView list;
    int ItemPosition;
    private ArrayAdapter<ConnectRow> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        list = (ListView) findViewById(R.id.list);

        ConnectRow tmp = new ConnectRow("Gra", 12);
        ConnectRow Games[] = {tmp, tmp, tmp};

        ArrayList<ConnectRow> gamesL = new ArrayList<ConnectRow>();
        gamesL.addAll( Arrays.asList(Games) );

        ConnectListAdapter adapter = new ConnectListAdapter(this, R.layout.connect_list_row, gamesL);
        ListView connectListView = (ListView)findViewById(R.id.list);
        connectListView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed(){
        Intent main = new Intent(OldConnect.this, CHMainMenu.class);
        OldConnect.this.startActivity(main);
        OldConnect.this.finish();
        overridePendingTransition(R.layout.fadein, R.layout.fadeout);
    }
}





