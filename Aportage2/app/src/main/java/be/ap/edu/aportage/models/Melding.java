package be.ap.edu.aportage.models;

import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Melding {
    int _id;
    String titel;
    String omschrijving;
    String locatie;
    String status;

    FirebaseUser melder;

    //todo: api call naar 26 vs 24 rechtzetten om localdatetime te kunnen gebruiken, of een andere date lib gebruiken
    //LocalDateTime datum;


    public Melding(String t, String omschr, String loc, String sts) {

        this.titel = t;
        this.omschrijving = omschr;
        this.locatie = loc;
        this.status = sts;
        //this.datum = d;
    }

    public int getKleurInt() {
        //todo: switch om status tot kleur int
        int kleur = 0;

        return kleur;
    }



    public void setId(int id){
        //todo: id setten op basis van aantal items in db
        this._id = id;
    }

    public void setMelder(FirebaseUser mldr){
        this.melder = mldr;
    }


}
