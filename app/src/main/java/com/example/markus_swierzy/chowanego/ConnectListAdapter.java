package com.example.markus_swierzy.chowanego;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by markus_swierzy on 2017-03-14.
 */

public class ConnectListAdapter extends BaseAdapter {

    private List<GameInfo> items;
    private Context context;
    private final Map<View, Map<Integer,View>> cache = new HashMap<View, Map<Integer,View>>();

    public ConnectListAdapter(Context context, List<GameInfo> items) {
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
        return getItem( position ).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        TextView tv;
        ImageView iv;

        if ( v == null )
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            v = inflater.inflate( R.layout.connect_list_item, parent, false );
        }
        Map<Integer, View> itemMap = cache.get( v );
        if( itemMap == null )
        {
            itemMap = new HashMap<Integer, View>();
            tv = (TextView) v.findViewById( R.id.tvConnectListItemGameName );
            iv = (ImageView) v.findViewById( R.id.ivConnectListItemHelp );
            itemMap.put( R.id.tvConnectListItemGameName, tv );
            itemMap.put(R.id.ivConnectListItemHelp, iv );
            cache.put( v, itemMap );
        }
        else
        {
            tv = (TextView)itemMap.get( R.id.tvConnectListItemGameName );
            iv = (ImageView)itemMap.get( R.id.ivConnectListItemHelp );
        }
        final GameInfo item = (GameInfo) getItem( position );
        final Context mContext = this.context;
        tv.setText( item.getGameName() );
        iv.setOnClickListener( new View.OnClickListener()
        {

            @Override
            public void onClick( View v )
            {

                String strGameName = mContext.getString(R.string.txtGameName) + ": " + item.getGameName();
                //String strStatus = mContext.getString(R.string.txtGameStatus)+ ": " + item.getStatusString(context);
                //String strPlayers = mContext.getString(R.string.txtPlayersCount) + ": " + Integer.toString(item.getPlayersCnt());

                /*Toast.makeText( context,
                        String.format( "%s\n%s\n%s", strGameName, strStatus, strPlayers),
                        Toast.LENGTH_SHORT ).show();
                        */
                Toast.makeText( context,
                        String.format( "%s", strGameName),
                        Toast.LENGTH_SHORT ).show();
            }
        } );
        return v;
    }
}
