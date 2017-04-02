package com.example.markus_swierzy.chowanego;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wiktor on 27.03.2017.
 */

class CreateNewGame extends AsyncTask<MyTaskParams, Void, Void> {

    private Activity activity;

    JSONParser jsonParser = new JSONParser();

    private String message;
    private int success;
    private int idGry;
    private int idGracza;
    private long endHideTime;
    private long endSearchTime;

    String latitudePos;// = CreateDialog.nLatitude;
    String longitudePos;// = CreateDialog.nLongitude;

    // url to create new product
    private static String url_create_product = "http://kinwsm2017.cba.pl/podchody/create_game.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_ID_GRY = "ID_Gry";
    private static final String TAG_ID_GRACZA = "ID_Gracza";
    private static final String TAG_HIDE_TIME = "HideTime";
    private static final String TAG_SEARCH_TIME = "SearchTime";


    /**
     * Before starting background thread Show Progress Dialog
     */

    public CreateNewGame(Activity activity, String latitude, String longitude){
        this.activity = activity;
        this.latitudePos = latitude;
        this.longitudePos = longitude;
    }

    /**
     * Creating product
     */

    protected Void doInBackground(MyTaskParams... args) {

        String Nazwa_gry = args[0].Nazwa_gry,
                Haslo_gry = args[0].Haslo_gry,
                Login = args[0].Login,
                Czas_szukania = args[0].Czas_szukania,
                Czas_ukrycia = args[0].Czas_ukrycia,
                Latitude = args[0].Latitude,
                Longitude = args[0].Longitude;

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Nazwa_gry", Nazwa_gry));
        params.add(new BasicNameValuePair("Haslo_gry", Haslo_gry));
        params.add(new BasicNameValuePair("Login", Login));
        params.add(new BasicNameValuePair("Czas_szukania", Czas_szukania));
        params.add(new BasicNameValuePair("Czas_ukrycia", Czas_ukrycia));
        params.add(new BasicNameValuePair("Latitude", Latitude));
        params.add(new BasicNameValuePair("Longitude", Longitude));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                "POST", params);

        // check log cat fro response
        Log.d("Create Response", json.toString());

        // check for success tag
        try {
            this.success = json.getInt(TAG_SUCCESS);
            this.message = json.getString(TAG_MESSAGE);
            this.idGry = Integer.parseInt(json.getString(TAG_ID_GRY));
            this.idGracza = Integer.parseInt(json.getString(TAG_ID_GRACZA));
            this.endHideTime = json.getLong(TAG_HIDE_TIME);
            this.endSearchTime = json.getLong(TAG_SEARCH_TIME);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     **/
    protected void onPostExecute(String file_url) {
    }

    public String getMessage() {
        return this.message;
    }

    public int getSuccess() {
        return this.success;
    }

    public int getGameID() {
        return this.idGry;
    }

    public int getLoginID() {
        return this.idGracza;
    }

    public long getEndHideTime() { return this.endHideTime; }

    public long getEndSearchTime() { return this.endSearchTime; }
}