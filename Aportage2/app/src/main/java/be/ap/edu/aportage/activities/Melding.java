package be.ap.edu.aportage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import be.ap.edu.aportage.R;
import be.ap.edu.aportage.helpers.ApiContract;
import be.ap.edu.aportage.helpers.MongoCollections;
import be.ap.edu.aportage.interfaces.IVolleyCallback;
import be.ap.edu.aportage.managers.MyDatamanger;

public class Melding extends Activity {

    Intent binnenkomendeIntent;
    Intent uitgaandeIntent;
    Button btn_campus_afk;
    Button btn_verdiep_nr;
    Button btn_melding_lokaalnr;
    FloatingActionButton nieuweMeldingFab;
    ImageView iv_melding_foto;
    TextView tv_melding_titel;
    TextView tv_melding_beschrijving;
    TextView tv_melding_melder;
    TextView tv_melding_tijdstip;

    private String s_campus;
    private String s_verdieping;
    private String s_lokaal;
    private String s_melding_id;
    private MyDatamanger datamanager;
    private be.ap.edu.aportage.models.Melding melding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_melding);

        this.binnenkomendeIntent = getIntent();
        this.s_melding_id = this.binnenkomendeIntent.getStringExtra("melding_id");

        this.btn_campus_afk = findViewById(R.id.btn_campus_afk);
        this.btn_verdiep_nr = findViewById(R.id.btn_verdiep_nr);
        this.btn_melding_lokaalnr = findViewById(R.id.btn_melding_lokaalnr);
        this.nieuweMeldingFab = findViewById(R.id.melding_fab);
        this.iv_melding_foto = findViewById(R.id.iv_melding_foto);
        this.tv_melding_titel = findViewById(R.id.tv_melding_titel);
        this.tv_melding_beschrijving = findViewById(R.id.tv_melding_omschrijving);
        this.tv_melding_melder = findViewById(R.id.tv_melding_melder);
        this.tv_melding_tijdstip = findViewById(R.id.tv_melding_tijdstip);

        this.datamanager = MyDatamanger.getInstance(getApplication());
        getMeldingDetails();

        navigatieButtonsOpvullen();
        registreerButtonOnClicks();
    }

    private void getMeldingDetails() {

        JsonObjectRequest req = this.datamanager.getMeldingMetId(this.s_melding_id, new IVolleyCallback() {
            @Override
            public void onCustomSuccess(Object data) {
                melding = datamanager.parseMeldingJson((JSONObject)data);
                vulMeldingDetailsIn(melding);
            }

            @Override
            public void onPostSuccess(JSONObject response) {

                //ignore
            }
        });

        this.datamanager.addToRequestQueue(req);




    }

    private void vulMeldingDetailsIn(be.ap.edu.aportage.models.Melding m) {
        this.tv_melding_titel.setText(m.titel);

    }

    private void registreerButtonOnClicks() {
        this.btn_melding_lokaalnr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gaNaarLokalen();
            }
        });
        this.btn_verdiep_nr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gaNaarVerdiepen();
            }
        });
        this.btn_campus_afk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gaNaarCampussen();
            }
        });
        this.nieuweMeldingFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gaNaarScanMelding();
            }
        });
    }

    private void navigatieButtonsOpvullen(){

        try {
            this.s_campus = this.binnenkomendeIntent.getStringExtra("campus_afk");
            this.s_verdieping = this.binnenkomendeIntent.getStringExtra("verdiep_nr");
            this.s_lokaal = this.binnenkomendeIntent.getStringExtra("lokaal_nr");
            this.btn_campus_afk.setText(this.s_campus);
            this.btn_verdiep_nr.setText(this.s_verdieping);
            this.btn_melding_lokaalnr.setText(this.s_lokaal);
        } catch (Error e) {
            Log.e("navigatieButtonsOpvullen Mislukt", e.getMessage());
        }
    }

    private void gaNaarScanMelding(){
        Intent intent = new Intent(this, ScanMelding.class);
        intent.putExtra("campus_afk", s_campus );
        intent.putExtra("verdiep_nr", s_verdieping);
        intent.putExtra("lokaal_nr", s_lokaal);
        startActivity(intent);
    }

    private void gaNaarLokalen() {

        this.uitgaandeIntent = new Intent(this, Lokalen.class);
        this.uitgaandeIntent.putExtra("verdiep_nr", this.btn_verdiep_nr.getText());
        this.uitgaandeIntent.putExtra("campus_afk", this.btn_campus_afk.getText());
        startActivity(this.uitgaandeIntent);

    }

    private void gaNaarVerdiepen(){

        this.uitgaandeIntent = new Intent(this, Verdiepingen.class);
        this.uitgaandeIntent.putExtra("campus_afk", this.btn_campus_afk.getText());
        startActivity(this.uitgaandeIntent);
    }

    private void gaNaarCampussen(){

        this.uitgaandeIntent = new Intent(this, Campussen.class);
        startActivity(this.uitgaandeIntent);
    }

    private void gaNaarMelding(){
        Intent intent = new Intent(this, Melding.class);
        intent.putExtra("campus_afk", s_campus );
        intent.putExtra("verdiep_nr", s_verdieping);
        intent.putExtra("lokaal_nr", s_lokaal);

        startActivity(intent);
    }

}
