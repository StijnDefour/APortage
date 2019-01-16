package be.ap.edu.aportage.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
    private ImageView iv_mijnmeldingen_bg;
    private FloatingActionButton fab_profiel;
    private String TAG = Overzicht.class.toString();

    private MyDatamanger dataManager;
    private Intent uitgaandeIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkLoginStatus();

        setContentView(R.layout.activity_overzicht);

        ParseInstallation.getCurrentInstallation().saveInBackground();
        //this.dataManager = MyDatamanger.getInstance(this.getApplicationContext());

        this.iv_scannen_bg = findViewById(R.id.iv_scannen_bg);
        this.iv_zoeken_bg = findViewById(R.id.iv_zoeken_bg);
        this.iv_mijnmeldingen_bg = findViewById(R.id.iv_meldingen_bg);
        this.fab_profiel = findViewById(R.id.fab_profiel);

        setClicks();

    }


    void setClicks(){
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
        this.iv_zoeken_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Overzicht.this, Campussen.class);
                startActivity(intent);
                Overzicht.this.finish();

                gaNaarCampussenActivity();
            }
        });

        this.iv_mijnmeldingen_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gaNaarMijnMeldingenActivity();
            }
        });

        this.fab_profiel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gaNaarProfielActivity();
            }
        });
    }

    private void gaNaarMijnMeldingenActivity() {
        this.uitgaandeIntent = new Intent(Overzicht.this, MijnMeldingenActivity.class);
        this.uitgaandeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(uitgaandeIntent);

    }


    private void gaNaarScanLokaalActivity() {
        this.uitgaandeIntent = new Intent(Overzicht.this, ScanLokaal.class);
        this.uitgaandeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(uitgaandeIntent);
    }
    private void gaNaarCampussenActivity(){
        this.uitgaandeIntent = new Intent(Overzicht.this, Campussen.class);
        this.uitgaandeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(uitgaandeIntent);
    }

    private void gaNaarProfielActivity(){
        this.uitgaandeIntent = new Intent(Overzicht.this, ProfielActivity.class);
        //this.uitgaandeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(uitgaandeIntent);
    }


    private void checkLoginStatus() {

        if(this.aangemeldeMelder == null){
            this.uitgaandeIntent = new Intent(Overzicht.this, LoginActivity.class);
            this.uitgaandeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(this.uitgaandeIntent);
        }

    }





}
