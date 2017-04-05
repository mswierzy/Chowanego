package com.example.markus_swierzy.chowanego;

/**
 * Created by Wiktor on 27.03.2017.
 */


import android.app.ProgressDialog;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class GetLoginSearcher extends AsyncTask<String, String, String> {

    private int ID_Gry;
    private String LoginSearcher;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    // url to get all products list
    private static String url_all_products = "http://kinwsm2017.cba.pl/podchody/get_login_searcher.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    //private static final String TAG_SEARCHER = "searcher";
    private static final String TAG_SEARCHER_LOGIN = "Login";

/*
    // products JSONArray
    JSONArray searcher = null;
*/

    public GetLoginSearcher(int ID_Gry){
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
                LoginSearcher = json.getString(TAG_SEARCHER_LOGIN);
            } else {
                LoginSearcher = "Temp Searcher";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    protected void onPostExecute(String file_url) {
    }

    public String getSearcherLogin() {
        return LoginSearcher;
    }
}
