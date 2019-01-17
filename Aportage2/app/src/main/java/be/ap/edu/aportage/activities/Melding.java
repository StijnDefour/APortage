package be.ap.edu.aportage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import be.ap.edu.aportage.R;

import be.ap.edu.aportage.interfaces.CampusKleuren;

import be.ap.edu.aportage.helpers.ApiContract;
import be.ap.edu.aportage.helpers.MongoCollections;
import be.ap.edu.aportage.interfaces.IVolleyCallback;
import be.ap.edu.aportage.managers.MyDatamanger;
import be.ap.edu.aportage.models.Melder;


public class Melding extends Activity {

    Intent binnenkomendeIntent;
    Intent uitgaandeIntent;
    Button btn_campus_afk;
    Button btn_verdiep_nr;
    Button btn_melding_lokaalnr;
    FloatingActionButton nieuweMeldingFab;
    ImageView iv_melding_foto;
    TextView tv_melding_titel;
    TextView tv_melding_beschrijving;
    TextView tv_melding_melder;
    TextView tv_melding_tijdstip;
    //todo: kleur op basis van status aanpassen voor frameLayout
    FrameLayout fl_melding_status;


    private CampusKleuren campusKleuren = new CampusKleuren();

    private String s_campus;
    private String s_verdieping;
    private String s_lokaal;
    private String s_melding_id;
    private MyDatamanger datamanager;
    private be.ap.edu.aportage.models.Melding melding = new be.ap.edu.aportage.models.Melding();
    private Melder melder = new Melder();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_melding);

        this.binnenkomendeIntent = getIntent();
        this.s_melding_id = this.binnenkomendeIntent.getStringExtra("melding_id");

        this.btn_campus_afk = findViewById(R.id.btn_campus_afk);
        this.btn_verdiep_nr = findViewById(R.id.btn_verdiep_nr);
        this.btn_melding_lokaalnr = findViewById(R.id.btn_melding_lokaalnr);
        this.nieuweMeldingFab = findViewById(R.id.melding_fab);
        this.iv_melding_foto = findViewById(R.id.iv_melding_foto_groot);
        this.tv_melding_titel = findViewById(R.id.tv_melding_titel);
        this.tv_melding_beschrijving = findViewById(R.id.tv_melding_beschrijving);
        this.tv_melding_melder = findViewById(R.id.tv_melding_melder);
        this.tv_melding_tijdstip = findViewById(R.id.tv_melding_tijdstip);

        this.datamanager = MyDatamanger.getInstance(getApplicationContext());
        this.tv_melding_titel.setText("Melding wordt geladen.");
        this.datamanager.addToRequestQueue(getMeldingDetails());
        this.datamanager.addToRequestQueue(getMelderDetails());

        navigatieButtonsOpvullen();
        registreerButtonOnClicks();
    }

    JsonObjectRequest getMeldingDetails() {

        JsonObjectRequest req = this.datamanager.getMeldingMetId(this.s_melding_id, new IVolleyCallback() {
            @Override
            public void onCustomSuccess(Object data) {
                melding = datamanager.parseMeldingJson((JSONObject)data);
            }

            @Override
            public void onPostSuccess(JSONObject response) {

                //ignore
            }

            @Override
            public void onFailure() {
                //ignore
            }
        });

       return req;




    }

    JsonArrayRequest getMelderDetails(){
        JsonArrayRequest req = this.datamanager.getMelderMetMelderId(ParseUser.getCurrentUser().getObjectId(), new IVolleyCallback() {
            @Override
            public void onCustomSuccess(Object data) {
                melder = (Melder)data;
                vulMeldingDetailsIn(melder);

            }

            @Override
            public void onPostSuccess(JSONObject response) {

            }

            @Override
            public void onFailure() {

            }
        });

      return req;

    }

    private void vulMeldingDetailsIn(Melder melder) {
        this.tv_melding_titel.setText(this.melding.titel);
        this.tv_melding_beschrijving.setText(this.melding.omschrijving);
        this.tv_melding_tijdstip.setText(this.melding.datumString);
        this.tv_melding_melder.setText(melder.getNaam()); //todo_done: op basis van melderID een melder object posten en getten van de db
        String url = "https://res.cloudinary.com/dt6ae1zfh/image/upload/c_fit,w_500/meldingen/" + melding.getImgUrl() + ".jpg";
        Picasso.get().load(url).into(this.iv_melding_foto);

    }

    private void registreerButtonOnClicks() {
        this.btn_melding_lokaalnr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gaNaarLokalen();
            }
        });
        this.btn_verdiep_nr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gaNaarVerdiepen();
            }
        });
        this.btn_campus_afk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gaNaarCampussen();
            }
        });
        this.nieuweMeldingFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gaNaarScanMelding();
            }
        });
    }

    private void navigatieButtonsOpvullen(){

        try {
            this.s_campus = this.binnenkomendeIntent.getStringExtra(getString(R.string.campus_intent));
            this.s_verdieping = this.binnenkomendeIntent.getStringExtra(getString(R.string.verdieping_intent));
            this.s_lokaal = this.binnenkomendeIntent.getStringExtra(getString(R.string.lokaal_intent));
            this.btn_campus_afk.setText(this.s_campus);
            this.btn_verdiep_nr.setText(this.s_verdieping);
            this.btn_melding_lokaalnr.setText(this.s_lokaal);
            this.btn_campus_afk.setBackgroundColor(campusKleuren.getCampusColor(s_campus.toLowerCase(), this));
            this.btn_verdiep_nr.setBackgroundColor(campusKleuren.getVerdiepingColor(s_campus.toLowerCase(), this));
            this.btn_melding_lokaalnr.setBackgroundColor(campusKleuren.getLokaalColor(s_campus.toLowerCase(), this));
        } catch (Error e) {
            Log.e("navigatieButtonsOpvullen Mislukt", e.getMessage());
        }
    }

    private void gaNaarScanMelding(){
        this.uitgaandeIntent = new Intent(this, ScanMelding.class);
        this.uitgaandeIntent.putExtra(getString(R.string.verdieping_intent), s_verdieping);
        this.uitgaandeIntent.putExtra(getString(R.string.campus_intent), s_campus);
        this.uitgaandeIntent.putExtra(getString(R.string.lokaal_intent), s_lokaal);
        this.uitgaandeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); //todo: is er een verschilt tussen FLAG_ACTIVITY REORDER EN Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity( this.uitgaandeIntent);
        Melding.this.finish();
    }

    private void gaNaarLokalen() {

        this.uitgaandeIntent = new Intent(this, Lokalen.class);
        this.uitgaandeIntent.putExtra(getString(R.string.verdieping_intent), this.btn_verdiep_nr.getText());
        this.uitgaandeIntent.putExtra(getString(R.string.campus_intent), this.btn_campus_afk.getText());
        startActivity(this.uitgaandeIntent);
        Melding.this.finish();

    }

    private void gaNaarVerdiepen(){

        this.uitgaandeIntent = new Intent(this, Verdiepingen.class);
        this.uitgaandeIntent.putExtra(getString(R.string.campus_intent), this.btn_campus_afk.getText());
        startActivity(this.uitgaandeIntent);
        Melding.this.finish();
    }

    private void gaNaarCampussen(){
        this.uitgaandeIntent = new Intent(this, Campussen.class);
        startActivity(this.uitgaandeIntent);
        Melding.this.finish();
    }

    @Override
    public void onBackPressed() {
        this.uitgaandeIntent = new Intent(this, Meldingen.class);
        this.uitgaandeIntent.putExtra(getString(R.string.verdieping_intent), s_verdieping);
        this.uitgaandeIntent.putExtra(getString(R.string.campus_intent), s_campus);
        this.uitgaandeIntent.putExtra(getString(R.string.lokaal_intent), s_lokaal);
        startActivity(this.uitgaandeIntent);
        Melding.this.finish();
    }

    private void gaNaarMelding(){
        Intent intent = new Intent(this, Melding.class);
        intent.putExtra("campus_afk", s_campus );
        intent.putExtra("verdiep_nr", s_verdieping);
        intent.putExtra("lokaal_nr", s_lokaal);

        startActivity(intent);
    }

}
