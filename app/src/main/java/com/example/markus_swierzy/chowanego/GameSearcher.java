package com.example.markus_swierzy.chowanego;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

/**
 * Created by markus_swierzy on 2017-03-19.
 */

public class GameSearcher extends AppCompatActivity implements SensorEventListener {

    private static String TAG = GameSearcher.class.getSimpleName();

    private int nGameID = -1;
    private String strLogin = "";
    private String strHiddenLogin = "";
    private int nLoginID = -1;
    private int nHiddenLoginID = -1;
    private String strGameName = "";
    int position = 1;

    /*
        Zmienne panelu Drawer
     */
    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    ArrayList<UserInfo> ListItems = new ArrayList<UserInfo>();

    /*
        Zmienne timera
     */
    private TextView timerValue;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    private long HideTime = 100000L;

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    /*
        Zmienne kompasu
     */
    private ImageView image;
    private float currentDegree = 0f;
    private SensorManager mSensorManager;
    TextView tvHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_searcher);

        Button catched = (Button) findViewById(R.id.btnGameSearcherCatched);
        TextView txtGameName = (TextView) findViewById(R.id.tvGameSearcherGameName);
        TextView txtLogin = (TextView) findViewById(R.id.txtGameSearcherLogin);
        TextView txtHiddenLogin = (TextView) findViewById(R.id.tvGameSearcherPlayerToFind);

        strGameName = getIntent().getStringExtra("GameName");
        nGameID = getIntent().getIntExtra("GameID",-1);
        strLogin = getIntent().getStringExtra("Login");
        nLoginID = getIntent().getIntExtra("LoginID",-1);
        strHiddenLogin = getIntent().getStringExtra("HiddenLogin");
        nHiddenLoginID = getIntent().getIntExtra("HiddenLoginID",-1);

        txtGameName.setText(strGameName);
        txtLogin.setText(strLogin);
        txtHiddenLogin.setText(strHiddenLogin);


        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(DownCount, 0);
        /*
            Kompas
         */
        image = (ImageView) findViewById(R.id.imageGameSearcherViewCompass);
        timerValue = (TextView) findViewById(R.id.tvGameSearcherTimer);

        // TextView that will tell the user what degree is he heading
        tvHeading = (TextView) findViewById(R.id.tvGameSearcherHeading);

        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        ListItems.add(new UserInfo("Adam", 13.5, 1));
        ListItems.add(new UserInfo("Kasia", 18.5, 2));
        ListItems.add(new UserInfo("Michal", 19.5, 3));
        ListItems.add(new UserInfo("Trauta", 28.5, 4));

        strHiddenLogin = ListItems.get(0).strLogin;
        nHiddenLoginID = ListItems.get(0).nUserID;

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_game_searcher);

        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.rlGameSearcherDrawerPane);
        mDrawerList = (ListView) findViewById(R.id.lvGameSearcherList);
        GameSearcherDrawer adapter = new GameSearcherDrawer(this, ListItems);
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
                // Dolacz do gry!!!!
                FragmentManager fm = getFragmentManager();
                GameSearcherCatchedDialog dialogFragment = new GameSearcherCatchedDialog();
                Bundle args = new Bundle();
                args.putString("HiddenLogin", strHiddenLogin);
                args.putInt("HiddenLoginID", nGameID);
                args.putInt("GameID", nGameID);
                args.putString("GameName", strGameName);
                dialogFragment.setArguments(args);
                dialogFragment.show(fm, "Catched");
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
        Intent create = new Intent(GameSearcher.this, GameSearcher.class);
        create.putExtra("GameID", nGameID);
        create.putExtra("GameName", strGameName);
        create.putExtra("Login", strLogin);
        create.putExtra("HiddenLogin", strHiddenLogin);
        create.putExtra("HiddenLoginID", nHiddenLoginID);
        GameSearcher.this.startActivity(create);
        GameSearcher.this.finish();
    }

    private void toast( String text )
    {
        Toast.makeText( GameSearcher.this,
                String.format( "%s", text ), Toast.LENGTH_SHORT )
                .show();
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        GameDialogQuit dialogFragment = new GameDialogQuit();
        Bundle args = new Bundle();
        args.putInt("GameID", nGameID);
        args.putString("GameName", strGameName);
        args.putString("Login", strLogin);
        dialogFragment.setArguments(args);
        dialogFragment.show(fm, "Quit Game");
    }

    /*
        Okno wyswietlane przy koncu czasu
    */
    public void EndOfTime(){
        FragmentManager fm = getFragmentManager();
        GameDialogEndOfTime dialogFragment = new GameDialogEndOfTime();
        Bundle args = new Bundle();
        args.putInt("GameID", nGameID);
        args.putString("GameName", strGameName);
        args.putString("Login", strLogin);
        dialogFragment.setArguments(args);
        dialogFragment.show(fm, "End of Time");
    }

    /*
        Odliczanie zegara do gory
    */
    private Runnable UpCount = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

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

    /*
        Odliczanie zegara w dol
    */
    private Runnable DownCount = new Runnable() {

        public void run() {

            timeInMilliseconds = HideTime - (SystemClock.uptimeMillis() - startTime);

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

        tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");

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

