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
import be.ap.edu.aportage.models.Melder;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterenActivity extends AppCompatActivity {

    private MyUserManager myUserManager;
    private Intent uitgaandeIntent;

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
        this.gaNaarLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gaNaarLoginActivity();
            }
        });
    }
    void gaNaarLoginActivity(){
        this.uitgaandeIntent = new Intent(RegisterenActivity.this, LoginActivity.class);
        this.uitgaandeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(this.uitgaandeIntent);
    }

    void maakMelder(){

        //todo: melder object maken en dit ook opslaan met de objectID van parseuser object naar mlab db

        Melder melder = new Melder();
        ParseUser user = new ParseUser();
// Set the user's username and password, which can be obtained by a forms
        Log.d("USER", this.mailEditText.getText().toString());
        user.setUsername(this.mailEditText.getText().toString());


        Log.d("USER", this.wwEditText.getText().toString());
        user.setPassword(this.wwEditText.getText().toString());
        user.setEmail(this.mailEditText.getText().toString());
        Log.d("PARSEUSER", user.toString());

        registreerMelder(user);

    }

   void registreerMelder(ParseUser user){
       user.signUpInBackground(new SignUpCallback() {
           @Override
           public void done(ParseException e) {
               if (e == null) {
                   Toast.makeText(RegisterenActivity.this, "Registratie gelukt!", Toast.LENGTH_LONG).show();
                   ParseUser.logOut();
                   gaNaarLoginActivity();
               } else {
                   ParseUser.logOut();
                   Toast.makeText(RegisterenActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                   wisAlleVelden();

               }
           }
       });
   }

    void toonFoutMelding(){

    }

    void wisAlleVelden(){
        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);
    }


}
