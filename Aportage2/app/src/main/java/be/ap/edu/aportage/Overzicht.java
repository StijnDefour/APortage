package be.ap.edu.aportage;

import android.os.Bundle;
import android.app.Activity;

import be.ap.edu.aportage.models.DataManager;

public class Overzicht extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overzicht);

        DataManager dm = DataManager.getInstance();
    }

}
