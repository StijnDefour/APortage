package be.ap.edu.aportage;

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
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScanMelding extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 5;
    String mCurrentPhotoPath;
    Button btnCampus;
    Button btnVerdiep;
    Button btnLokaal;
    ImageView imageView;
    Button button;

    String s_campus;
    String s_verdieping;
    String s_lokaal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_melding);

        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);
        btnCampus = findViewById(R.id.btn_campus_afk);
        btnVerdiep = findViewById(R.id.btn_verdiep_nr);
        btnLokaal = findViewById(R.id.btn_melding_lokaalnr);

        lokaalButtonsOpvullen();
        buttonsAddClickEvents();
    }


    private void buttonsAddClickEvents() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakePhoto(view);
            }
        });

        final Activity activity = this;
        btnCampus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Campussen.class);
                intent.putExtra("campus_id", s_campus);
                startActivity(intent);
            }
        });
        btnVerdiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,Verdiepingen.class);
                intent.putExtra("verdieping_id", s_verdieping);
                startActivity(intent);
            }
        });
        btnLokaal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Lokalen.class);
                intent.putExtra("lokaal_id", s_lokaal);
                startActivity(intent);
            }
        });
    }

    private void lokaalButtonsOpvullen() {
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            String j = (String) b.get("lokaal_info");
            Log.d("Test", j);

            try {
                j = j.replace(" ", "");
                j = j.replace(".", "");
            } catch (NullPointerException e) {
                Log.e("Error",e.toString());
            }

            try {
                if (j == null) throw new AssertionError();
                s_campus = j.substring(0,3);
                j = j.substring(3, j.length());
                s_lokaal = j.substring(j.length()-3,j.length());
                j = j.substring(0,j.length()-3);
                s_verdieping = j;
                btnCampus.setText(s_campus);
                btnVerdiep.setText(s_verdieping);
                btnLokaal.setText(s_lokaal);
            } catch (StringIndexOutOfBoundsException e) {
                Log.e("Error",e.toString());
                Intent intent = new Intent(this, Overzicht.class);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File imgFile = new  File(mCurrentPhotoPath);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                imageView.setImageBitmap(myBitmap);
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
