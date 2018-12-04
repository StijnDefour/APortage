package be.ap.edu.aportage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.List;

import be.ap.edu.aportage.models.Campus;
import be.ap.edu.aportage.models.MockDataManager;
import be.ap.edu.aportage.models.VerdiepenRecyclerAdapter;
import be.ap.edu.aportage.models.Verdiep;

public class Verdiepingen extends AppCompatActivity {


    private RecyclerView verdiepenRV;
    private LinearLayoutManager verdiepenLM;
    private VerdiepenRecyclerAdapter verdiepenAdapter;
    private MockDataManager dataManager = MockDataManager.getInstance();
    private Intent uitgaandeIntent;
    private Intent inkomendeIntent;
    private Button navBtnCampus;
    private List<Verdiep> verdiepenLijst;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verdiepingen);

        this.inkomendeIntent = getIntent();
        String campus_naam = this.inkomendeIntent.getStringExtra("campus_titel");
        String campus_afk = this.inkomendeIntent.getStringExtra("campus_afk");

        this.navBtnCampus = (Button)findViewById(R.id.btn_nav_campus);
        this.navBtnCampus.setText(campus_afk);
        this.verdiepenRV = (RecyclerView) findViewById(R.id.rv_verdiepingen);
        this.verdiepenLM = new LinearLayoutManager(this);

        this.verdiepenAdapter = new VerdiepenRecyclerAdapter(this, this.dataManager.getVerdiepenLijst(campus_afk), campus_afk);
        this.verdiepenRV.setLayoutManager(this.verdiepenLM);
        this.verdiepenRV.setAdapter(this.verdiepenAdapter);


        registreerOnClickListeners();




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
