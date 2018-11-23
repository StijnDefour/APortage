package be.ap.edu.aportage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
                Intent intent = new Intent(activity, ScanForFoodActivity.class);
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
    }

}
