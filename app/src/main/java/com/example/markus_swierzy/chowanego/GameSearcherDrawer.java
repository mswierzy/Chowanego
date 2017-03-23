package com.example.markus_swierzy.chowanego;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by markus_swierzy on 2017-03-23.
 */

public class GameSearcherDrawer extends BaseAdapter {

    Context context;
    ArrayList<UserInfo> items;

    public GameSearcherDrawer(Context context, ArrayList<UserInfo> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.game_searcher_drawer_item, null);
        }
        else {
            view = convertView;
        }

        TextView tvLogin = (TextView) view.findViewById(R.id.tvGameSearcherDrawerLogin);
        TextView tvDistance = (TextView) view.findViewById(R.id.tvGameSearcherDrawerDistance);
        //ImageView iconView = (ImageView) view.findViewById(R.id.icon);

        tvLogin.setText( items.get(position).strLogin );
        tvDistance.setText( Double.toString(items.get(position).dblDistance) );
        //iconView.setImageResource(items.get(position).mIcon);

        return view;
    }
}
