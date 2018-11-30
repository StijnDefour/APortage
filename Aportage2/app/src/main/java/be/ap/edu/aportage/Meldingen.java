package be.ap.edu.aportage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.ap.edu.aportage.models.Melding;
import be.ap.edu.aportage.models.MeldingenRecyclerAdapter;
import be.ap.edu.aportage.models.MockDataManager;

public class Meldingen extends AppCompatActivity {

    private Button meldingenCampusBtn;
    private Button meldingenVerdiepBtn;
    private Button meldingenLokaalBtn;
    private RecyclerView meldingenRV;
    private CardView meldingCV;
    private LinearLayoutManager meldingenLM;
    private MeldingenRecyclerAdapter meldingenAdapter;
    private List<Melding> meldingenLijst;
    private Intent incomingIntent;
    private MockDataManager dataManager = MockDataManager.getInstance();
    private Intent uitgaandeIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meldingen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.meldingenCampusBtn = (Button) findViewById(R.id.btn_campus_afk);
        this.meldingenVerdiepBtn = (Button) findViewById(R.id.btn_verdiep_nr);
        this.meldingenLokaalBtn = (Button) findViewById(R.id.btn_melding_lokaalnr);
        this.meldingenRV = (RecyclerView) findViewById(R.id.rv_meldingen);
        this.meldingCV = (CardView) findViewById(R.id.cv_melding);
        this.meldingenLijst = dataManager.getMeldingenLijst();
        this.incomingIntent = getIntent();

        this.meldingenLM = new LinearLayoutManager(this);
        this.meldingenRV.setLayoutManager(this.meldingenLM);

        this.meldingenAdapter = new MeldingenRecyclerAdapter(this, this.meldingenLijst);
        this.meldingenRV.setAdapter(this.meldingenAdapter);



        Bundle b = this.incomingIntent.getExtras();
        checkBundleForData(b);


    }

    private void checkBundleForData(Bundle b) {

        if(b!=null)
        {
            String j = (String) b.get("lokaal_id");
            lokaalButtonsOpvullen();
            Log.v("Meldingen", j.toString());
        }

    }

    private void lokaalButtonsOpvullen() {

        //todo: buttons aanvullen met data dat uit intent wordt gehaald, momenteel mock data

        this.meldingenCampusBtn.setText("LOL");
        this.meldingenVerdiepBtn.setText("V1");
        this.meldingenLokaalBtn.setText("001");
    }

    private void registreerButtonOnClicks(){
        this.meldingenLokaalBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gaNaarLokalen();
            }
        });
        this.meldingenVerdiepBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gaNaarVerdiepen();
            }
        });
        this.meldingenCampusBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gaNaarCampussen();
            }
        });
    }

    private void gaNaarLokalen() {

        this.uitgaandeIntent = new Intent(this, Lokalen.class);
        this.uitgaandeIntent.putExtra("verdiep", this.meldingenVerdiepBtn.getText());
        this.uitgaandeIntent.putExtra("campus", this.meldingenCampusBtn.getText());
        startActivity(this.uitgaandeIntent);

    }

    private void gaNaarVerdiepen(){

        this.uitgaandeIntent = new Intent(this, Verdiepingen.class);
        this.uitgaandeIntent.putExtra("campus", this.meldingenCampusBtn.getText());
        startActivity(this.uitgaandeIntent);
    }

    private void gaNaarCampussen(){

        this.uitgaandeIntent = new Intent(this, Campussen.class);
        startActivity(this.uitgaandeIntent);
    }



}
