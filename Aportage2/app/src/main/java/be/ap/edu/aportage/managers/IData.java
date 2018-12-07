package be.ap.edu.aportage.managers;

import java.util.List;

import be.ap.edu.aportage.models.Campus;
import be.ap.edu.aportage.models.Verdiep;

public interface IData {

    void setMeldingenLijst();
    void setCampussenLijst();
    List<Verdiep> getVerdiepenLijst(String afk);
    int[] getLokalenLijst(String afk, int verdiep);
    List<Verdiep> getVerdiepLijst(int campusID);
    List<Campus> getCampussenLijst();

}
