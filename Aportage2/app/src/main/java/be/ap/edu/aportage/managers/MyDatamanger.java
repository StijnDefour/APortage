package be.ap.edu.aportage.managers;

import java.util.List;

import be.ap.edu.aportage.models.Campus;
import be.ap.edu.aportage.models.Verdiep;

public class MyDatamanger implements IData {
    private MyDatamanger instance = null;
    public MyDatamanger getInstance(){
        if(this.instance != null)
            return this.instance;
        else {
            this.instance = new MyDatamanger();
            initialiseerData();
            return this.instance;
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
}
