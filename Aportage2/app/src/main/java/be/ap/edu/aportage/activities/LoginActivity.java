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

        this.regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gaNaarRegistrerenActivity();
            }
        });


    }

    void gaNaarRegistrerenActivity(){
        this.uitgaandeIntent = new Intent(LoginActivity.this, RegisterenActivity.class);
        this.uitgaandeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(this.uitgaandeIntent);

    }

    void registreerOnClicks(){

    }

    void toonFoutmelding(String fout){

    }

    void logMelderIn(){
        ParseUser.logInInBackground(this.mailEditText.getText().toString(), this.wwEditText.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {
                    alertDisplayer("Sucessful Login","Welcome back" + parseUser.getUsername() + "!");
                } else {
                    ParseUser.logOut();
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void alertDisplayer(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // don't forget to change the line below with the names of your Activities
                        Intent intent = new Intent(LoginActivity.this, UitloggenActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}
