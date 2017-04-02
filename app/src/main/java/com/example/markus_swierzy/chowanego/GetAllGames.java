package com.example.markus_swierzy.chowanego;

/**
 * Created by Wiktor on 27.03.2017.
 */



import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

class GetAllGames extends AsyncTask<String, String, String> {

    //Activity activity;
    private List<GameInfo> GameInfoList;

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

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
    //private static final String TAG_CZAS_SZUKANIA = "Czas_szukania_ms";
    //private static final String TAG_CZAS_UKRYWANIA = "Czas_ukrywania_ms";

    // products JSONArray
    JSONArray games = null;


    public GetAllGames(List<GameInfo> GameInfoList){
        this.GameInfoList = GameInfoList;
    }

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
                // games found
                // Getting Array of games
                games = json.getJSONArray(TAG_GRY);

                // looping through All games
                for (int i = 0; i < games.length(); i++) {
                    JSONObject c = games.getJSONObject(i);

                    // Storing each json item in variable
                    int game_id = c.getInt(TAG_ID_GRY);
                    String game_name = c.getString(TAG_NAZWA_GRY);
                    String game_password = c.getString(TAG_HASLO_GRY);
                    long game_endHideTime = c.getLong(TAG_KONIEC_UKRYWANIA);
                    long game_endSearchTime = c.getLong(TAG_KONIEC_SZUKANIA);

                    //long game_msHideTime = c.getLong(TAG_CZAS_UKRYWANIA);
                    //long game_msSearchTime = c.getLong(TAG_CZAS_SZUKANIA);

                    GameInfoList.add(new GameInfo(game_id, game_name, game_password, game_endHideTime, game_endSearchTime));
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

    public List<GameInfo> getList() {
        return GameInfoList;
    }
}
