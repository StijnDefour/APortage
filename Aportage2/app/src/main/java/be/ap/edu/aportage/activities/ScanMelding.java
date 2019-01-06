package be.ap.edu.aportage.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.MediaManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import be.ap.edu.aportage.R;
import be.ap.edu.aportage.interfaces.CampusKleuren;

public class ScanMelding extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 5;
    String mCurrentPhotoPath;
    Button btnCampus;
    Button btnVerdiep;
    Button btnLokaal;
    ImageView imageView;
    Button button;

    private CampusKleuren campusKleuren = new CampusKleuren();

    EditText tvTitel;

    Button btnOk;
    Button btnAnnuleer;

    String s_campus;
    String s_verdieping;
    String s_lokaal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_melding);

        this.imageView = findViewById(R.id.imageView);
        this.button = findViewById(R.id.button);
        this.btnCampus = findViewById(R.id.btn_campus_afk);
        this.btnVerdiep = findViewById(R.id.btn_verdiep_nr);
        this.btnLokaal = findViewById(R.id.btn_melding_lokaalnr);

        this.tvTitel = findViewById(R.id.et_melding_titel);

        btnOk = findViewById(R.id.btn_melding_ok);
        btnAnnuleer = findViewById(R.id.btn_melding_annuleren);

        lokaalButtonsOpvullen();
        buttonsAddClickEvents();

        if (savedInstanceState != null) {
            this.tvTitel.setText(savedInstanceState.getString("tvTitel"));
            this.mCurrentPhotoPath = savedInstanceState.getString("mCurrentPhotoPath");
        } else {
            Log.e("State", "state did not exist");
        }

//        Cloudinary init
        try {
            MediaManager.init(this);
        } catch (Exception e) {

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Meldingen.class);
        intent.putExtra(getString(R.string.campus_intent), s_campus);
        intent.putExtra(getString(R.string.verdieping_intent), s_verdieping);
        intent.putExtra(getString(R.string.lokaal_intent), s_lokaal);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("mCurrentPhotoPath", this.mCurrentPhotoPath);
        savedInstanceState.putString("tvTitel", this.tvTitel.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }

    private void buttonsAddClickEvents() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakePhoto(view);
            }
        });

        btnCampus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScanMelding.this, Campussen.class);
                startActivity(intent);
            }
        });
        btnVerdiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScanMelding.this, Verdiepingen.class);
                intent.putExtra(getString(R.string.campus_intent), s_campus);
                startActivity(intent);
            }
        });
        btnLokaal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScanMelding.this, Lokalen.class);
                intent.putExtra(getString(R.string.campus_intent), s_campus);
                intent.putExtra(getString(R.string.verdieping_intent), s_verdieping);
                startActivity(intent);
            }
        });
        btnAnnuleer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScanMelding.this, Meldingen.class);
                intent.putExtra(getString(R.string.campus_intent), s_campus);
                intent.putExtra(getString(R.string.verdieping_intent), s_verdieping);
                intent.putExtra(getString(R.string.lokaal_intent), s_lokaal);
                startActivity(intent);
                ScanMelding.this.finish();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slaMeldingOpNaarDeDB();
//                Intent intent = new Intent(ScanMelding.this, Meldingen.class);
//                intent.putExtra(getString(R.string.campus_intent), s_campus);
//                intent.putExtra(getString(R.string.verdieping_intent), s_verdieping);
//                intent.putExtra(getString(R.string.lokaal_intent), s_lokaal);
//                startActivity(intent);
//                ScanMelding.this.finish();
            }
        });
    }

    private void maakIntent(Activity destination) {

    }
    private void saveInDB() {}

    private void slaMeldingOpNaarDeDB() {
        //todo test foto api
        Cloudinary cloudinary = new Cloudinary();
        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String strDate = sdf.format(c.getTime());

            MediaManager.get().upload(mCurrentPhotoPath)
                    .unsigned("qjmws079")
                    .option("folder", "meldingen/")
                    .option("public_id", strDate)
                    .dispatch();
            Log.e("test", strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //todo vul in juiste data en push naar database
        //be.ap.edu.aportage.models.Melding melding = new be.ap.edu.aportage.models.Melding("MockMelding", "Blablablablabla", new String[]{"ELL","-01","005"}, "behandeling", new Date());
    }

    private void lokaalButtonsOpvullen() {
        Intent inkomendeIntent = this.getIntent();
        s_campus = inkomendeIntent.getStringExtra(getString(R.string.campus_intent));
        s_verdieping = inkomendeIntent.getStringExtra(getString(R.string.verdieping_intent));
        s_lokaal = inkomendeIntent.getStringExtra(getString(R.string.lokaal_intent));
        btnCampus.setText(s_campus);
        btnVerdiep.setText(s_verdieping);
        btnLokaal.setText(s_lokaal);
        this.btnCampus.setBackgroundColor(campusKleuren.getCampusColor(s_campus.toLowerCase(), this));
        this.btnVerdiep.setBackgroundColor(campusKleuren.getVerdiepingColor(s_campus.toLowerCase(), this));
        this.btnLokaal.setBackgroundColor(campusKleuren.getLokaalColor(s_campus.toLowerCase(), this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File imgFile = new  File(mCurrentPhotoPath);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                this.imageView.setImageBitmap(myBitmap);
            }
        }
    }

    public void TakePhoto(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {}
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "be.ap.edu.aportage",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
