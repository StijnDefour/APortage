package be.ap.edu.aportage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import be.ap.edu.aportage.models.Campus;
import be.ap.edu.aportage.models.CampussenRecyclerAdapter;
import be.ap.edu.aportage.models.MockDataManager;

public class Campussen extends AppCompatActivity {

    private RecyclerView mijnCampussenRV;
    private LinearLayoutManager mijnLM;
    private CampussenRecyclerAdapter campussenAdapter;
    private MockDataManager dataManager = MockDataManager.getInstance();
    private Intent uitgaandeIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campussen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialisatie properties voor recyclerview van campussen
        this.campussenAdapter = new CampussenRecyclerAdapter(this, this.dataManager.getCampussenLijst());
        this.mijnCampussenRV = (RecyclerView) findViewById(R.id.rv_campussen);
        this.mijnLM = new LinearLayoutManager(this);
        this.mijnCampussenRV.setLayoutManager(this.mijnLM);
        this.mijnCampussenRV.setAdapter(this.campussenAdapter);
    }

}
