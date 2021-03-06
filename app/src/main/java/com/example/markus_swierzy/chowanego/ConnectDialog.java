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

import java.util.concurrent.ExecutionException;

/**
 * Created by markus_swierzy on 2017-03-16.
 */

public class ConnectDialog extends DialogFragment {

    private Activity activity;
    String strGameName = "";
    int nGameID = -1;
    private long endHideTime = -1;
    private long endSearchTime = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.connect_dialog, container, false);
        getDialog().setTitle("Connect");

        Button dismiss = (Button) rootView.findViewById(R.id.btnConnectDialogCancel);

        strGameName = getArguments().getString("GameName");
        nGameID = getArguments().getInt("GameID",-1);
        endHideTime = getArguments().getLong("endHideTime");
        endSearchTime = getArguments().getLong("endSearchTime");

        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button play = (Button) rootView.findViewById(R.id.btnConnectDialogPlay);
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent i = new Intent(activity, Hide.class);
                i.putExtra("GameName", strGameName);
                i.putExtra("GameID", nGameID);
                i.putExtra("endHideTime", endHideTime);
                i.putExtra("endSearchTime", endSearchTime);
                activity.startActivity(i);
                activity.finish();
            }
        });

        return rootView;
    }



}
