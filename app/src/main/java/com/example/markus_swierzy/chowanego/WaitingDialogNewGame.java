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
import android.widget.Toast;

/**
 * Created by markus_swierzy on 2017-03-23.
 */

public class WaitingDialogNewGame extends DialogFragment {


    private OnCancelListener mCancelListener;
    private OnPlayListener mPlayListener;

    private Resources res;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.waiting_dialog_new_game, container, false);

        res = getResources();

        Button play = (Button) rootView.findViewById(R.id.btnWaitingDialogNewGameStart);
        Button cancel = (Button) rootView.findViewById(R.id.btnWaitingDialogNewGameCancel);

        getDialog().setTitle(res.getString(R.string.txtPlayNewGame) + "???");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnCancel();
                dismiss();
            }
        });


        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                OnPlay();
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }

    public void OnPlay(){
        this.mPlayListener.onPlay();
    }

    public void OnCancel(){
        this.mCancelListener.onCancel();
    }

    public static interface OnPlayListener {
        public abstract void onPlay();
    }

    public static interface OnCancelListener {
        public abstract void onCancel();
    }

    // make sure the Activity implemented it
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mPlayListener = (OnPlayListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnPlayListener");
        }

        try {
            this.mCancelListener = (OnCancelListener) activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCancelListener");
        }
    }

}
