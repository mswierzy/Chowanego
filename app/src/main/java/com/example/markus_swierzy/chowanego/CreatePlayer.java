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
 * Created by Wiktor on 02.04.2017.
 */

class CreatePlayer extends AsyncTask<MyTaskParams_createPlayer, Void, Void> {

    private Activity activity;

    JSONParser jsonParser = new JSONParser();

    private String message;
    private int success;
    private int idGry;
    private int idGracza;
    private String Login;

    private double latitudePos;// = CreateDialog.nLatitude;
    private double longitudePos;// = CreateDialog.nLongitude;

    // url to create new product
    private static String url_create_product = "http://kinwsm2017.cba.pl/podchody/create_player.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_ID_GRY = "ID_Gry";
    private static final String TAG_ID_GRACZA = "ID_Gracza";
    private static final String TAG_LOGIN = "Login";
    private static final String TAG_LATITUDE = "Latitude";
    private static final String TAG_LONGITUDE = "Longitude";


    /**
     * Before starting background thread Show Progress Dialog
     */

    public CreatePlayer(Activity activity, double latitude, double longitude, String Login, int ID_Gry){
        this.activity = activity;
        this.latitudePos = latitude;
        this.longitudePos = longitude;
        this.Login = Login;
        this.idGry = ID_Gry;
    }

    /**
     * Creating product
     */

    protected Void doInBackground(MyTaskParams_createPlayer... args) {

        String Login = args[0].Login;
        int ID_Gry = args[0].ID_Gry;
        double Latitude = args[0].Latitude,
                Longitude = args[0].Longitude;

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Login", Login));
        params.add(new BasicNameValuePair("ID_Gry", Integer.toString(ID_Gry)));
        params.add(new BasicNameValuePair("Latitude", Double.toString(Latitude)));
        params.add(new BasicNameValuePair("Longitude", Double.toString(Longitude)));

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
            this.idGracza = Integer.parseInt(json.getString(TAG_ID_GRACZA));
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

    public int getGameID() { return this.idGry; }

    public int getPlayerID() { return this.idGracza; }

}
