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

    private OnCompleteListener mListener;
    private OnDismissListener mDismissListener;

    Resources res;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.game_catched_dialog, container, false);

        res = getResources();

        getDialog().setTitle(res.getString(R.string.txtCatched) + "???");

        Button no = (Button) rootView.findViewById(R.id.btnGameCatchedNo);

        no.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                OnNo();
                dismiss();
            }
        });


        Button yes = (Button) rootView.findViewById(R.id.btnGameCatchedYes);
        yes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                OnYes();
                dismiss();
            }
        });

        return rootView;
    }

    public void OnYes(){
        this.mListener.onComplete();
    }

    public void OnNo(){
        this.mDismissListener.onDismiss();
    }

    public static interface OnCompleteListener {
        public abstract void onComplete();
    }

    public static interface OnDismissListener {
        public abstract void onDismiss();
    }

    // make sure the Activity implemented it
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }

        try {
            this.mDismissListener = (OnDismissListener) activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnDismissListener");
        }
    }
}
