package be.ap.edu.aportage.models;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Campus {
    public String naam;
    public String afkorting;
    public List<Verdiep> verdiepingen;


    public Campus(String n, String afkorting, List<Verdiep> verd) {
        this.naam = n;
        this.afkorting = afkorting;
        this.verdiepingen = verd;
    }

    public String getNaam() {
        return this.naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getAfkorting() {
        return this.afkorting;
    }

    public void setAfkorting(String afkorting) {
        this.afkorting = afkorting;
    }

    public List<Verdiep> getVerdiepingen() {
        return this.verdiepingen;
    }

    public void setVerdiepingen(List<Verdiep> verdiepingen) {
        this.verdiepingen = verdiepingen;
    }

    public void voegToeAanVerdiepingenlijst(Verdiep v) {
        this.verdiepingen.add(v);

    }
}
