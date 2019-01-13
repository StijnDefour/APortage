package be.ap.edu.aportage.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import be.ap.edu.aportage.helpers.Statussen;

public class Melding {
    public String _id;
    public String titel;
    public String omschrijving;
    public String[] locatie;
    public Statussen status;
    public String imgUrl;

    public Date datum;
    public String datumString;

    public Melder melder;
    public String melderId;


//todo_done: api call naar 26 vs 24 rechtzetten om localdatetime te kunnen gebruiken, of een andere date lib gebruiken


    public Melding(){

    }

    public Melding(String t, String omschr, String[] loc, Statussen sts, Date d, String img) {
        this.titel = t;
        this.omschrijving = omschr;
        this.locatie = loc;
        this.status = sts;
        this.datum = d;
        this.imgUrl = img;
    }
    public Melding(String t, String omschr, String[] loc, Statussen sts, String d, String img) {
        this.titel = t;
        this.omschrijving = omschr;
        this.locatie = loc;
        this.status = sts;
        //this.setDate(d);
        this.datumString = d;
        this.imgUrl = img;
    }

    public int getKleurInt() {
        //todo: switch om status tot kleur int
        int kleur = 0;

        return kleur;
    }



    public void setId(String id){
        this._id = id;
    }

    public String get_id(){
        return _id;
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
            this.datumString = d;
        }
    }


    public String getMelderId() {
        return melderId;
    }

    public void setMelderId(String melderId) {
        this.melderId = melderId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

}
