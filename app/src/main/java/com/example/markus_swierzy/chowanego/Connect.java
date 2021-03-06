package com.example.markus_swierzy.chowanego;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Connect extends AppCompatActivity {

    String strLogin = "";
    String strGameName = "";
    List<String> data = new ArrayList<String>();
    List<GameInfo> GameInfoList = new ArrayList<GameInfo>();

    ConnectListAdapter adapter;
    int nSelected = -1;

    Resources res;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        Button play = (Button) findViewById(R.id.btnConnectPlay);

        final EditText txtLogin = (EditText)findViewById(R.id.edConnectLogin);

        GetAllGames games = new GetAllGames(GameInfoList);
        try {
            games.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        GameInfoList = games.getList();


        //GameInfoList.add(new GameInfo(0, "Roszowicki Las", "1234", "20:10:00", "20:30:00"));
        //GameInfoList.add(new GameInfo(1, "Mikolow", "abcd", "19:00:00", "19:10:00"));

        adapter = new ConnectListAdapter(Connect.this, GameInfoList);

        ListView listView = (ListView)findViewById( R.id.list );
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter( adapter );

        play.setHapticFeedbackEnabled(CHEngine.HAPTIC_BUTTON_FEEDBACK);

        res = getResources();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bOk = true;
                String strToastMessage = "";
                strLogin = txtLogin.getText().toString();

                if(strLogin.equals("")){
                    strToastMessage = getString(R.string.txtNoLoginTyped) + "\n";
                    bOk = false;
                }
                if (nSelected < 0){
                    strToastMessage += res.getString(R.string.txtNoGameSelected) + "\n";
                    bOk = false;
                }

                if (bOk){
                    FragmentManager fm = getFragmentManager();
                    ConnectDialogPassword dialogFragment = new ConnectDialogPassword();

                    Bundle args = new Bundle();
                    args.putInt("GameID", GameInfoList.get(nSelected).getGameID());
                    args.putString("GameName", GameInfoList.get(nSelected).getGameName());
                    args.putString("Login", strLogin);
                    args.putString("Password", GameInfoList.get(nSelected).getPassword());
                    args.putLong("endHideTime", GameInfoList.get(nSelected).getEndHideTime());
                    args.putLong("endSearchTime", GameInfoList.get(nSelected).getEndSearchTime());
                    dialogFragment.setArguments(args);

                    dialogFragment.show(fm, "Connect");
                }else {
                    toast(strToastMessage);
                }
            }
        });

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick( AdapterView<?> listView, View view,
                                     int pos, long id )
            {
                TextView textView =
                        (TextView) view.findViewById( R.id.tvConnectListItemGameName );
                //toast( (String) textView.getText() );
                view.setSelected(true);
                strGameName = (String)textView.getText();
                nSelected = pos;
            }
        } );


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void toast( String text )
    {
        Toast.makeText( Connect.this,
                String.format( "%s", text ), Toast.LENGTH_SHORT )
                .show();
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Connect.this);
        // Get the layout inflater
        LayoutInflater inflater = Connect.this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.connect_dialog, null))
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                });
        return builder.create();
    }

    @Override
    public void onBackPressed() {
        Intent main = new Intent(Connect.this, CHMainMenu.class);
        Connect.this.startActivity(main);
        Connect.this.finish();
        overridePendingTransition(R.layout.fadein, R.layout.fadeout);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Connect Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
