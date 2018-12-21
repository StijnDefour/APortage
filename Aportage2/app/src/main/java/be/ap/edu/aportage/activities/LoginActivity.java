package be.ap.edu.aportage.activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import be.ap.edu.aportage.R;
import be.ap.edu.aportage.managers.MyUserManager;

public class LoginActivity extends AppCompatActivity {

    private MyUserManager myUserManager;

    private EditText mailEditText;
    private EditText wwEditText;
    private Button loginBtn;
    private Button regBtn;
    private Intent uitgaandeIntent;
    private TextView foutMeldingTextView; //todo: foutmeldingen tonen bij foute login


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.myUserManager = MyUserManager.getInstance(this.getApplicationContext());
        this.mailEditText = (EditText) findViewById(R.id.et_email);
        this.wwEditText = (EditText) findViewById(R.id.et_login_wachtwoord);
        this.loginBtn = (Button) findViewById(R.id.btn_login);
        this.regBtn = (Button) findViewById(R.id.btn_registreren);


    }

    void gaNaarRegistreren(){

    }

    void registreerOnClicks(){

    }

    void toonFoutmelding(String fout){

    }
}
