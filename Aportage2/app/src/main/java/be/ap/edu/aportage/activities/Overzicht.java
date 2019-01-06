package be.ap.edu.aportage.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import be.ap.edu.aportage.R;

import be.ap.edu.aportage.managers.MyDatamanger;


public class Overzicht extends Activity {

    private ParseUser aangemeldeMelder = ParseUser.getCurrentUser();

    private ImageView iv_scannen_bg;
    private ImageView iv_zoeken_bg;
    private String TAG = Overzicht.class.toString();

    private MyDatamanger dataManager;
    private Intent uitgaandeIntent;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkLoginStatus();

        setContentView(R.layout.activity_overzicht);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("GCMSenderId", 373238590859L);
        installation.saveInBackground();
        //this.dataManager = MyDatamanger.getInstance(this.getApplicationContext());

        iv_scannen_bg = findViewById(R.id.iv_scannen_bg);
        iv_zoeken_bg = findViewById(R.id.iv_zoeken_bg);


        createIntents();
    }

    @Override
    public void onBackPressed() {
        Overzicht.this.finish();
    }

    public void createIntents() {
        iv_scannen_bg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Overzicht.this, ScanLokaal.class);
                startActivity(intent);
                Overzicht.this.finish();
            }
        });



        iv_scannen_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gaNaarScanLokaalActivity();

            }
        });
        iv_zoeken_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Overzicht.this, Campussen.class);
                startActivity(intent);
                Overzicht.this.finish();

                gaNaarCampussenActivity();

            }
        });
    }


    private void gaNaarScanLokaalActivity() {
        this.uitgaandeIntent = new Intent(Overzicht.this, ScanLokaal.class);
        this.uitgaandeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(uitgaandeIntent);
    }

    private void checkLoginStatus() {

        if(this.aangemeldeMelder == null){

            this.uitgaandeIntent = new Intent(Overzicht.this, LoginActivity.class);
            this.uitgaandeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(this.uitgaandeIntent);
        }

    }

    private void gaNaarCampussenActivity(){
        this.uitgaandeIntent = new Intent(Overzicht.this, Campussen.class);
        this.uitgaandeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(uitgaandeIntent);
    }



}
