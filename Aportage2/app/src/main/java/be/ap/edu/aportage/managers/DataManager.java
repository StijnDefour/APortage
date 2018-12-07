package be.ap.edu.aportage.managers;

import android.util.Log;

/* firebase imports dat niet werken bij mij/karima

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
*/

import java.util.ArrayList;

import be.ap.edu.aportage.models.Campus;
import be.ap.edu.aportage.models.Verdiep;

public class DataManager {

    //private DatabaseReference mDatabase;

    //test voor git ignore
    private static DataManager singleInstance = null;
    private static ArrayList<Campus> campusList = new ArrayList<Campus>();
    private static ArrayList<Verdiep> verdiepingList = new ArrayList<Verdiep>();

    public static DataManager getInstance(){
        if(singleInstance == null) {
            singleInstance = new DataManager();
            singleInstance.populateList();
        }
        return singleInstance;
    }

    private void populateList() {
        campusList = getCampusList();
    }

    public Campus JsonToCampus(String json) {
        Campus formatted = new Campus("", "", new ArrayList<Verdiep>());
        Log.d("Campus ", json);
        json = json.replaceAll("\\{", "");
        String[] input = json.split(",");

        for (String item: input) {
            String[] substring = item.split("=");


            Log.d("Campus ", substring[0]);

            switch (substring[0]) {
                case "Naam":
                    formatted.setNaam(substring[1]);
                    break;
                case "Afkorting":
                    formatted.setAfkorting(substring[1]);
                    break;
                case "Verdiepingen":
                    ArrayList<Integer> lokalenLijst = new ArrayList<Integer>();
                    Log.d("Campus ", substring[3]);
                    String[] lokalen = substring[3].split(";");


                    for (String lokaal: lokalen) {
                        lokalenLijst.add(Integer.parseInt(lokaal));
                    }

                    //tijdelijk lokalenLijst aangepast naar een int[]
                    Verdiep tmp = new Verdiep(Integer.parseInt(substring[0]) , new int[]{1, 2, 3, 4});

                    formatted.voegToeAanVerdiepingenlijst(tmp);
                    break;
                default:
                    break;
            }
        }


        return formatted;
    }

    private ArrayList<Campus> getCampusList() {
        /* todo: errorClusterfuck fixen voor firebase reference - OG code
        mDatabase = FirebaseDatabase.getInstance().getReference("Campussen");

        //Get data from database
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> campusChildren = dataSnapshot.getChildren();

                for (DataSnapshot campus : campusChildren) {
                    //Log.d("Campus ", campus.getValue().toString());
                    campusList.add(JsonToCampus(campus.getValue().toString()));

                    //for (Campus c: campusList) {
                    //    Log.d("Campus: ", c.getNaam());
                    //}

                    //Campus c = campus.getValue(Campus.class);
                    //Log.d("Campus: ", c.getNaam());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });


        return campusList;

        */
        //temp code:

        return new ArrayList<Campus>();
    }
}