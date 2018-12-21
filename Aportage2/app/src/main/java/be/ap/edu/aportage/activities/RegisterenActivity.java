package be.ap.edu.aportage.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import be.ap.edu.aportage.R;
import be.ap.edu.aportage.managers.MyUserManager;

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
    }

    void maakMelder(){

    }

    void slaOpNaarDB(){

    }

    void toonFoutMelding(){

    }
}
