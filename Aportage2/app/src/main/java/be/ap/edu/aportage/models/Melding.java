package be.ap.edu.aportage.models;

import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.Date;
import java.util.Locale;

import be.ap.edu.aportage.interfaces.Statussen;

public class Melding {
    public int _id;
    public String titel;
    public String omschrijving;
    public String[] locatie;
    public Statussen status;

    public Date datum;

    public Melder melder;

    //todo_done: api call naar 26 vs 24 rechtzetten om localdatetime te kunnen gebruiken, of een andere date lib gebruiken

    public Melding(String t, String omschr, String[] loc, Statussen sts, Date d) {
        this.titel = t;
        this.omschrijving = omschr;
        this.locatie = loc;
        this.status = sts;
        this.datum = d;
    }
    public Melding(String t, String omschr, String[] loc, Statussen sts, String d) {
        this.titel = t;
        this.omschrijving = omschr;
        this.locatie = loc;
        this.status = sts;
        this.setDate(d);
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

    public void setMelder(Melder mldr){
        this.melder = mldr;
    }

    public void setDate(String d){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
        try {
            this.datum = sdf.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
