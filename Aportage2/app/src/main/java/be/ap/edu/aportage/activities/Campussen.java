package be.ap.edu.aportage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONObject;

import be.ap.edu.aportage.R;
import be.ap.edu.aportage.Helpers.ApiContract;
import be.ap.edu.aportage.interfaces.IVolleyCallback;
import be.ap.edu.aportage.managers.MyDatamanger;
import be.ap.edu.aportage.Helpers.MongoCollections;
import be.ap.edu.aportage.recycleradapters.CampussenRecyclerAdapter;

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
        JsonArrayRequest req = this.dataManager.createGetRequest(ApiContract.createCollectionUrl(MongoCollections.CAMPUSSEN), MongoCollections.CAMPUSSEN, new IVolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                //todo_done: implementatie
                campussenAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCustomSuccess(Object data) {

                campussenAdapter.setCampussenList(dataManager.getCampussenLijst());
                campussenAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPostSuccess(JSONObject response) {
                //ignore
            }
        });
        req.setShouldCache(false);
        this.dataManager.addToRequestQueue(req);

    }






}
