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
 * Created by markus_swierzy on 2017-03-22.
 */

public class CreateDialog extends DialogFragment {

    private Activity activity;
    private String strGameName = "";
    private String strLogin = "";
    private String strPassword = "";
    private boolean bIsPassword = false;
    private int nSearchTime = 30;
    private int nHideTime = 10;
    private int nGameID = -1;
    private int nLoginID = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.create_dialog, container, false);

        activity = getActivity();

        getDialog().setTitle("Create Game??");

        Button dismiss = (Button) rootView.findViewById(R.id.btnCreateDialogCancel);
        Button create = (Button) rootView.findViewById(R.id.btnCreateDialogCreate);

        strGameName = getArguments().getString("GameName");
        strPassword = getArguments().getString("Password");
        strLogin = getArguments().getString("Login");
        nHideTime = getArguments().getInt("HideTime");
        nSearchTime = getArguments().getInt("SearchTime");
        bIsPassword = getArguments().getBoolean("IsPassword");

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        create.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent i = new Intent(activity, GameSearcher.class);
                // Create GameID
                nGameID = 12;
                nLoginID = 3;

                i.putExtra("GameName", strGameName);
                i.putExtra("GameID", nGameID);
                i.putExtra("Login", strLogin);
                i.putExtra("LoginID", nLoginID);

                activity.startActivity(i);
                activity.finish();
            }
        });

        return rootView;
    }



}
