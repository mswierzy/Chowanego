package com.example.markus_swierzy.chowanego;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
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

public class Connect extends AppCompatActivity {

    String strLogin = "";
    String strSearch = "";
    String strGameName = "";
    int nSelected = -1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        Button login = (Button) findViewById(R.id.btnConnectLogin);
        Button search = (Button) findViewById(R.id.btnConnectSearch);
        Button play = (Button) findViewById(R.id.btnConnectPlay);

        final EditText txtLogin = (EditText)findViewById(R.id.edConnectLogin);
        final EditText txtSearch = (EditText)findViewById(R.id.edConnectSearch);

        List<String> data = new ArrayList<String>();
        for ( int i = 1; i <= 10; i++ )
        {
            data.add( String.format( "Item %d", i ) );
        }

        CustomAdapter adapter = new CustomAdapter( Connect.this, data );

        ListView listView = (ListView)findViewById( R.id.list );
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter( adapter );

        login.setHapticFeedbackEnabled(CHEngine.HAPTIC_BUTTON_FEEDBACK);
        search.setHapticFeedbackEnabled(CHEngine.HAPTIC_BUTTON_FEEDBACK);
        play.setHapticFeedbackEnabled(CHEngine.HAPTIC_BUTTON_FEEDBACK);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strLogin = txtLogin.getText().toString();
                toast("Login: " + txtLogin.getText().toString());
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strSearch = txtLogin.getText().toString();
                toast("Search: " + txtSearch.getText().toString());
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dolacz do gry!!!!
                String str = "Play: " + Integer.toString(nSelected) + " " + strGameName;
                toast(str);
                FragmentManager fm = getFragmentManager();
                ConnectDialog dialogFragment = new ConnectDialog ();
                Bundle args = new Bundle();
                args.putInt("GameID", nSelected);
                args.putString("GameName", strGameName);
                dialogFragment.setArguments(args);
                dialogFragment.show(fm, "Sample Fragment");

                //Intent intent = new Intent(getBaseContext(), ConnectDialog.class);
                //intent.putExtra("GameName", strGameName);
                //intent.putExtra("SelectedID", nSelected);
            }
        });

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick( AdapterView<?> listView, View view,
                                     int pos, long id )
            {
                TextView textView =
                        (TextView) view.findViewById( R.id.text1 );
                toast( (String) textView.getText() );
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
