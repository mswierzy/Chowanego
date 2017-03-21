package com.example.markus_swierzy.chowanego;

import android.app.FragmentManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends AppCompatActivity implements SensorEventListener {

    String strGameName = "";
    int nGameID = -1;

    // define the display assembly compass picture
    private ImageView image;

    // record the compass picture angle turned
    private float currentDegree = 0f;

    // device sensor manager
    private SensorManager mSensorManager;

    TextView tvHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Button catched = (Button) findViewById(R.id.btnGameCatched);
        image = (ImageView) findViewById(R.id.imageViewCompass);


        // TextView that will tell the user what degree is he heading
        tvHeading = (TextView) findViewById(R.id.tvHeading);

        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        TextView txtGameName = (TextView) findViewById(R.id.txtGameName);
        TextView txtGameID = (TextView) findViewById(R.id.txtGameID);
        strGameName = getIntent().getStringExtra("GameName");
        nGameID = getIntent().getIntExtra("GameID",-1);
        txtGameName.setText(strGameName);
        txtGameID.setText(Integer.toString(nGameID));

        catched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dolacz do gry!!!!
                FragmentManager fm = getFragmentManager();
                GameCatchedDialog dialogFragment = new GameCatchedDialog();
                Bundle args = new Bundle();
                args.putInt("GameID", nGameID);
                args.putString("GameName", strGameName);
                dialogFragment.setArguments(args);
                dialogFragment.show(fm, "Sample Fragment");

                //Intent intent = new Intent(getBaseContext(), ConnectDialog.class);
                //intent.putExtra("GameName", strGameName);
                //intent.putExtra("SelectedID", nSelected);
            }
        });
    }

    private void toast( String text )
    {
        Toast.makeText( Game.this,
                String.format( "%s", text ), Toast.LENGTH_SHORT )
                .show();
    }

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
