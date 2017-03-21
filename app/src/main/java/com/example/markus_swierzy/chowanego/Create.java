package com.example.markus_swierzy.chowanego;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Create extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button create = (Button) findViewById(R.id.btnCreate);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dolacz do gry!!!!
                Intent create = new Intent(Create.this, GameSearcher.class);
                Create.this.startActivity(create);
                Create.this.finish();
                overridePendingTransition(R.layout.fadein, R.layout.fadeout);

                //Intent intent = new Intent(getBaseContext(), ConnectDialog.class);
                //intent.putExtra("GameName", strGameName);
                //intent.putExtra("SelectedID", nSelected);
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
}
