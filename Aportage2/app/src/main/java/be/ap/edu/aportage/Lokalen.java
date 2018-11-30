package be.ap.edu.aportage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.card.MaterialCardView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

import be.ap.edu.aportage.models.LokalenRecyclerAdapter;

public class Lokalen extends AppCompatActivity {

    private LokalenRecyclerAdapter lokalenAdapter;
    private RecyclerView lokalenRV;
    private MaterialCardView lokaalCV;

    private LinearLayoutManager lokaalLM;
    private List<Integer> lokalenLijst = new ArrayList<>();

    Activity activity;

    String s_campus;
    String s_verdieping;
    String s_lokaal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokalen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        s_campus = "ELL";
        s_verdieping = "01";
        s_lokaal = "001";

        this.lokalenLijst.add(0);
        this.lokalenLijst.add(1);
        this.lokalenLijst.add(2);
        this.lokalenRV = (RecyclerView) findViewById(R.id.rv_lokalen);
        this.lokaalCV = (MaterialCardView) findViewById(R.id.matcv_lokaal);
        this.lokaalLM = new LinearLayoutManager(this);
        this.lokalenRV.setLayoutManager(this.lokaalLM);
        this.lokalenAdapter = new LokalenRecyclerAdapter(this, this.lokalenLijst);
        this.lokalenRV.setAdapter(lokalenAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        activity = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ScanMelding.class);
                intent.putExtra("lokaalInfo", s_campus+s_verdieping+s_lokaal);
                startActivity(intent);
            }
        });
    }

}
