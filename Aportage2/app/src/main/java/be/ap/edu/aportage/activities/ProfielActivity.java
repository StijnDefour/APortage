package be.ap.edu.aportage.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonArrayRequest;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.json.JSONObject;

import java.security.acl.Group;

import be.ap.edu.aportage.R;
import be.ap.edu.aportage.interfaces.IVolleyCallback;
import be.ap.edu.aportage.managers.MyDatamanger;
import be.ap.edu.aportage.models.Melder;

public class ProfielActivity extends AppCompatActivity {

    private EditText et_naam;
    private EditText et_mail;
    private EditText et_oud_ww;
    private EditText et_nieuw_ww;
    private ParseUser parseUser;
    private Button btn_opslaan;
    private ImageView iv_uitloggen;
    private Intent uitgaandeIntent;

    private Melder melder;
    private MyDatamanger myDatamanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiel);

        this.et_naam = findViewById(R.id.et_profiel_naam);
        this.et_mail = findViewById(R.id.et_profiel_mail);
        this.et_oud_ww = findViewById(R.id.et_profiel_ww_oud);
        this.et_nieuw_ww = findViewById(R.id.et_profiel_ww_nieuw);
        this.btn_opslaan = findViewById(R.id.btn_profiel_opslaan);
        this.iv_uitloggen = findViewById(R.id.iv_profiel_loguit);


        haalMelderDetailsVanDB();
        setClicks();

    }


    private void haalMelderDetailsVanDB() {
        JsonArrayRequest req = this.myDatamanger.getMelderMetMelderId(ParseUser.getCurrentUser().getObjectId(), new IVolleyCallback() {
            @Override
            public void onCustomSuccess(Object data) {
                melder = (Melder)data;
                vulVeldenIn();
            }

            @Override
            public void onPostSuccess(JSONObject response) {

            }

            @Override
            public void onFailure() {

            }
        });
        this.myDatamanger.addToRequestQueue(req);
    }

    private void vulVeldenIn() {

        this.et_naam.setText(this.melder.getNaam());
        this.et_mail.setText(ParseUser.getCurrentUser().getEmail());

    }

    private void setClicks() {
        this.iv_uitloggen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.getCurrentUser().logOut();
                toonLogUitBericht();
                gaNaarLoginActivity();
            }
        });

        this.btn_opslaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                komtOudWachtwoordOvereen();
            }
        });
    }

    private void slaNieuweMelderGegevensOp() {
        //todo: update parseUser
        //todo: doe een put request naar mlab voor de melder details
    }

    private void komtOudWachtwoordOvereen() {
       ParseUser.logInInBackground(ParseUser.getCurrentUser().getUsername(), this.et_oud_ww.getText().toString(), new LogInCallback() {
           @Override
           public void done(ParseUser user, ParseException e) {
               if(user != null){
                   slaNieuweMelderGegevensOp();
               } else {
                   toonFoutbericht();
               }
           }
       });
    }

    private void toonFoutbericht() {
        Toast.makeText(ProfielActivity.this, "Je oud wachtwoord is verkeerd, probeer opnieuw!", Toast.LENGTH_LONG);
        herstartActivity();
    }

    private void herstartActivity() {
        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);
    }

    private void gaNaarLoginActivity() {
        this.uitgaandeIntent = new Intent(ProfielActivity.this, LoginActivity.class);
        this.uitgaandeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(this.uitgaandeIntent);
    }

    private void toonLogUitBericht() {
        Toast.makeText(ProfielActivity.this, "Je bent uitgelogd!", Toast.LENGTH_LONG);
    }
}
