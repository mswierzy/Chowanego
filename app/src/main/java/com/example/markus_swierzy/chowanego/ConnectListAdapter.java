package com.example.markus_swierzy.chowanego;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by markus_swierzy on 2017-03-14.
 */

public class ConnectListAdapter extends ArrayAdapter<ConnectRow> {

    private List<ConnectRow> items;
    private int layoutResourceId;
    private Context context;

    public ConnectListAdapter(Context context, int layoutResourceId, List<ConnectRow> items) {
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ConnectHolder holder = null;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        holder = new ConnectHolder();
        holder.connectRow = items.get(position);
        holder.ChooseGame = (RadioButton)row.findViewById(R.id.btnConnect);
        //holder.removePaymentButton.setTag(holder.connectRow);

        holder.name = (TextView)row.findViewById(R.id.txtNameConnectRow);
        holder.value = (TextView)row.findViewById(R.id.txtPlayersConnectRow);
        holder.ChooseGame = (RadioButton)row.findViewById(R.id.btnConnect);

        holder.ChooseGame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
// set Yes values in ArrayList if RadioButton is checked
                if (isChecked)
                    Log.d(".chowanego","Wlaz");
            }
        });

        holder.ChooseGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dolacz do gry!!!!
                Log.d(".chowanego", "Wlaz2");
            }
        });
        row.setTag(holder);

        setupItem(holder);
        return row;
    }

    private void setupItem(ConnectHolder holder) {
        holder.name.setText(holder.connectRow.getGameName());
        holder.value.setText(String.valueOf(holder.connectRow.getPlayers()));
    }

    public static class ConnectHolder {
        ConnectRow connectRow;
        TextView name;
        TextView value;
        RadioButton ChooseGame;
    }
}
