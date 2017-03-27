package com.example.markus_swierzy.chowanego;

/**
 * Created by Wiktor on 27.03.2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.FloatProperty;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;



class GetAllGames extends AsyncTask<String, String, String> {

    //Activity activity;
    private List<GameInfo> GameInfoList;

    String strLogin = "";
    String strSearch = "";
    String strGameName = "";
    int nSelected = -1;


    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    //ArrayList<HashMap<String, String>> gamesList;

    // url to get all products list
    private static String url_all_products = "http://kinwsm2017.cba.pl/podchody/get_games.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_GRY = "gry";
    private static final String TAG_NAZWA_GRY = "Nazwa_gry";
    private static final String TAG_HASLO_GRY = "Haslo_gry";
    private static final String TAG_ID_GRY = "ID_Gry";
    private static final String TAG_KONIEC_SZUKANIA = "Koniec_czasu_szukania";
    private static final String TAG_KONIEC_UKRYWANIA = "Koniec_czasu_ukrycia";

    // products JSONArray
    JSONArray games = null;


    public GetAllGames(List<GameInfo> GameInfoList){
        //this.activity = activity;
        this.GameInfoList = GameInfoList;
    }

//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        pDialog = new ProgressDialog(activity);
//        pDialog.setMessage("Loading points. Please wait...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();
//    }
    /**
     * getting All products from url
     * */
    protected String doInBackground(String... args) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

        // Check your log cat for JSON reponse
        Log.d("All Points: ", json.toString());

        try {
            // Checking for SUCCESS TAG
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // products found
                // Getting Array of Products
                games = json.getJSONArray(TAG_GRY);

                // looping through All Products
                for (int i = 0; i < games.length(); i++) {
                    JSONObject c = games.getJSONObject(i);

                    // Storing each json item in variable\
                    //Integer id = Integer.parseInt(c.getString(TAG_ID));
                    int game_id = c.getInt(TAG_ID_GRY);
                    String game_name = c.getString(TAG_NAZWA_GRY);
                    String game_password = c.getString(TAG_HASLO_GRY);
                    String game_endSearchTime = c.getString(TAG_KONIEC_SZUKANIA);
                    String game_endHideTime = c.getString(TAG_KONIEC_UKRYWANIA);

                    GameInfoList.add(new GameInfo(game_id, game_name, game_password, game_endHideTime, game_endSearchTime));



/*
                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put(TAG_ID_GRY, game_id);
                    map.put(TAG_NAZWA_GRY, game_name);

                    // adding HashList to ArrayList
                    gamesList.add(map);
*/
                }
            } else {
                // no products found
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/

    protected void onPostExecute(String file_url) {
        // dismiss the dialog after getting all products
//        pDialog.dismiss();
//        // updating UI from Background Thread
//        runOnUiThread(new Runnable() {
//            public void run() {
//
//                //Updating parsed JSON data into ListView
//
//                ListAdapter adapter = new SimpleAdapter(
//                        Connect.this, gamesList,
//                        R.layout.item, new String[] { TAG_ID_GRY,
//                        TAG_NAZWA_GRY},
//                        new int[] { R.id.textView_gameId, R.id.text1 });
//                // updating listview
//                ListView listView = (ListView) findViewById(R.id.list);
//                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//                listView.setAdapter(adapter);
//                //setListAdapter(adapter);
//
//                // tmp wiktor
///*
//                    CustomAdapter adapter = new CustomAdapter( Connect.this, gamesList);
//
//                    ListView listView = (ListView)findViewById( R.id.list );
//                    listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//                    listView.setAdapter( adapter );
//*/
//                // tmp wiktor koniec
//            }
//        });

    }

    public List<GameInfo> getList() {
        return GameInfoList;
    }
}
