package be.ap.edu.aportage.models;

import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import be.ap.edu.aportage.Lokalen;

public class DataManager {

    private DatabaseReference mDatabase;

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
        ArrayList<Integer> lokalenLijst;

        //json opschonen van tekens
        json = json.replaceAll("\\{|\\}|\\s", "");
        Log.d("Campus ", json);
        String[] input = json.split(",");

        //campus item opvullen
        for (String item: input) {
            String[] substring = item.split("=");
            switch (substring[0]) {
                case "Naam":
                    formatted.setNaam(substring[1]);
                    break;
                case "Afkorting":
                    formatted.setAfkorting(substring[1]);
                    break;
                default:

                    try {
                        // index verdiepingnr
                        int i = 0;

                        // kijken of string begint met verdiepingen
                        if ( substring[0].equals("Verdiepingen")) {
                            i = 1;
                        }

                        lokalenLijst = new ArrayList<Integer>();
                        Log.d("verdieping ", substring[i]);
                        Log.d("lokalen ", substring[i+2]);
                        String[] lokalen = substring[i+2].split(";");

                        for (String lokaal: lokalen) {
                            lokalenLijst.add(Integer.parseInt(lokaal));
                        }

                        Verdiep tmp = new Verdiep(Integer.parseInt(substring[i]) , lokalenLijst);

                        formatted.voegToeAanVerdiepingenlijst(tmp);

                    } catch (Exception e) {
                        Log.d("Error", e.toString());
                    }
                    break;
            }
        }

        return formatted;
    }

    private ArrayList<Campus> getCampusList() {
        mDatabase = FirebaseDatabase.getInstance().getReference("Campussen");

        /* Get data from database */
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> campusChildren = dataSnapshot.getChildren();

                for (DataSnapshot campus : campusChildren) {
                    //Log.d("Campus ", campus.getValue().toString());
                    campusList.add(JsonToCampus(campus.getValue().toString()));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });

        return campusList;
    }
}