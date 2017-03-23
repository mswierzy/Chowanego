package com.example.markus_swierzy.chowanego;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by markus_swierzy on 2017-03-23.
 */

public class WaitingDialogNewGame extends DialogFragment {

    private Activity activity;
    private String strGameName = "";
    private int nGameID = -1;
    private String strLogin = "";
    private int nLoginID = -1;
    private String strSearcherLogin = "";
    private int nSearcherLoginID = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.game_dialog_quit, container, false);

        activity = getActivity();

        Button exit = (Button) rootView.findViewById(R.id.btnGameDialogQuitExit);
        Button cancel = (Button) rootView.findViewById(R.id.btnGameDialogQuitCancel);

        getDialog().setTitle("Do You really want to quit?");
        strGameName = getArguments().getString("GameName");
        strLogin = getArguments().getString("Login");
        nLoginID = getArguments().getInt("LoginID");
        nGameID = getArguments().getInt("GameID");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                /*
                    Rozpoczecie nowej gry
                 */
                if (nLoginID == nSearcherLoginID){
                    Intent i = new Intent(activity, GameSearcher.class);
                    i.putExtra("GameName", strGameName);
                    i.putExtra("GameID", nGameID);
                    i.putExtra("Login", strLogin);
                    i.putExtra("LoginID", nLoginID);
                    activity.startActivity(i);
                    activity.finish();
                }else{
                    Intent i = new Intent(activity, Game.class);
                    i.putExtra("GameName", strGameName);
                    i.putExtra("GameID", nGameID);
                    i.putExtra("Login", strLogin);
                    i.putExtra("LoginID", nLoginID);
                    activity.startActivity(i);
                    activity.finish();
                }
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
