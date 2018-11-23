package be.ap.edu.aportage;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Campussen extends AppCompatActivity {

    private RecyclerView mijnCampussenRV;
    private GridLayoutManager mijnGridLM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campussen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialisatie properties voor recyclerview van campussen
        this.mijnCampussenRV = (RecyclerView) findViewById(R.id.rv_campussen);
        this.mijnGridLM = new GridLayoutManager(getApplicationContext(), 2 /*, LinearLayoutManager.HORIZONTAL,false*/);
        this.mijnCampussenRV.setLayoutManager(this.mijnGridLM);


    }

}
