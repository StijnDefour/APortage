package be.ap.edu.aportage.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.volley.toolbox.JsonArrayRequest;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.ap.edu.aportage.R;
import be.ap.edu.aportage.interfaces.IMeldingCallBack;
import be.ap.edu.aportage.interfaces.IVolleyCallback;
import be.ap.edu.aportage.managers.MyDatamanger;
import be.ap.edu.aportage.models.Melding;
import be.ap.edu.aportage.recycleradapters.MeldingenRecyclerAdapter;

public class MijnMeldingenActivity extends AppCompatActivity {

    private MyDatamanger myDatamanger;

    private TextView titelTV;
    private RecyclerView mijnMeldingenRV;
    private LinearLayoutManager meldingenLM;
    private MeldingenRecyclerAdapter meldingenAdapter;
    //private Intent binnenkomendeIntent;
    private Intent uitgaandeIntent;
    private List<Melding> meldingenLijst = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mijn_meldingen);
        this.myDatamanger = MyDatamanger.getInstance(getApplicationContext());
        this.titelTV = (TextView)findViewById(R.id.tv_mijnmeldingen_titel);
        this.mijnMeldingenRV = (RecyclerView) findViewById(R.id.rv_meldingen);
        this.meldingenLM = new LinearLayoutManager(this);
        this.mijnMeldingenRV.setLayoutManager(this.meldingenLM);
        this.meldingenAdapter = new MeldingenRecyclerAdapter(getApplicationContext(), this.meldingenLijst);        
        this.mijnMeldingenRV.setAdapter(this.meldingenAdapter);

        this.titelTV.setText(ParseUser.getCurrentUser().getUsername());
        laadMijnMeldingenData();




    }

    private void laadMijnMeldingenData(){
        JsonArrayRequest req = this.myDatamanger.getMeldingenLijstMetId(ParseUser.getCurrentUser().getObjectId(), new IVolleyCallback() {
            @Override
            public void onCustomSuccess(Object data) {
                meldingenLijst = myDatamanger.geefParsedMeldingen((JSONArray)data);
                meldingenAdapter.setMeldingenList(meldingenLijst);
                meldingenAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPostSuccess(JSONObject response) {
                //ignore
            }
        });
        this.myDatamanger.addToRequestQueue(req);
    }




}
