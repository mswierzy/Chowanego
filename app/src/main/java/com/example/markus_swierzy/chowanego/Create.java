package com.example.markus_swierzy.chowanego;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Create extends AppCompatActivity {

    private String strGameName = "";
    private String strLogin = "";
    private String strPassword = "";
    private boolean bIsPassword = false;
    private int nSearchTime = 30;
    private int nHideTime = 10;

    public String strOccupiedGameName = "";
    public String strOccupiedLogin = "";

    Button btnCreate;
    Button btnGameName;
    Button btnPassword;
    Button btnLogin;

    EditText edGameName;
    EditText edPassword;
    EditText edLogin;
    EditText edHideTime;
    EditText edSearchTime;

    Resources res;

    double nLatitude = CHMainMenu.latitude;
    double nLongitude = CHMainMenu.longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        res = getResources();

        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnGameName = (Button) findViewById(R.id.btnCreateGameName);
        btnPassword = (Button) findViewById(R.id.btnCreatePassword);
        btnLogin = (Button) findViewById(R.id.btnCreateLogin);

        edGameName = (EditText) findViewById(R.id.edCreateGameName);
        edPassword = (EditText) findViewById(R.id.edCreatePassword);
        edLogin = (EditText) findViewById(R.id.edCreateLogin);
        edHideTime = (EditText) findViewById((R.id.edCreateHideTime));
        edSearchTime = (EditText) findViewById((R.id.edCreateSearchTime));

        //edHideTime.setText(Integer.toString(nHideTime));
        //edSearchTime.setText(Integer.toString(nSearchTime));

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmpHide = edHideTime.getText().toString();
                String tmpSearch = edSearchTime.getText().toString();

                if(!tmpHide.isEmpty()){
                    nHideTime = Integer.parseInt(tmpHide);
                }
                if(!tmpSearch.isEmpty()){
                    nSearchTime = Integer.parseInt(tmpSearch);
                }

                // Dolacz do gry!!!!
                if(strGameName.isEmpty()){
                    toast(res.getString(R.string.txtNoGameNameTyped));
                }else if(strLogin.isEmpty()){
                    toast(res.getString(R.string.txtNoLoginTyped));
                }else{
                    FragmentManager fm = getFragmentManager();
                    CreateDialog dialogFragment = new CreateDialog ();

                    Bundle args = new Bundle();
                    args.putString("GameName", strGameName);
                    args.putString("Login", strLogin);
                    args.putString("Password", strPassword);
                    args.putInt("SearchTime", nSearchTime);
                    args.putInt("HideTime", nHideTime);
                 //   args.putBoolean("IsPassword", bIsPassword);
                    args.putDouble("Latitude", nLatitude);
                    args.putDouble("Longitude", nLongitude);
                    dialogFragment.setArguments(args);

                    dialogFragment.show(fm, "Create");
                }
            }
        });

        btnGameName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmpGameName = edGameName.getText().toString();

                if(tmpGameName.isEmpty()){
                    toast(res.getString(R.string.txtNoGameNameTyped));
                    strGameName = "";
                }else if(tmpGameName.equals(strOccupiedGameName)){
                    toast(res.getString(R.string.txtNoGameNameExists));
                    strGameName = "";
                }else {
                    strGameName = tmpGameName;
                    toast(res.getString(R.string.txtGameName) + ": " + strGameName);
                }

            }
        });

        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmpPassword = edPassword.getText().toString();

                if(tmpPassword.isEmpty()){
                    toast(res.getString(R.string.txtNoPasswordTyped));
                    strPassword = "";
                    bIsPassword = false;
                }else {
                    strPassword = tmpPassword;
                    toast(res.getString(R.string.txtPassword) + ": " + strPassword);
                    bIsPassword = true;
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmpLogin = edLogin.getText().toString();

                if(tmpLogin.isEmpty()){
                    toast(res.getString(R.string.txtNoLoginTyped));
                    strLogin = "";
                }else if(tmpLogin.equals(strOccupiedLogin)){
                    toast(res.getString(R.string.txtLoginOccupied));
                    strLogin = "";
                }else {
                    strLogin = tmpLogin;
                    toast(res.getString(R.string.txtLogin) + ": " + strLogin);
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent main = new Intent(Create.this, CHMainMenu.class);
        Create.this.startActivity(main);
        Create.this.finish();
        overridePendingTransition(R.layout.fadein, R.layout.fadeout);
    }

    private void toast( String text )
    {
        Toast.makeText( Create.this,
                String.format( "%s", text ), Toast.LENGTH_SHORT )
                .show();
    }
}