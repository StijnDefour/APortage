package be.ap.edu.aportage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONObject;

import be.ap.edu.aportage.R;
import be.ap.edu.aportage.interfaces.ApiContract;
import be.ap.edu.aportage.interfaces.IVolleyCallback;
import be.ap.edu.aportage.interfaces.MongoCollections;
import be.ap.edu.aportage.managers.MockDataManager;
import be.ap.edu.aportage.managers.MyDatamanger;
import be.ap.edu.aportage.recycleradapters.LokalenRecyclerAdapter;

public class Lokalen extends AppCompatActivity {

    private MyDatamanger datamanger;
    private LokalenRecyclerAdapter lokalenAdapter;
    private RecyclerView lokalenRV;
    private LinearLayoutManager lokaalLM;
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

        this.datamanger = MyDatamanger.getInstance(this.getApplicationContext());
        this.inkomendeIntent = this.getIntent();

        this.s_campus = this.inkomendeIntent.getStringExtra(getString(R.string.campus_intent));
        this.s_verdieping = this.inkomendeIntent.getStringExtra(getString(R.string.verdieping_intent));

        this.btnCampus = findViewById(R.id.btn_campus);

        this.btnVerdiep = findViewById(R.id.btn_verdiep);

        navigatieOpvullen();

        this.lokalenRV = (RecyclerView) findViewById(R.id.rv_lokalen);
        this.lokaalLM = new LinearLayoutManager(this);
        this.lokalenRV.setLayoutManager(this.lokaalLM);
        this.lokalenAdapter = new LokalenRecyclerAdapter(this, this.datamanger.getLokalenLijst(s_campus, Integer.parseInt(s_verdieping)), s_campus, s_verdieping);
        this.lokalenRV.setAdapter(lokalenAdapter);

        registreerOnClicks();
        requestLokalenData();
    }

    private void requestLokalenData() {
        JsonArrayRequest req = this.datamanger.createGetRequest(ApiContract.createCollectionUrl(MongoCollections.LOKALEN), MongoCollections.LOKALEN, new IVolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                //todo_done: implementatie
                lokalenAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCustomSuccess(Object data) {

                lokalenAdapter.setLokalenList(datamanger.getLokalenLijst(s_campus, Integer.parseInt(s_verdieping)));
                lokalenAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPostSuccess(JSONObject response) {
                //ignore
            }
        });
        req.setShouldCache(false);
        datamanger.addToRequestQueue(req);
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
