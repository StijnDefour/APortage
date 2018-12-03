package be.ap.edu.aportage;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;

public class Melding extends Activity {

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_melding);

        FloatingActionButton fab = findViewById(R.id.fab);
        activity = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn_campus_afk = findViewById(R.id.btn_campus_afk);
                Button btn_verdiep_nr = findViewById(R.id.btn_verdiep_nr);
                Button btn_melding_lokaalnr = findViewById(R.id.btn_melding_lokaalnr);
                Intent intent = new Intent(activity, ScanMelding.class);

                intent.putExtra("lokaalInfo", btn_campus_afk.getText().toString()+btn_verdiep_nr.getText().toString()+btn_melding_lokaalnr.getText().toString());
                startActivity(intent);
            }
        });
    }

}
