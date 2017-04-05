package com.example.markus_swierzy.chowanego;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wiktor on 05.04.2017.
 */

public class GetToCatch extends AsyncTask<String, String, String> {

    //Activity activity;
    private ArrayList<String> ToCatchLoginList;
    private int ID_Gry;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    // url to get all products list
    private static String url_all_products = "http://kinwsm2017.cba.pl/podchody/get_to_catch.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PLAYERS = "players";
    private static final String TAG_LOGIN = "Login";


    // products JSONArray
    JSONArray players = null;


    public GetToCatch(ArrayList<String> ToCatchLoginList, int ID_Gry){
        this.ToCatchLoginList = ToCatchLoginList;
        this.ID_Gry = ID_Gry;
    }

    /**
     * getting All products from url
     * */
    protected String doInBackground(String... args) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("ID_Gry", Integer.toString(ID_Gry)));
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

        // Check your log cat for JSON reponse
        Log.d("All Points: ", json.toString());

        try {
            // Checking for SUCCESS TAG
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // games found
                // Getting Array of games
                players = json.getJSONArray(TAG_PLAYERS);

                // looping through All games
                for (int i = 0; i < players.length(); i++) {
                    JSONObject c = players.getJSONObject(i);

                    // Storing each json item in variable
                    String player_login = c.getString(TAG_LOGIN);


                    ToCatchLoginList.add(player_login);
                }
            } else {
                // no games found
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
    }

    public ArrayList<String> getList() {
        return ToCatchLoginList;
    }
}
