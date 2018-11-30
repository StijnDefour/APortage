package be.ap.edu.aportage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import be.ap.edu.aportage.models.MockDataManager;
import be.ap.edu.aportage.models.VerdiepenRecyclerAdapter;

public class Verdiepingen extends AppCompatActivity {


    private RecyclerView verdiepenRV;
    private LinearLayoutManager verdiepenLM;
    private VerdiepenRecyclerAdapter verdiepenAdapter;
    private MockDataManager dataManager = MockDataManager.getInstance();
    private Intent uitgaandeIntent;
    private Intent inkomendeIntent;
    private Button navBtnCampus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verdiepingen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.inkomendeIntent = getIntent();
        String campus_naam = this.inkomendeIntent.getStringExtra("campus_titel");
        String campus_afk = this.inkomendeIntent.getStringExtra("campus_afk");
        this.navBtnCampus = (Button)findViewById(R.id.btn_nav_campus);
        this.navBtnCampus.setText(campus_afk);
        this.verdiepenRV = (RecyclerView) findViewById(R.id.rv_verdiepingen);
        this.verdiepenLM = new LinearLayoutManager(this);
        this.verdiepenAdapter =
                new VerdiepenRecyclerAdapter(this, this.dataManager.getVerdiepenLijst(campus_naam), campus_afk);
        this.verdiepenRV.setLayoutManager(this.verdiepenLM);
        this.verdiepenRV.setAdapter(this.verdiepenAdapter);



    }



}
