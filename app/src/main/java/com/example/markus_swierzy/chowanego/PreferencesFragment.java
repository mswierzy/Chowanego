package com.example.markus_swierzy.chowanego;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by markus_swierzy on 2017-03-20.
 */

public class PreferencesFragment extends Fragment {

    int position = -1;

    public PreferencesFragment() {
        // Required empty public constructor
    }

   /* public void SetPosition(int position){
        this.position = position;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.drawer_layout, container, false);
    }

}
