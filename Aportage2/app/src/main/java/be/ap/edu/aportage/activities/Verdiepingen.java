package be.ap.edu.aportage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;


import com.android.volley.toolbox.JsonArrayRequest;

import be.ap.edu.aportage.R;
import be.ap.edu.aportage.interfaces.ApiContract;
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verdiepingen);

        this.dataManager = MyDatamanger.getInstance(this.getApplicationContext());
        this.inkomendeIntent = getIntent();
        String campus_afk = this.inkomendeIntent.getStringExtra("campus_afk");
        this.navBtnCampus = findViewById(R.id.btn_nav_campus);
        this.navBtnCampus.setText(campus_afk);

        this.verdiepenAdapter = new VerdiepenRecyclerAdapter(this, this.dataManager.getVerdiepenLijst(campus_afk), campus_afk);

        this.verdiepenLM = new LinearLayoutManager(this);
        this.verdiepenRV = (RecyclerView) findViewById(R.id.rv_verdiepingen);
        //this.verdiepCV = (CardView) findViewById(R.id.cv)


        this.verdiepenRV.setLayoutManager(this.verdiepenLM);
        this.verdiepenRV.setAdapter(this.verdiepenAdapter);


        //this.verdiepenAdapter = new VerdiepenRecyclerAdapter(this, this.dataManager.getVerdiepenLijst(campus_afk), campus_afk);



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
