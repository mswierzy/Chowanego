package com.example.markus_swierzy.chowanego;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by markus_swierzy on 2017-03-19.
 */

public class GameSearcher extends AppCompatActivity implements SensorEventListener, GameCatchedDialog.OnCompleteListener, GameCatchedDialog.OnDismissListener, GameDialogQuit.OnCancelListener, GameDialogQuit.OnExitListener, GameDialogEndOfTime.OnOkListener {

    private static String TAG = GameSearcher.class.getSimpleName();

    private int nGameID = -1;
    private String strLogin = "";
    private String strHiddenLogin = "";
    private int nLoginID = -1;
    private int nHiddenLoginID = 1;
    private String strGameName = "";
    int nHiddenLoginPosition = 1;
    private long endSearchTime=-1L;

    /*
        Zmienne zmiany koloru cieplo-zimno
     */
    private double dblDistance = 0.0; // w metrach
    private double dblPreviousDistance = 0.0;
    private double dblMaxDistance = 50.0;
    TextView tvDistance;
    TextView tvDistanceDifference;

    RelativeLayout DistanceRectangle;
    /*
        Zmienne panelu Drawer
     */
    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    GameSearcherDrawer adapter;

    ArrayList<UserInfo> ListItems = new ArrayList<UserInfo>();

    /*
        Zmienne timera
     */
    private TextView timerValue;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    private long SearchTime = 100000L;

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    // aktualny czas
    private long unixTime;

    /*
        Zmienne kompasu
     */
    private ImageView image;
    private float currentDegree = 0f;
    private SensorManager mSensorManager;

    /*
        Zmienne aktualnej pozycji
     */
    private double Latitude = CHMainMenu.latitude;
    private double Longitude = CHMainMenu.longitude;

    /*
        Zmienne pozycji ukrywajacego sie
     */
    double latitude_ukrywajacego = 0d;
    double longitude_ukrywajacego = 0d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_searcher);

        Button btnExit = (Button) findViewById(R.id.btnGameSearcherQuit);
        Button catched = (Button) findViewById(R.id.btnGameSearcherCatched);
        DistanceRectangle = (RelativeLayout) findViewById(R.id.rlGameSearcherCompassBackground);
        TextView txtGameName = (TextView) findViewById(R.id.tvGameSearcherGameName);
        TextView txtLogin = (TextView) findViewById(R.id.txtGameSearcherLogin);
        TextView txtHiddenLogin = (TextView) findViewById(R.id.tvGameSearcherPlayerToFind);

        tvDistance = (TextView) findViewById(R.id.tvGameSearcherDistance);
        tvDistanceDifference = (TextView) findViewById(R.id.tvGameSearcherDistanceDifference);

        strGameName = getIntent().getStringExtra("GameName");
        nGameID = getIntent().getIntExtra("GameID",-1);
        strLogin = getIntent().getStringExtra("Login");
        nLoginID = getIntent().getIntExtra("LoginID",-1);
        strHiddenLogin = getIntent().getStringExtra("HiddenLogin");
        nHiddenLoginID = getIntent().getIntExtra("HiddenLoginID",1);
        endSearchTime = getIntent().getLongExtra("endSearchTime",-1);
        latitude_ukrywajacego = getIntent().getDoubleExtra("latitude_ukrywajacego",0);
        longitude_ukrywajacego = getIntent().getDoubleExtra("longitude_ukrywajacego", 0);

        txtGameName.setText(strGameName);
        txtLogin.setText(strLogin);
        txtHiddenLogin.setText(strHiddenLogin);

        unixTime = System.currentTimeMillis();
        SearchTime = endSearchTime - unixTime;
        if(SearchTime < 0)
        {
            SearchTime = 0;
        }

        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(Count, 500); // tmp 500. bylo 0
        customHandler.postDelayed(ChangeColor, 500); //jw.


        GetPlayers players = new GetPlayers(ListItems, nGameID, Latitude, Longitude);
        try {
            players.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ListItems = players.getList();


        if (!ListItems.isEmpty()){
            latitude_ukrywajacego = ListItems.get(nHiddenLoginPosition).dblLatitude;
            longitude_ukrywajacego = ListItems.get(nHiddenLoginPosition).dblLongitude;
        }

        customHandler.postDelayed(StartNewActivity, SearchTime);
        /*
            Kompas
         */
        image = (ImageView) findViewById(R.id.imageGameSearcherViewCompass);
        timerValue = (TextView) findViewById(R.id.tvGameSearcherTimer);

        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_game_searcher);

        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.rlGameSearcherDrawerPane);
        mDrawerList = (ListView) findViewById(R.id.lvGameSearcherList);
        adapter = new GameSearcherDrawer(this, ListItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });

        catched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                GameCatchedDialog dialogFragment = new GameCatchedDialog();
                dialogFragment.show(fm, "Catched");
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dolacz do gry!!!!
                FragmentManager fm = getFragmentManager();
                GameDialogQuit dialogFragment = new GameDialogQuit();
                dialogFragment.show(fm, "Quit Game");
            }
        });
    }

    /*
* Called when a particular item from the navigation drawer
* is selected.
* */
    private void selectItemFromDrawer(int position) {
        strHiddenLogin = ListItems.get(position).strLogin;
        nHiddenLoginID = ListItems.get(position).nUserID;
        latitude_ukrywajacego = ListItems.get(position).dblLatitude;
        longitude_ukrywajacego = ListItems.get(position).dblLongitude;
        nHiddenLoginPosition = position;
        customHandler.removeCallbacks(Count);
        customHandler.removeCallbacks(StartNewActivity);
        customHandler.removeCallbacks(ChangeColor);
        Intent create = new Intent(GameSearcher.this, GameSearcher.class);
        create.putExtra("GameID", nGameID);
        create.putExtra("GameName", strGameName);
        create.putExtra("Login", strLogin);
        create.putExtra("HiddenLogin", strHiddenLogin);
        create.putExtra("HiddenLoginID", nHiddenLoginID);
        create.putExtra("endSearchTime", endSearchTime);
        create.putExtra("latitude_ukrywajacego", latitude_ukrywajacego);
        create.putExtra("longitude_ukrywajacego", longitude_ukrywajacego);
        GameSearcher.this.startActivity(create);
        GameSearcher.this.finish();
    }

    private void ChangeCompassBackgroundColor(){
        float hsv[] = {0, 0 ,0};
        float flHue = 0;
        float flSaturation = 0;
        double dblDistanceChange = 0.0;

        dblDistanceChange = this.dblPreviousDistance - this.dblDistance;

        if (dblDistanceChange < 0) {
            flHue = 255;
        }else flHue = 0;

        flSaturation = this.GetSaturation(dblDistanceChange, this.dblMaxDistance);
        hsv[0] = flHue;
        hsv[1] = flSaturation;
        hsv[2] = 1;

        DistanceRectangle.setBackgroundColor(Color.HSVToColor(hsv));
    }

    private float GetSaturation(double Distance, double Max){
        float flSaturation = 0;
        double absDistance = Math.abs(Distance);
        if (absDistance > Max){
            flSaturation = 1;
        }else{
            flSaturation = (1/(float)Max)*(float)absDistance;
        }

        if (flSaturation > 1) flSaturation = 1;

        return flSaturation;
    }

    private void toast( String text )
    {
        Toast.makeText( GameSearcher.this,
                String.format( "%s", text ), Toast.LENGTH_SHORT )
                .show();
    }

    public void onComplete() {
        int nIDGraczaKtoregoSzukam = ListItems.get(nHiddenLoginPosition).nUserID;

        // UPDATE W BAZIE POLA O ZLAPANIU - UPDATE POCHODZI OD SZUKAJACEGO
        MyTaskParams_updateZlapany args = new MyTaskParams_updateZlapany(nIDGraczaKtoregoSzukam);
        UpdateZlapanyOdUkrywajacego updZlapOdSzuk = new UpdateZlapanyOdUkrywajacego(nIDGraczaKtoregoSzukam);

        try {
            updZlapOdSzuk.execute(args).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (updZlapOdSzuk.getSuccess() == 1 ) {
            toast(updZlapOdSzuk.getMessage());
        }
        else
        {
            toast(updZlapOdSzuk.getMessage());
        }

        /*
            usuń gracza z listy ListItems
         */
        ListItems.remove(nHiddenLoginPosition);
        adapter.notifyDataSetChanged();
    }

    public void onDismiss() {

    }

    public void onExit() {

        // usuniecie z listy
        ListItems.remove(nHiddenLoginPosition);

        // wylogowanie z bazy
        MyTaskParams_deletePlayer args = new MyTaskParams_deletePlayer(nLoginID);
        DeletePlayer delSearcher = new DeletePlayer(nLoginID);

        try {
            delSearcher.execute(args).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (delSearcher.getSuccess() == 1 ) {
            toast(delSearcher.getMessage());
        }
        else
        {
            toast(delSearcher.getMessage());
        }

        customHandler.removeCallbacks(Count);
        customHandler.removeCallbacks(StartNewActivity);
        customHandler.removeCallbacks(ChangeColor);
        Intent create = new Intent(GameSearcher.this, CHMainMenu.class);
        GameSearcher.this.startActivity(create);
        GameSearcher.this.finish();
        overridePendingTransition(R.layout.fadein, R.layout.fadeout);
    }

    public void onCancel() {
    }

    public void onOk(){
        customHandler.removeCallbacks(Count);
        customHandler.removeCallbacks(ChangeColor);
        Intent create = new Intent(GameSearcher.this, RecreateGame.class);
        create.putExtra("GameID", nGameID);
        create.putExtra("GameName", strGameName);
        create.putExtra("Login", strLogin);
        create.putExtra("LoginID", nLoginID);
        GameSearcher.this.startActivity(create);
        GameSearcher.this.finish();
        overridePendingTransition(R.layout.fadein, R.layout.fadeout);
    }

    private void OpenRecreateGameActivity(){
        FragmentManager fm = getFragmentManager();
        GameDialogEndOfTime dialogFragment = new GameDialogEndOfTime();
        dialogFragment.show(fm, "End of Time");
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        GameDialogQuit dialogFragment = new GameDialogQuit();
        dialogFragment.show(fm, "Quit Game");
    }

    /*
        Odliczanie zegara
    */
    private Runnable Count = new Runnable() {

        public void run() {

            timeInMilliseconds = SearchTime - (SystemClock.uptimeMillis() - startTime);
            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }

    };

    private Runnable StartNewActivity = new Runnable() {

        public void run() {
            OpenRecreateGameActivity();
        }

    };

    private Runnable ChangeColor = new Runnable() {

        public void run() {
            dblPreviousDistance = dblDistance;

            Latitude = CHMainMenu.latitude;
            Longitude = CHMainMenu.longitude;

            // ustalenie aktualnej lokalizacji szukajacego
            Location actLocation = new Location("");
            actLocation.setLatitude(Latitude);
            actLocation.setLongitude(Longitude);

            // ustalenie lokalizacji ukrywajacego sie, na podstawie danych pobranych z bazy
            Location playerLocation = new Location("");
            playerLocation.setLatitude(latitude_ukrywajacego);
            playerLocation.setLongitude(longitude_ukrywajacego);

            dblDistance =  playerLocation.distanceTo(actLocation);

            ChangeCompassBackgroundColor();
            tvDistance.setText(String.valueOf((int) Math.round(dblDistance)));
            tvDistanceDifference.setText(String.valueOf((int) Math.round(dblPreviousDistance-dblDistance)));
            customHandler.postDelayed(this, 5000);
        }

    };

    @Override
    protected void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }



    @Override
    protected void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);

        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(210);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        image.startAnimation(ra);
        currentDegree = -degree;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }

}

