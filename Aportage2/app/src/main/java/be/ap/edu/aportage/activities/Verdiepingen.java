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
import be.ap.edu.aportage.interfaces.CampusKleuren;
import be.ap.edu.aportage.interfaces.IVolleyCallback;
import be.ap.edu.aportage.managers.MyDatamanger;
import be.ap.edu.aportage.interfaces.MongoCollections;
import be.ap.edu.aportage.recycleradapters.VerdiepenRecyclerAdapter;

public class Verdiepingen extends AppCompatActivity {


    private RecyclerView verdiepenRV;
    private LinearLayoutManager verdiepenLM;
    private VerdiepenRecyclerAdapter verdiepenAdapter;
    private MyDatamanger dataManager;

    private Intent uitgaandeIntent;
    private Intent inkomendeIntent;
    private Button navBtnCampus;

    private CampusKleuren campusKleuren = new CampusKleuren();

    String s_campus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verdiepingen);
        this.inkomendeIntent = getIntent();

        this.dataManager = MyDatamanger.getInstance(this.getApplicationContext());
        this.s_campus = this.inkomendeIntent.getStringExtra(getString(R.string.campus_intent));
        this.navBtnCampus = (Button)findViewById(R.id.btn_nav_campus);
        this.navBtnCampus.setText(s_campus);
        this.navBtnCampus.setBackgroundColor(campusKleuren.getCampusColor(s_campus.toLowerCase(), this));

        this.verdiepenAdapter = new VerdiepenRecyclerAdapter(this, this.dataManager.getVerdiepenLijst(s_campus), s_campus);
        this.verdiepenRV = (RecyclerView) findViewById(R.id.rv_verdiepingen);
        this.verdiepenLM = new LinearLayoutManager(this);
        this.verdiepenRV.setLayoutManager(this.verdiepenLM);
        this.verdiepenRV.setAdapter(this.verdiepenAdapter);

        registreerOnClickListeners();
        requestVerdiepenData();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Campussen.class);
        startActivity(intent);
        Verdiepingen.this.finish();
    }

    private void requestVerdiepenData() {
        this.verdiepenAdapter.clearVerdiepingen();
        JsonArrayRequest req = this.dataManager.createGetRequest(ApiContract.createCollectionUrl(MongoCollections.VERDIEPEN), MongoCollections.VERDIEPEN, new IVolleyCallback() {

            @Override
            public void onCustomSuccess(Object data) {

                verdiepenAdapter.setVerdiepenLijst(dataManager.getVerdiepenLijst(navBtnCampus.getText().toString()));
                Log.d("callback", "verdiep success: " + data.toString());
                verdiepenAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPostSuccess(JSONObject response) {
                //ignore
            }

            @Override
            public void onFailure() {
                //todo: failure bericht tonen
            }
        });
        req.setShouldCache(false);
        this.dataManager.addToRequestQueue(req);

    }

    public void registreerOnClickListeners(){
        this.navBtnCampus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gaNaarCampussenActivity();
            }
        });

    }

    public void gaNaarCampussenActivity(){
        this.uitgaandeIntent = new Intent(this, Campussen.class);
        startActivity(this.uitgaandeIntent);
    }
}
