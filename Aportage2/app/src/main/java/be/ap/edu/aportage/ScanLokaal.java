package be.ap.edu.aportage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class ScanLokaal extends AppCompatActivity  implements SurfaceHolder.Callback, Detector.Processor  {

    private String LOG_TAG = this.getClass().toString();
    private SurfaceView cameraView;
    private TextView txtView;
    private CameraSource cameraSource;
    private Button btn_ok;
    private Button btn_annuleren;
    private String[] gelezenTekst;

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_for_food);
        cameraView = (SurfaceView) findViewById(R.id.surface_view);
        txtView = (TextView) findViewById(R.id.txtview);

        TextRecognizer txtRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!txtRecognizer.isOperational()) {
            Log.e("ScanLokaal", "Detector dependencies are not yet available");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), txtRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(this);  //getcontext shit gebruiken in de plaat
            txtRecognizer.setProcessor(this);
        }

        btn_ok = findViewById(R.id.btn_ocr_ok);
        btn_annuleren = findViewById(R.id.btn_ocr_annuleer);
        final Activity activity = this;
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lokaalInfo = gelezenTekst[gelezenTekst.length-1].toUpperCase();
                maakAparteExtras(lokaalInfo);
                Log.d("testLokaalInfo", lokaalInfo);
                if (!lokaalInfo.equals("")) {
                    lokaalInfo = lokaalInfo.replace("LOKAAL ", "");
                    lokaalInfo = lokaalInfo.replace(",", ".");

                    if (checkLokaal(lokaalInfo)) {
                        Intent intent = new Intent(activity, Meldingen.class);
                        intent.putExtra("lokaalInfo", lokaalInfo);
                        startActivity(intent);
                    }
                }
            }
        });
        btn_annuleren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, Overzicht.class);
                startActivity(intent);
            }
        });
    }


    private Boolean checkLokaal(String lokaal_s) {
        //todo Check of lokaal wel in database zit
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        try {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},1);
                return;
            }
            cameraSource.start(cameraView.getHolder());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        cameraSource.stop();
    }

    @Override
    public void release() {

    }

    @Override
    public void receiveDetections(Detector.Detections detections) {


        SparseArray items = detections.getDetectedItems();
        final StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < items.size(); i++)
        {
            TextBlock item = (TextBlock)items.valueAt(i);
            strBuilder.append(item.getValue());


        }
        Log.v("strBuilder.toString()", strBuilder.toString());
        registreerGelezenTekst(strBuilder.toString());

        txtView.post(new Runnable() {
            @Override
            public void run() {
                txtView.setText(strBuilder.toString());
                //txtView.setText(gelezenTekst.get(gelezenTekst.size()-1));
            }

        });
    }


    public void registreerGelezenTekst(String teSplittenString) {

        String delimiter = "/";
        gelezenTekst = teSplittenString.split(delimiter);

        Log.v("gelezen tekst", gelezenTekst[gelezenTekst.length-1]);

    }

    public void maakAparteExtras(String gelezenText){
        //todo: omzetten van lokaalinfo in 3 extras: afk, verdiepnr, lokaalnr
        //todo: als er geen correct lokaal kon worden gelezen nadat user op "doorsturen" klikt -> popup tonen en naar zoeken redirecten
        Log.d(LOG_TAG + "maakAparteExtras", gelezenText );
        try {
            gelezenText = gelezenText.replace("LOKAAL ", "");
            gelezenText = gelezenText.replace(",", ".");
            Log.d(LOG_TAG + "maakAparteExtras", gelezenText );

            gelezenText = gelezenText.replace("LOKAAL", "");
            gelezenText = gelezenText.replace(" ", "");
            gelezenText = gelezenText.replace(".", "");
            Log.d(LOG_TAG + "maakAparteExtras", gelezenText );
        } catch (Error e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }
}
