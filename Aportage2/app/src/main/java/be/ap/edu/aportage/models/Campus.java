package be.ap.edu.aportage.models;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Campus {
    String naam;
    String afkorting;
    List<Verdiep> verdiepingen = new ArrayList<Verdiep>();


    public Campus(String n, String afkorting, List<Verdiep> verd) {
        this.naam = n;
        this.afkorting = afkorting;
        this.verdiepingen = verd;
    }
}
