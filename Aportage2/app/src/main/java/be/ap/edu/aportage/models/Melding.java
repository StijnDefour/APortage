package be.ap.edu.aportage.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Melding {
    int _id;
    String titel;
    String omschrijving;
    String locatie;
    String status;
    //Melder melder;
<<<<<<< HEAD
    //todo: api call naar 26 vs 24 rechtzetten om localdatetime te kunnen gebruiken, of een andere date lib gebruiken
    //LocalDateTime datum;

    public Melding(String t, String omschr, String loc, String sts) {

        this.titel = t;
        this.omschrijving = omschr;
        this.locatie = loc;
        this.status = sts;
        //this.datum = d;
    }
=======
    LocalDateTime datum;
>>>>>>> e88fd09f5822575a731a4beb7cbd4028f40e35fb
    public int getKleurInt() {
        //todo: switch om status tot kleur int
        int kleur = 0;

        return kleur;
    }
<<<<<<< HEAD


    public void setId(){
        //todo: id setten op basis van aantal items in db
        this._id = 1;
    }



=======
>>>>>>> e88fd09f5822575a731a4beb7cbd4028f40e35fb
}
