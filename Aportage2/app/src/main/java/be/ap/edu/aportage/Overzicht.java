package be.ap.edu.aportage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import be.ap.edu.aportage.models.Campus;
import be.ap.edu.aportage.models.DataManager;

public class Overzicht extends Activity {

    private ImageView iv_scannen_bg;
    private ImageView iv_zoeken_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overzicht);

        iv_scannen_bg = findViewById(R.id.iv_scannen_bg);
        iv_zoeken_bg = findViewById(R.id.iv_zoeken_bg);

        final Activity activity = this;
        iv_scannen_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ScanLokaal.class);
                startActivity(intent);
            }
        });
        iv_zoeken_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Campussen.class);
                startActivity(intent);
            }
        });

        DataManager dm = DataManager.getInstance();
        ArrayList<Campus> campusLijst = DataManager.getCampusList();

        for (Campus c: campusLijst) {
            Log.d("overzicht", c.getNaam());
        }
    }

}
