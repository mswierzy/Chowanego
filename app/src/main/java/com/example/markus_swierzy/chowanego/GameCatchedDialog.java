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
 * Created by markus_swierzy on 2017-03-19.
 */

public class GameCatchedDialog extends DialogFragment {

    String strGameName = "";
    int nGameID = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.game_catched_dialog, container, false);
        getDialog().setTitle("Connect");

        Button no = (Button) rootView.findViewById(R.id.btnGameCatchedNo);

        strGameName = getArguments().getString("GameName");
        nGameID = getArguments().getInt("GameID",-1);

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
                Activity activity = getActivity();
                Intent i = new Intent(activity, Waiting.class);
                i.putExtra("GameName", strGameName);
                i.putExtra("GameID", nGameID);
                activity.startActivity(i);
                activity.finish();
            }
        });

        return rootView;
    }
}
