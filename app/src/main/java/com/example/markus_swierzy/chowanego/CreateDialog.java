package com.example.markus_swierzy.chowanego;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * Created by markus_swierzy on 2017-03-22.
 */

public class CreateDialog extends DialogFragment {

    private Activity activity;
    private String strGameName = "";
    private String strLogin = "";
    private String strPassword = "";
    private int nSearchTime = 30;
    private int nHideTime = 10;
    private double nLatitude;
    private double nLongitude;
    private int nGameID = -1;
    private int nLoginID = -1;

    Resources res;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.create_dialog, container, false);

        res = getResources();

        activity = getActivity();

        getDialog().setTitle(res.getString(R.string.txtCreateGame) + "???");

        Button dismiss = (Button) rootView.findViewById(R.id.btnCreateDialogCancel);
        Button create = (Button) rootView.findViewById(R.id.btnCreateDialogCreate);

        strGameName = getArguments().getString("GameName");
        strPassword = getArguments().getString("Password");
        strLogin = getArguments().getString("Login");
        nHideTime = getArguments().getInt("HideTime");
        nSearchTime = getArguments().getInt("SearchTime");
        nLatitude = getArguments().getDouble("nLatitude");
        nLongitude = getArguments().getDouble("nLongitude");

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        create.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent i = new Intent(activity, HideSearcher.class);

                String Nazwa_gry = strGameName;
                String Haslo_gry = strPassword;
                String Login = strLogin;
                String Czas_szukania = Integer.toString(nSearchTime);
                String Czas_ukrycia = Integer.toString(nHideTime);
                String Latitude = Double.toString(nLatitude);
                String Longitude = Double.toString(nLongitude);

                MyTaskParams args = new MyTaskParams(Nazwa_gry, Haslo_gry, Login, Czas_szukania, Czas_ukrycia, Latitude, Longitude);
                CreateNewGame newGame = new CreateNewGame(activity, Latitude, Longitude);
                try {
                    newGame.execute(args).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


                if (newGame.getSuccess() == 1 ){
                    toast(newGame.getMessage());
                    nGameID = newGame.getGameID();
                    nLoginID = newGame.getLoginID();
                    activity.startActivity(i);
                    activity.finish();
                }
                else{
                    toast(newGame.getMessage());
                    dismiss();
                }

                i.putExtra("GameName", strGameName);
                i.putExtra("Login", strLogin);
                i.putExtra("GameID", nGameID);
                i.putExtra("LoginID", nLoginID);
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
