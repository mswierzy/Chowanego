package com.example.markus_swierzy.chowanego;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by markus_swierzy on 2017-03-22.
 */

public class ConnectDialogNoPassword extends DialogFragment {

    private Activity activity;
    private String strGameName = "";
    private int nGameID = -1;
    private String strLogin = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.connect_dialog_no_password, container, false);

        activity = getActivity();

        Button play = (Button) rootView.findViewById(R.id.btnConnectDialogNoPasswordPlay);
        Button dismiss = (Button) rootView.findViewById(R.id.btnConnectDialogNoPasswordCancel);

        strGameName = getArguments().getString("GameName");
        strLogin = getArguments().getString("Login");
        nGameID = getArguments().getInt("GameID",-1);

        getDialog().setTitle(strLogin);

        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(activity, Game.class);
                i.putExtra("GameName", strGameName);
                i.putExtra("GameID", nGameID);
                i.putExtra("Login", strLogin);
                activity.startActivity(i);
                activity.finish();
            }
        });

        return rootView;
    }

    private void toast( String text )
    {
        Toast.makeText( activity,
                String.format( "%s", text ), Toast.LENGTH_SHORT )
                .show();
    }

}
