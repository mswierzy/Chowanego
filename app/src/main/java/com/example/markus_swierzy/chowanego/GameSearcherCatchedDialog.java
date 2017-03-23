package com.example.markus_swierzy.chowanego;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by markus_swierzy on 2017-03-23.
 */

public class GameSearcherCatchedDialog extends DialogFragment {

    private String strGameName = "";
    private int nGameID = -1;
    private String strLogin = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.game_catched_dialog, container, false);
        getDialog().setTitle("Catched???");

        Button no = (Button) rootView.findViewById(R.id.btnGameCatchedNo);

        strGameName = getArguments().getString("GameName");
        nGameID = getArguments().getInt("GameID",-1);
        strLogin = getArguments().getString("Login");

        no.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button yes = (Button) rootView.findViewById(R.id.btnGameCatchedYes);
        yes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dismiss();
            }
        });

        return rootView;
    }
}
