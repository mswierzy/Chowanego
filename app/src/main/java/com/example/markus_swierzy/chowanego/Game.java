package com.example.markus_swierzy.chowanego;


import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.SENSOR_SERVICE;

public class Game extends Activity implements SensorEventListener, GameCatchedDialog.OnCompleteListener, GameCatchedDialog.OnDismissListener, GameDialogQuit.OnCancelListener, GameDialogQuit.OnExitListener, GameDialogEndOfTime.OnOkListener{

    private boolean bEndOfTime = false;
    private String strGameName = "";
    private String strLogin = "";
    private int nGameID = -1;
    private int nLoginID = -1;

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
        setContentView(R.layout.activity_game);

        Button btnExit = (Button) findViewById(R.id.btnGameQuit);
        Button catched = (Button) findViewById(R.id.btnGameCatched);
        TextView txtGameName = (TextView) findViewById(R.id.tvGameName);
        TextView txtLogin = (TextView) findViewById(R.id.txtGameLogin);
        timerValue = (TextView) findViewById(R.id.tvGameTimer);

        strGameName = getIntent().getStringExtra("GameName");
        nGameID = getIntent().getIntExtra("GameID",-1);
        strLogin = getIntent().getStringExtra("Login");
        nLoginID = getIntent().getIntExtra("LoginID",-1);

        txtGameName.setText(strGameName);
        txtLogin.setText(strLogin);
//TODO: Pobierz czas zakończenia szukania z bazy danych i wylicz na jego podstawie ilość milisekund do jego końca i potem zapisz do zmiennej SearchTime
        SearchTime = 20000L;

        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(DownCount, 0);
        customHandler.postDelayed(StartNewActivity, SearchTime);
        /* //TODO: KOMPAS
            Kompas
         */
        image = (ImageView) findViewById(R.id.imageViewCompass);


        // TextView that will tell the user what degree is he heading
        tvHeading = (TextView) findViewById(R.id.tvHeading);

        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

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
                FragmentManager fm = getFragmentManager();
                GameDialogQuit dialogFragment = new GameDialogQuit();
                dialogFragment.show(fm, "Quit Game");
            }
        });
    }

    public void onComplete() {
        customHandler.removeCallbacks(DownCount);
        customHandler.removeCallbacks(StartNewActivity);
        OpenWaitingActivity();
    }

    public void onDismiss() {

    }

    public void onExit() {
//TODO: Wylogowanie użytkownika z bazy danych graczy
        customHandler.removeCallbacks(DownCount);
        customHandler.removeCallbacks(StartNewActivity);
        Intent create = new Intent(Game.this, CHMainMenu.class);
        Game.this.startActivity(create);
        Game.this.finish();
        overridePendingTransition(R.layout.fadein, R.layout.fadeout);
    }

    public void onCancel() {

    }

    public void onOk(){
        customHandler.removeCallbacks(DownCount);
        Intent create = new Intent(Game.this, RecreateGame.class);
        create.putExtra("GameID", nGameID);
        create.putExtra("GameName", strGameName);
        create.putExtra("Login", strLogin);
        create.putExtra("LoginID", nLoginID);
        Game.this.startActivity(create);
        Game.this.finish();
        overridePendingTransition(R.layout.fadein, R.layout.fadeout);
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        GameDialogQuit dialogFragment = new GameDialogQuit();
        dialogFragment.show(fm, "Quit Game");
    }

    private void OpenWaitingActivity(){
        Intent create = new Intent(Game.this, Waiting.class);
        create.putExtra("GameID", nGameID);
        create.putExtra("GameName", strGameName);
        create.putExtra("Login", strLogin);
        create.putExtra("LoginID", nLoginID);
        Game.this.startActivity(create);
        Game.this.finish();
        overridePendingTransition(R.layout.fadein, R.layout.fadeout);
    }

    private void OpenRecreateGameActivity(){
        Intent create = new Intent(Game.this, RecreateGame.class);
        Game.this.startActivity(create);
        Game.this.finish();
        overridePendingTransition(R.layout.fadein, R.layout.fadeout);
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
        args.putInt("LoginID", nLoginID);
        dialogFragment.setArguments(args);
        dialogFragment.show(fm, "End of Time");
    }

    /*
        Odliczanie zegara do gory
    */
    private Runnable DownCount = new Runnable() {

        public void run() {
        if(bEndOfTime == false)  {

            timeInMilliseconds = SearchTime - (SystemClock.uptimeMillis() - startTime);

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            if (timeInMilliseconds < 0) {
                bEndOfTime = true;
                /*
                Koniec czasu gry
                 */
                customHandler.removeCallbacks(this);

            } else {
                customHandler.postDelayed(this, 100);
            }
        }
        }

    };

    private Runnable StartNewActivity = new Runnable() {

        public void run() {
            //EndOfTime();
            OpenRecreateGameActivity();
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
