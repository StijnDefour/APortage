package be.ap.edu.aportage;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokalen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        this.lokalenLijst.add(00);
        this.lokalenLijst.add(01);
        this.lokalenLijst.add(02);
        this.lokalenRV = (RecyclerView) findViewById(R.id.rv_lokalen);
        this.lokaalCV = (MaterialCardView) findViewById(R.id.matcv_lokaal);
        this.lokaalLM = new LinearLayoutManager(this);
        this.lokalenRV.setLayoutManager(this.lokaalLM);
        this.lokalenAdapter = new LokalenRecyclerAdapter(this, this.lokalenLijst);
        this.lokalenRV.setAdapter(lokalenAdapter);

    }

}
