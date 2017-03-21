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
 * Created by markus_swierzy on 2017-03-16.
 */

public class ConnectDialog extends DialogFragment {

    String strGameName = "";
    int nGameID = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.connect_dialog, container, false);
        getDialog().setTitle("Connect");

        Button dismiss = (Button) rootView.findViewById(R.id.btnConnectDialogCancel);

        strGameName = getArguments().getString("GameName");
        nGameID = getArguments().getInt("GameID",-1);

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
                Activity activity = getActivity();
                Intent i = new Intent(activity, Game.class);
                i.putExtra("GameName", strGameName);
                i.putExtra("GameID", nGameID);
                activity.startActivity(i);
                activity.finish();
            }
        });

        return rootView;
    }

}
