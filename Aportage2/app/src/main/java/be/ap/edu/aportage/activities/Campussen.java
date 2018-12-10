package be.ap.edu.aportage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.toolbox.JsonArrayRequest;

import be.ap.edu.aportage.R;
import be.ap.edu.aportage.managers.ApiContract;
import be.ap.edu.aportage.managers.MyDatamanger;
import be.ap.edu.aportage.recycleradapters.CampussenRecyclerAdapter;
import be.ap.edu.aportage.managers.MockDataManager;

public class Campussen extends AppCompatActivity {

    private RecyclerView mijnCampussenRV;
    private LinearLayoutManager mijnLM;
    private CampussenRecyclerAdapter campussenAdapter;
    private MockDataManager dataManager = MockDataManager.getInstance();
    private Intent uitgaandeIntent;
    private MyDatamanger manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campussen);

        this.manager = MyDatamanger.getInstance(this.getApplicationContext());
        JsonArrayRequest req = this.manager.createRequest(ApiContract.createCollectionUrl(ApiContract.MELDINGEN_COLL));
        this.manager.addToRequestQueue(req);

        //initialisatie properties voor recyclerview van campussen
        this.campussenAdapter = new CampussenRecyclerAdapter(this, this.dataManager.getCampussenLijst());
        this.mijnCampussenRV = (RecyclerView) findViewById(R.id.rv_campussen);
        this.mijnLM = new LinearLayoutManager(this);
        this.mijnCampussenRV.setLayoutManager(this.mijnLM);
        this.mijnCampussenRV.setAdapter(this.campussenAdapter);
    }

}
