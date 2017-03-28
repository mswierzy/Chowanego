package com.example.markus_swierzy.chowanego;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by markus_swierzy on 2017-03-22.
 */

public class GameDialogQuit extends DialogFragment {

    private OnCancelListener mCancelListener;
    private OnExitListener mExitListener;


    Resources res;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.game_dialog_quit, container, false);

        res = getResources();

        Button exit = (Button) rootView.findViewById(R.id.btnGameDialogQuitExit);
        Button cancel = (Button) rootView.findViewById(R.id.btnGameDialogQuitCancel);

        getDialog().setTitle(res.getString(R.string.txtDoYouReallyWantToQuit) + "???");
/*
        strGameName = getArguments().getString("GameName");
        strLogin = getArguments().getString("Login");
        nLoginID = getArguments().getInt("LoginID");
        nGameID = getArguments().getInt("GameID");
*/

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnCancel();
                dismiss();
            }
        });


        exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                OnExit();
                dismiss();
            }
        });

        return rootView;
    }

    public void OnExit(){
        this.mExitListener.onExit();
    }

    public void OnCancel(){
        this.mCancelListener.onCancel();
    }

    public static interface OnExitListener {
        public abstract void onExit();
    }

    public static interface OnCancelListener {
        public abstract void onCancel();
    }

    // make sure the Activity implemented it
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mExitListener = (OnExitListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnExitListener");
        }

        try {
            this.mCancelListener = (OnCancelListener) activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCancelListener");
        }
    }

}
