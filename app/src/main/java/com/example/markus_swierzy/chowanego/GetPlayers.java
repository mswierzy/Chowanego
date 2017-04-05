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

class GetPlayers extends AsyncTask<String, String, String> {

    //Activity activity;
    private ArrayList<UserInfo> UserInfoList;
    private int ID_Gry;
    private double actLatitude;
    private double actLongitude;

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    // url to get all products list
    private static String url_all_products = "http://kinwsm2017.cba.pl/podchody/get_ukrywajacy_z_gry.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PLAYERS = "players";
    private static final String TAG_ID_GRACZA = "ID_Gracza";
    private static final String TAG_LOGIN = "Login";
    private static final String TAG_LATITUDE = "Latitude";
    private static final String TAG_LONGITUDE = "Longitude";

    // products JSONArray
    JSONArray players = null;


    public GetPlayers(ArrayList<UserInfo> UserInfoList, int ID_Gry, double actLatitude, double actLongitude){
        this.UserInfoList = UserInfoList;
        this.ID_Gry = ID_Gry;
        this.actLatitude = actLatitude;
        this.actLongitude = actLongitude;
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
                    int player_id = c.getInt(TAG_ID_GRACZA);
                    String player_login = c.getString(TAG_LOGIN);
                    double player_latitude = c.getDouble(TAG_LATITUDE);
                    double player_longitude = c.getDouble(TAG_LONGITUDE);

                    // ustalenie aktualnej lokalizacji szukajacego
                    Location actLocation = new Location("");
                    actLocation.setLatitude(actLatitude);
                    actLocation.setLongitude(actLongitude);

                    // ustalenie lokalizacji ukrywajacego sie, na podstawie danych pobranych z bazy
                    Location playerLocation = new Location("");
                    playerLocation.setLatitude(player_latitude);
                    playerLocation.setLongitude(player_longitude);

                    double distanceInMeters =  playerLocation.distanceTo(actLocation);

                    UserInfoList.add(new UserInfo(player_login, distanceInMeters, player_id, player_latitude, player_longitude));
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

    public ArrayList<UserInfo> getList() {
        return UserInfoList;
    }
}
