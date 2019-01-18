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
import android.widget.TextView;

import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONObject;

import java.util.List;

import be.ap.edu.aportage.R;
import be.ap.edu.aportage.helpers.MongoCollections;
import be.ap.edu.aportage.interfaces.CampusKleuren;
import be.ap.edu.aportage.interfaces.IVolleyCallback;
import be.ap.edu.aportage.managers.MyDatamanger;
import be.ap.edu.aportage.models.Melding;
import be.ap.edu.aportage.recycleradapters.MeldingenRecyclerAdapter;

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
    private MyDatamanger dataManager ;
    private Intent uitgaandeIntent;
    private FloatingActionButton nieuweMeldingfab;
    private TextView placeholder;

    private CampusKleuren campusKleuren = new CampusKleuren();

    private String s_campus;
    private String s_verdieping;
    private String s_lokaal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meldingen);
        this.dataManager = MyDatamanger.getInstance(this.getApplicationContext());

        this.meldingenCampusBtn = (Button) findViewById(R.id.btn_campus_afk);
        this.meldingenVerdiepBtn = (Button) findViewById(R.id.btn_verdiep_nr);
        this.meldingenLokaalBtn = (Button) findViewById(R.id.btn_melding_lokaalnr);
        this.meldingenRV = (RecyclerView) findViewById(R.id.rv_meldingen);
        this.meldingCV = (CardView) findViewById(R.id.cv_melding);
        this.nieuweMeldingfab = (FloatingActionButton) findViewById(R.id.meldingen_fab);
        this.meldingenLijst = dataManager.getMeldingenLijst();
        this.binnenkomendeIntent = getIntent();
        this.placeholder = findViewById(R.id.tv_placeholder);

        this.meldingenLM = new LinearLayoutManager(this);
        this.meldingenRV.setLayoutManager(this.meldingenLM);

        this.meldingenAdapter = new MeldingenRecyclerAdapter(this, this.meldingenLijst);
        this.meldingenRV.setAdapter(this.meldingenAdapter);

        navigatieButtonsOpvullen();

        registreerButtonOnClicks();
        getMeldingenData();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Lokalen.class);
        intent.putExtra(getString(R.string.campus_intent), s_campus);
        intent.putExtra(getString(R.string.verdieping_intent), s_verdieping);
        startActivity(intent);
        Meldingen.this.finish();
    }

    private void getMeldingenData() {
        this.meldingenAdapter.clearMeldingen();
        String url = be.ap.edu.aportage.helpers.ApiContract.createMeldingenQueryUrl(s_campus, s_verdieping, s_lokaal);
        JsonArrayRequest req = dataManager.createGetRequest(url, MongoCollections.MELDINGEN, new IVolleyCallback() {

            @Override
            public void onCustomSuccess(Object data) {
//                Log.d("getMeldingenLijst", data.toString());
                meldingenAdapter.setMeldingenList(dataManager.getMeldingenLijst());
                meldingenAdapter.notifyDataSetChanged();
                placeholder.setText("");

            }

            @Override
            public void onPostSuccess(JSONObject response) {

            }

            @Override
            public void onFailure() {
                //todo: bericht/popup dat het tonen van de meldingen is mislukt
            }
        });
        dataManager.addToRequestQueue(req);

    }


    private void navigatieButtonsOpvullen(){
        try {
            this.s_campus = this.binnenkomendeIntent.getStringExtra(getString(R.string.campus_intent));
            this.s_verdieping = this.binnenkomendeIntent.getStringExtra(getString(R.string.verdieping_intent));
            this.s_lokaal = this.binnenkomendeIntent.getStringExtra(getString(R.string.lokaal_intent));
            this.meldingenCampusBtn.setText(this.s_campus);
            this.meldingenVerdiepBtn.setText(this.s_verdieping);
            this.meldingenLokaalBtn.setText(this.s_lokaal);
            this.meldingenCampusBtn.setBackgroundColor(campusKleuren.getCampusColor(s_campus.toLowerCase(), this));
            this.meldingenVerdiepBtn.setBackgroundColor(campusKleuren.getVerdiepingColor(s_campus.toLowerCase(), this));
            this.meldingenLokaalBtn.setBackgroundColor(campusKleuren.getLokaalColor(s_campus.toLowerCase(), this));
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

}
