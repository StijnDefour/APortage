package be.ap.edu.aportage.managers;

import android.util.Log;

import com.google.android.gms.common.api.Api;

import java.util.List;

import be.ap.edu.aportage.models.Campus;
import be.ap.edu.aportage.models.Verdiep;

public class MyDatamanger {
    public static String TAG_DM = "MyDataManager ";
    private static MyDatamanger mInstance = null;

    public static MyDatamanger getInstance(){
        if(mInstance != null)
            return mInstance;
        else {
            mInstance = new MyDatamanger();
            initialiseerData();
            return mInstance;
        }
    }

    private static void initialiseerData() {
        setCampussenLijst();
        setMeldingenLijst();
    }


    public static void setMeldingenLijst() {

    }


    public static void setCampussenLijst() {

    }


    public static void setVerdiepenLijst() {

    }


    public void setLokalenLijst() {

    }



    public List<Verdiep> getVerdiepenLijst(String afk) {
        return null;
    }


    public int[] getLokalenLijst(String afk, int verdiep) {
        return new int[0];
    }


    public List<Verdiep> getVerdiepLijst(int campusID) {
        return null;
    }

    public List<Campus> getCampussenLijst() {
        return null;
    }



}
