package be.ap.edu.aportage.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import be.ap.edu.aportage.R;
import be.ap.edu.aportage.managers.MyUserManager;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterenActivity extends AppCompatActivity {

    private MyUserManager myUserManager;

    private EditText naamEditText;
    private EditText mailEditText;
    private EditText wwEditText;
    private CheckBox facilitairCB;
    private CheckBox gdprCB;
    private Button registreerBtn;
    private TextView gaNaarLoginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeren);

        this.myUserManager = MyUserManager.getInstance(this.getApplicationContext());
        this.naamEditText = (EditText) findViewById(R.id.et_registeren_naam);
        this.mailEditText = (EditText) findViewById(R.id.et_registeren_email);
        this.wwEditText = (EditText) findViewById(R.id.et_registreren_wachtwoord);
        this.facilitairCB = (CheckBox) findViewById(R.id.cb_registreren_facilitair);
        this.gdprCB = (CheckBox) findViewById(R.id.cb_registreren_gdpr);
        this.registreerBtn = (Button)findViewById(R.id.btn_registeren_registreer);
        this.gaNaarLoginTextView = (TextView) findViewById(R.id.tv_registreren_loginlink);

        registreerOnClicks();

    }

    private void registreerOnClicks() {
        this.registreerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maakMelder();
            }
        });
    }

    void maakMelder(){

        ParseUser user = new ParseUser();
// Set the user's username and password, which can be obtained by a forms
        Log.d("USER", this.naamEditText.getText().toString());
        user.setUsername(this.naamEditText.getText().toString());

        Log.d("USER", this.wwEditText.getText().toString());
        user.setPassword(this.wwEditText.getText().toString());
        user.setEmail(this.mailEditText.getText().toString());
        Log.d("PARSEUSER", user.toString());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(RegisterenActivity.this, "Registratie gelukt!", Toast.LENGTH_LONG).show();
                } else {
                    ParseUser.logOut();
                    Toast.makeText(RegisterenActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    void slaOpNaarDB(){

    }

    void toonFoutMelding(){

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
                        Intent intent = new Intent(RegisterenActivity.this, UitloggenActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}
