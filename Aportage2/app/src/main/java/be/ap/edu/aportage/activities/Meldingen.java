package be.ap.edu.aportage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import be.ap.edu.aportage.R;
import be.ap.edu.aportage.models.Melding;
import be.ap.edu.aportage.recycleradapters.MeldingenRecyclerAdapter;
import be.ap.edu.aportage.managers.MockDataManager;

public class Meldingen extends AppCompatActivity {

    private Button meldingenCampusBtn;
    private Button meldingenVerdiepBtn;
    private Button meldingenLokaalBtn;
    private RecyclerView meldingenRV;
    private CardView meldingCV;
    private LinearLayoutManager meldingenLM;
    private MeldingenRecyclerAdapter meldingenAdapter;
    private List<Melding> meldingenLijst;
    private Intent binnenkomendeIntent;
    private MockDataManager dataManager = MockDataManager.getInstance();
    private Intent uitgaandeIntent;
    private FloatingActionButton nieuweMeldingfab;

    private String s_campus;
    private String s_verdieping;
    private String s_lokaal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meldingen);

        this.meldingenCampusBtn = (Button) findViewById(R.id.btn_campus_afk);
        this.meldingenVerdiepBtn = (Button) findViewById(R.id.btn_verdiep_nr);
        this.meldingenLokaalBtn = (Button) findViewById(R.id.btn_melding_lokaalnr);
        this.meldingenRV = (RecyclerView) findViewById(R.id.rv_meldingen);
        this.meldingCV = (CardView) findViewById(R.id.cv_melding);
        this.nieuweMeldingfab = (FloatingActionButton) findViewById(R.id.meldingen_fab);
        this.meldingenLijst = dataManager.getMeldingenLijst();
        this.binnenkomendeIntent = getIntent();

        this.meldingenLM = new LinearLayoutManager(this);
        this.meldingenRV.setLayoutManager(this.meldingenLM);

        this.meldingenAdapter = new MeldingenRecyclerAdapter(this, this.meldingenLijst);
        this.meldingenRV.setAdapter(this.meldingenAdapter);


        navigatieButtonsOpvullen();
        registreerButtonOnClicks();
    }


    private void navigatieButtonsOpvullen(){

        try {
            this.s_campus = this.binnenkomendeIntent.getStringExtra(getString(R.string.campus_intent));
            this.s_verdieping = this.binnenkomendeIntent.getStringExtra(getString(R.string.verdieping_intent));
            this.s_lokaal = this.binnenkomendeIntent.getStringExtra(getString(R.string.lokaal_intent));
            this.meldingenCampusBtn.setText(this.s_campus);
            this.meldingenVerdiepBtn.setText(this.s_verdieping);
            this.meldingenLokaalBtn.setText(this.s_lokaal);
        } catch (Error e) {
            Log.e("navigatieButtonsOpvullen Mislukt", e.getMessage());
        }
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
        this.nieuweMeldingfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gaNaarScanMelding();
            }
        });
    }

    private void gaNaarScanMelding(){
        this.uitgaandeIntent = new Intent(this, ScanMelding.class);
        this.uitgaandeIntent.putExtra(getString(R.string.campus_intent), s_campus);
        this.uitgaandeIntent.putExtra(getString(R.string.verdieping_intent), s_verdieping);
        this.uitgaandeIntent.putExtra(getString(R.string.lokaal_intent), s_lokaal);
        this.uitgaandeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(this.uitgaandeIntent);
        Meldingen.this.finish();
    }

    private void gaNaarLokalen() {

        this.uitgaandeIntent = new Intent(this, Lokalen.class);
        this.uitgaandeIntent.putExtra(getString(R.string.verdieping_intent), this.meldingenVerdiepBtn.getText());
        this.uitgaandeIntent.putExtra(getString(R.string.campus_intent), this.meldingenCampusBtn.getText());
        startActivity(this.uitgaandeIntent);
        Meldingen.this.finish();
    }

    private void gaNaarVerdiepen(){
        this.uitgaandeIntent = new Intent(this, Verdiepingen.class);
        this.uitgaandeIntent.putExtra(getString(R.string.campus_intent), this.meldingenCampusBtn.getText());
        startActivity(this.uitgaandeIntent);
        Meldingen.this.finish();
    }

    private void gaNaarCampussen(){
        this.uitgaandeIntent = new Intent(this, Campussen.class);
        startActivity(this.uitgaandeIntent);
        Meldingen.this.finish();
    }

    @Override
    public void onBackPressed() {
        this.uitgaandeIntent = new Intent(this, Lokalen.class);
        this.uitgaandeIntent.putExtra(getString(R.string.campus_intent), this.s_campus);
        this.uitgaandeIntent.putExtra(getString(R.string.lokaal_intent), this.s_lokaal);
        startActivity(this.uitgaandeIntent);
        Meldingen.this.finish();
    }
}
