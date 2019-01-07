package be.ap.edu.aportage.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import be.ap.edu.aportage.R;
import be.ap.edu.aportage.managers.MyUserManager;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private MyUserManager myUserManager;

    private EditText mailEditText;
    private EditText wwEditText;
    private Button loginBtn;
    private Button regBtn;
    private Intent uitgaandeIntent;
    //private TextView foutMeldingTextView; //todo_done: foutmeldingen tonen bij foute login


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        this.mailEditText = (EditText) findViewById(R.id.et_email);
        this.wwEditText = (EditText) findViewById(R.id.et_login_wachtwoord);
        this.loginBtn = (Button) findViewById(R.id.btn_login);
        this.regBtn = (Button) findViewById(R.id.btn_registreren);


        registreerOnClicks();


    }


    void registreerOnClicks(){
        this.regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gaNaarRegistrerenActivity();
            }
        });
        this.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logMelderIn();
            }
        });
    }

    void gaNaarRegistrerenActivity(){
        this.uitgaandeIntent = new Intent(LoginActivity.this, RegisterenActivity.class);
        this.uitgaandeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(this.uitgaandeIntent);

    }




    void logMelderIn(){
        ParseUser.logInInBackground(this.mailEditText.getText().toString(), this.wwEditText.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {
                    if(parseUser.getBoolean("emailVerified")) {
                        Toast.makeText(LoginActivity.this, "Je bent ingelogd!", Toast.LENGTH_LONG).show();
                        gaNaarOverzichtActivity();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Bevestig eerst jouw emailadres en probeer opnieuw", Toast.LENGTH_LONG).show();
                        herlaadActivity();
                    }


                } else {
                    ParseUser.logOut();
                    Toast.makeText(LoginActivity.this, "Oei, jouw e-mailadres of wachtwoord worden niet herkent. Probeer opnieuw.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void herlaadActivity() {
        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);
    }

    private void gaNaarOverzichtActivity() {
        this.uitgaandeIntent = new Intent(LoginActivity.this, Overzicht.class);
        this.uitgaandeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(this.uitgaandeIntent);
    }

}
