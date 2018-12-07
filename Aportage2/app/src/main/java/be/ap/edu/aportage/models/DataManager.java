package be.ap.edu.aportage.models;

import android.app.Activity;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataManager {

    private static DatabaseReference mDatabase;

    private static DataManager singleInstance = null;
    private static ArrayList<Campus> campusList = new ArrayList<Campus>();

    public static DataManager getInstance(){
        if(singleInstance == null) {
            mDatabase = FirebaseDatabase.getInstance().getReference("Campussen");
            singleInstance = new DataManager();
            singleInstance.populateList();
        }
        return singleInstance;
    }

    private void populateList() {
        fillCampusList();
    }

    private Campus JsonToCampus(String json) {
        Campus formatted = new Campus("", "", new ArrayList<Verdiep>());

        // json opschonen van tekens
        json = json.replaceAll("\\{|\\}|\\s", "");
        String[] input = json.split(",");

        // campus item opvullen
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
                        int[] lokalenLijst;
                        // index verdiepingnr
                        int i = 0;

                        // kijken of string begint met verdiepingen
                        if ( substring[0].equals("Verdiepingen")) {
                            i = 1;
                        }

                        String[] lokalen = substring[i+2].split(";");
                        lokalenLijst = new int[lokalen.length];

                        for (int teller = 0; teller < lokalen.length; teller++) {
                            lokalenLijst[teller] = (Integer.parseInt(lokalen[teller]));
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

    private void fillCampusList() {
        /* Get data from database */
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataManager dm = DataManager.getInstance();
                Iterable<DataSnapshot> campusChildren = dataSnapshot.getChildren();

                for (DataSnapshot campus : campusChildren) {
                    dm.addCampusList(JsonToCampus(campus.getValue().toString()));
                }

                for (Campus c: campusList) {
                    Log.d("test inner", c.getNaam());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });

        for (Campus c: campusList) {
            Log.d("test outside", c.getNaam());
        }
    }

    public static ArrayList<Campus> getCampusList() {
        return campusList;
    }

    public static void addCampusList(Campus c) {
        campusList.add(c);
    }
}