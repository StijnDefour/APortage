package be.ap.edu.aportage;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import be.ap.edu.aportage.models.MockDataManager;
import be.ap.edu.aportage.models.VerdiepingenRecyclerAdapter;

public class Verdiepingen extends AppCompatActivity {

    private RecyclerView verdiepenRV;
    private CardView verdiepCV;
    private LinearLayoutManager verdiepLM;
    private VerdiepingenRecyclerAdapter verdiepenAdapter;
    private MockDataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verdiepingen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.verdiepenRV = (RecyclerView) findViewById(R.id.rv_verdiepingen);
        //this.verdiepCV = (CardView) findViewById(R.id.cv)
        this.dataManager = MockDataManager.getInstance();
        //todo: hier nog correct geselcteerde verdiep meegeven met de campusLijst
        this.verdiepenAdapter = new VerdiepingenRecyclerAdapter(this, this.dataManager.getVerdiepLijst(0));
        //todo: verder recyclerView aanvullen

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
