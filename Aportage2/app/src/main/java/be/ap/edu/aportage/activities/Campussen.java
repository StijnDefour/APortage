package be.ap.edu.aportage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.Console;

import com.android.volley.toolbox.JsonArrayRequest;

import be.ap.edu.aportage.R;
import be.ap.edu.aportage.interfaces.ApiContract;
import be.ap.edu.aportage.managers.MyDatamanger;
import be.ap.edu.aportage.interfaces.MongoCollections;
import be.ap.edu.aportage.recycleradapters.CampussenRecyclerAdapter;
import be.ap.edu.aportage.managers.MockDataManager;

public class Campussen extends AppCompatActivity {

    private RecyclerView mijnCampussenRV;
    private LinearLayoutManager mijnLM;
    private CampussenRecyclerAdapter campussenAdapter;
    private MyDatamanger dataManager;
    private Intent uitgaandeIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campussen);

        //initialisatie van Datamanager die een getrequest doet naar de mLab Api voor de campussen collection
        this.dataManager = MyDatamanger.getInstance(this.getApplicationContext());


        //initialisatie properties voor recyclerview van campussen
        this.campussenAdapter = new CampussenRecyclerAdapter(this, this.dataManager.getCampussenLijst());
        this.mijnCampussenRV = (RecyclerView) findViewById(R.id.rv_campussen);
        this.mijnLM = new LinearLayoutManager(this);
        this.mijnCampussenRV.setLayoutManager(this.mijnLM);
        this.mijnCampussenRV.setAdapter(this.campussenAdapter);
        JsonArrayRequest req = this.dataManager.createGetRequest(ApiContract.createCollectionUrl(MongoCollections.CAMPUSSEN), MongoCollections.CAMPUSSEN, this.campussenAdapter );
        req.setShouldCache(false);
        this.dataManager.addToRequestQueue(req);

    }

    @Override
    public void onBackPressed() {
        this.uitgaandeIntent = new Intent(this, Overzicht.class);
        startActivity(this.uitgaandeIntent);
        Campussen.this.finish();
    }
}
