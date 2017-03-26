package com.example.markus_swierzy.chowanego;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by markus_swierzy on 2017-03-19.
 */

public class GameCatchedDialog extends DialogFragment {

    private String strGameName = "";
    private int nGameID = -1;
    private String strLogin = "";
    private int nLoginID = -1;

    Resources res;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.game_catched_dialog, container, false);

        res = getResources();

        getDialog().setTitle(res.getString(R.string.txtCatched) + "???");

        Button no = (Button) rootView.findViewById(R.id.btnGameCatchedNo);

        strGameName = getArguments().getString("GameName");
        nGameID = getArguments().getInt("GameID",-1);
        strLogin = getArguments().getString("Login");
        nLoginID = getArguments().getInt("LoginID", -1);

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
                i.putExtra("Login", strLogin);
                i.putExtra("GameID", nGameID);
                i.putExtra("LoginID", nLoginID);
                activity.startActivity(i);
                activity.finish();
            }
        });

        return rootView;
    }
}
