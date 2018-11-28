package be.ap.edu.aportage.models;

import java.time.LocalDateTime;

public class Melding {
    int _id;
    String titel;
    String omschrijving;
    String locatie;
    String status;
    //Melder melder;
    LocalDateTime datum;
    public int getKleurInt() {
        //todo: switch om status tot kleur int
        int kleur = 0;

        return kleur;
    }
}
