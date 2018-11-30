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

import java.util.ArrayList;
import java.util.List;

import be.ap.edu.aportage.models.Melding;
import be.ap.edu.aportage.models.MeldingenRecyclerAdapter;
import be.ap.edu.aportage.models.MockDataManager;

public class Meldingen extends AppCompatActivity {

    private Button meldingenCampusBtn;
    private Button meldingenVerdiepBtn;
    private Button meldingenLokaalBtn;
    private RecyclerView meldingenRV;
    private CardView meldingCV;
    private LinearLayoutManager meldingenLM;
    private MeldingenRecyclerAdapter meldingenAdapter;
    private List<Melding> meldingenLijst;
    private Intent binnenkomendeIntent;
    private MockDataManager dataManager = MockDataManager.getInstance();

    private String s_campus;
    private String s_verdieping;
    private String s_lokaal;


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
        this.meldingenLijst = dataManager.getMeldingenLijst();
        this.binnenkomendeIntent = getIntent();

        this.meldingenLM = new LinearLayoutManager(this);
        this.meldingenRV.setLayoutManager(this.meldingenLM);

        this.meldingenAdapter = new MeldingenRecyclerAdapter(this, this.meldingenLijst);
        this.meldingenRV.setAdapter(this.meldingenAdapter);



        Bundle b = this.binnenkomendeIntent.getExtras();
        checkBundleForData(b);


    }

    private void checkBundleForData(Bundle b) {

        if(b!=null)
        {
            String j = (String) b.get("lokaalInfo");
            lokaalButtonsOpvullen(j);
            Log.v("Meldingen", j);
        }

    }

    private void lokaalButtonsOpvullen(String lokaalInfo) {
            try {
                lokaalInfo = lokaalInfo.replace(" ", "");
                lokaalInfo = lokaalInfo.replace(".", "");
            } catch (NullPointerException e) {
                Log.e("Error",e.toString());
            }

            try {
                if (lokaalInfo == null) throw new AssertionError();
                s_campus = lokaalInfo.substring(0,3);
                lokaalInfo = lokaalInfo.substring(3, lokaalInfo.length());
                s_lokaal = lokaalInfo.substring(lokaalInfo.length()-3,lokaalInfo.length());
                lokaalInfo = lokaalInfo.substring(0,lokaalInfo.length()-3);
                s_verdieping = lokaalInfo;
                this.meldingenCampusBtn.setText(s_campus);
                this.meldingenVerdiepBtn.setText(s_verdieping);
                this.meldingenLokaalBtn.setText(s_lokaal);
            } catch (StringIndexOutOfBoundsException e) {
                Log.e("Error",e.toString());
                Intent intent = new Intent(this, Overzicht.class);
                startActivity(intent);
            }
        }

}
