package be.ap.edu.aportage.activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import be.ap.edu.aportage.R;
import be.ap.edu.aportage.managers.MyUserManager;

public class LoginActivity extends AppCompatActivity {

    private MyUserManager myUserManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.myUserManager = MyUserManager.getInstance(this.getApplicationContext());
    }
}
