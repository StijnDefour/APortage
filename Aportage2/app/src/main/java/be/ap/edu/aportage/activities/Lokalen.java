package be.ap.edu.aportage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import be.ap.edu.aportage.R;
import be.ap.edu.aportage.recycleradapters.LokalenRecyclerAdapter;
import be.ap.edu.aportage.managers.MockDataManager;

public class Lokalen extends AppCompatActivity {

    private MockDataManager datamanger = MockDataManager.getInstance();
    private LokalenRecyclerAdapter lokalenAdapter;
    private RecyclerView lokalenRV;
    private LinearLayoutManager lokaalLM;
    private int[] lokalenLijst;
    private Intent inkomendeIntent;
    private Intent uitgaandeIntent;
    private Button btnCampus;
    private Button btnVerdiep;

    String s_campus;
    String s_verdieping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokalen);

       this.inkomendeIntent = this.getIntent();
       this.s_campus = this.inkomendeIntent.getStringExtra(getString(R.string.campus_intent));
       this.s_verdieping = this.inkomendeIntent.getStringExtra(getString(R.string.verdieping_intent));
       this.btnCampus = findViewById(R.id.btn_campus);
       this.btnVerdiep = findViewById(R.id.btn_verdiep);




       navigatieOpvullen();

       this.lokalenLijst = datamanger.getLokalenLijst(s_campus, Integer.parseInt(s_verdieping));

       this.lokalenRV = (RecyclerView) findViewById(R.id.rv_lokalen);

       this.lokaalLM = new LinearLayoutManager(this);

       this.lokalenRV.setLayoutManager(this.lokaalLM);

       this.lokalenAdapter = new LokalenRecyclerAdapter(this, this.lokalenLijst, s_campus, s_verdieping);

       this.lokalenRV.setAdapter(lokalenAdapter);

       registreerOnClicks();

    }

    private void navigatieOpvullen(){
        try {
            btnCampus.setText(s_campus);
            btnVerdiep.setText(s_verdieping);
        } catch (Error e){
            Log.e("navigatieOpvullen", e.getMessage());
        }
    }

    private void registreerOnClicks(){

        this.btnCampus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gaNaarCampussen();
            }
        });
        this.btnVerdiep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gaNaarVerdiepen();
            }
        });

    }

    private void gaNaarCampussen(){
        this.uitgaandeIntent = new Intent(this, Campussen.class);
        this.startActivity(this.uitgaandeIntent);
        Lokalen.this.finish();
    }

    private void gaNaarVerdiepen(){
        this.uitgaandeIntent = new Intent(this, Verdiepingen.class);
        this.uitgaandeIntent.putExtra(getString(R.string.verdieping_intent), this.btnVerdiep.getText());
        this.uitgaandeIntent.putExtra(getString(R.string.campus_intent), this.btnCampus.getText());
        this.startActivity(this.uitgaandeIntent);
        Lokalen.this.finish();
    }

    @Override
    public void onBackPressed() {
        this.uitgaandeIntent = new Intent(this, Verdiepingen.class);
        this.uitgaandeIntent.putExtra(getString(R.string.campus_intent), this.s_campus);
        startActivity(this.uitgaandeIntent);
        Lokalen.this.finish();
    }
}
