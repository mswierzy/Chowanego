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

public class UpdateZlapanyOdSzukajacego extends AsyncTask<MyTaskParams_updateZlapany, Void, Void> {

    private Activity activity;

    JSONParser jsonParser = new JSONParser();

    private String message;
    private int success;
    private int idGracza;

    // url to create new product
    private static String url_create_product = "http://kinwsm2017.cba.pl/podchody/update_zlapany_od_szukajacego.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_ID_GRACZA = "ID_Gracza";

    /**
     * Before starting background thread Show Progress Dialog
     */

    public UpdateZlapanyOdSzukajacego(int ID_Gracza){
        this.idGracza = ID_Gracza;
    }

    /**
     * Creating product
     */

    protected Void doInBackground(MyTaskParams_updateZlapany... args) {

        int ID_Gracza = args[0].ID_Gracza;

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("ID_Gracza", Integer.toString(ID_Gracza)));

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

}
