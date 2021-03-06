package com.example.markus_swierzy.chowanego;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by markus_swierzy on 2017-03-29.
 */

public class DialogStartSearching extends DialogFragment {

    private Activity activity;
    private OnOkListener mOkListener;

    Resources res;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.dialog_start_searching, container, false);

        res = getResources();
        activity = getActivity();

        getDialog().setTitle(res.getString(R.string.txtStartSearching) + "!!!");

        Button ok = (Button) rootView.findViewById(R.id.btnStartSearchingOk);

        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                OnOk();
                dismiss();
            }
        });

        return rootView;
    }

    public void OnOk(){
        this.mOkListener.onOk();
    }


    public static interface OnOkListener {
        public abstract void onOk();
    }

    // make sure the Activity implemented it
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mOkListener = (OnOkListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnOkListener");
        }
    }
}
