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
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by markus_swierzy on 2017-03-22.
 */

public class ConnectDialogPassword extends DialogFragment {

    private Activity activity;
    private String strGameName = "";
    private int nGameID = -1;
    private String strLogin = "";
    private int nLoginID = -1;
    private String strPassword;

    Resources res;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.connect_dialog_password, container, false);

        activity = getActivity();

        Button play = (Button) rootView.findViewById(R.id.btnConnectDialogPasswordPlay);
        Button dismiss = (Button) rootView.findViewById(R.id.btnConnectDialogPasswordCancel);
        final EditText password = (EditText) rootView.findViewById(R.id.txtConnectDialogPassword);

        strGameName = getArguments().getString("GameName");
        strLogin = getArguments().getString("Login");
        nGameID = getArguments().getInt("GameID",-1);
        strPassword = getArguments().getString("Password");

        res = getResources();

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
                String strTypedPassword = password.getText().toString();
                if(strTypedPassword.equals("")){
                    toast(res.getString(R.string.txtNoPasswordTyped));
                }else {
                    if(strTypedPassword.equals(strPassword)){
//TODO: Dodanie nowego użytkownika do bazy danych z jego aktualną pozycją
                        nLoginID = 5;
                        Intent i = new Intent(activity, Hide.class);
                        i.putExtra("GameName", strGameName);
                        i.putExtra("GameID", nGameID);
                        i.putExtra("Login", strLogin);
                        i.putExtra("LoginID", nLoginID);
                        activity.startActivity(i);
                        activity.finish();
                    }else {
                        toast(res.getString(R.string.txtWrongPassword));
                    }
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