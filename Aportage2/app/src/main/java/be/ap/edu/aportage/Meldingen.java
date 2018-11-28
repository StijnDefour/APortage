package be.ap.edu.aportage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import be.ap.edu.aportage.models.MeldingenRecyclerAdapter;

public class Meldingen extends AppCompatActivity {

    private Button meldingenCampusBtn;
    private Button meldingenVerdiepBtn;
    private Button meldingenLokaalBtn;
    private RecyclerView meldingenRV;
    private CardView meldingCV;
    private LinearLayoutManager meldingenLM;
    private MeldingenRecyclerAdapter meldingenAdapter;
    private Intent incomingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meldingen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.meldingenCampusBtn = (Button) findViewById(R.id.btn_campus_afk);
        this.meldingenVerdiepBtn = (Button) findViewById(R.id.btn_verdiep_nr);
        this.meldingenLokaalBtn = (Button) findViewById(R.id.btn_melding_lokaalnr);
        this.meldingenRV = (RecyclerView) findViewById(R.id.rv_meldingen);
        this.meldingCV = (CardView) findViewById(R.id.cv_melding);
        this.meldingenLM = new LinearLayoutManager(this);
        this.incomingIntent = getIntent();
        Bundle b = this.incomingIntent.getExtras();
        checkBundleForData(b);



    }

    private void checkBundleForData(Bundle b) {

        if(b!=null)
        {
            String j = (String) b.get("lokaal_id");
            lokaalButtonsOpvullen();
            Log.v("Meldingen", j.toString());
        }

    }

    private void lokaalButtonsOpvullen() {

        //todo: nog data toevoegen dat uit de intents wordt gehaald

        this.meldingenCampusBtn.setText("LOL");
        this.meldingenVerdiepBtn.setText("V1");
        this.meldingenLokaalBtn.setText("001");
    }

}
