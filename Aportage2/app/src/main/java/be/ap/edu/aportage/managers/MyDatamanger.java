package be.ap.edu.aportage.managers;

import com.google.android.gms.common.api.Api;

import java.util.List;

import be.ap.edu.aportage.models.Campus;
import be.ap.edu.aportage.models.Verdiep;

public class MyDatamanger implements IData {
    private MyDatamanger mInstance = null;

    public MyDatamanger getInstance(){
        if(this.mInstance != null)
            return this.mInstance;
        else {
            this.mInstance = new MyDatamanger();
            initialiseerData();
            return this.mInstance;
        }
    }

    private void initialiseerData() {
        this.setCampussenLijst();
        this.setMeldingenLijst();
    }

    @Override
    public void setMeldingenLijst() {

    }

    @Override
    public void setCampussenLijst() {

    }

    @Override
    public void setVerdiepenLijst() {

    }

    @Override
    public void setLokalenLijst() {

    }


    @Override
    public List<Verdiep> getVerdiepenLijst(String afk) {
        return null;
    }

    @Override
    public int[] getLokalenLijst(String afk, int verdiep) {
        return new int[0];
    }

    @Override
    public List<Verdiep> getVerdiepLijst(int campusID) {
        return null;
    }

    @Override
    public List<Campus> getCampussenLijst() {
        return null;
    }

    private String createURL(String coll){
        //https://api.mlab.com/api/1/databases/my-db/collections/my-coll?apiKey=myAPIKey
        return "https://api.mlab.com/api/1/databases/"+ApiContract.DATABASE+"/collections/"+coll+"?apiKey="+ ApiContract.API_KEY;
    }
}
