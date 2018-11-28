package be.ap.edu.aportage.models;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Campus {
    String naam;
    String afkorting;
    List<Verdiep> verdiepingen = new ArrayList<Verdiep>();

    public Campus(String naam, String afkorting, List<Verdiep> verdiepingList) {
        this.naam = naam;
        this.afkorting = afkorting;
        this.verdiepingen = verdiepingList;
    }

<<<<<<< HEAD
    public Campus(String n, String afkorting, List<Verdiep> verd) {
        this.naam = n;
        this.afkorting = afkorting;
        this.verdiepingen = verd;
=======
    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getAfkorting() {
        return afkorting;
    }

    public void setAfkorting(String afkorting) {
        this.afkorting = afkorting;
    }

    public List<Verdiep> getVerdiepingen() {
        return verdiepingen;
    }

    public void setVerdiepingen(List<Verdiep> verdiepingen) {
        this.verdiepingen = verdiepingen;
    }

    public void voegToeAanVerdiepingenlijst(Verdiep v) {
        this.verdiepingen.add(v);
>>>>>>> e88fd09f5822575a731a4beb7cbd4028f40e35fb
    }
}
