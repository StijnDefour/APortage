package be.ap.edu.aportage.models;


public class Melder {
    public String naam;
    public String melderid;
    public boolean gdpr;
    public boolean facilitair;



    public String getID(){
        return this.melderid;
    }


    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getMelderid() {
        return melderid;
    }

    public void setMelderid(String melderid) {
        this.melderid = melderid;
    }

    public boolean isGdpr() {
        return gdpr;
    }

    public void setGdpr(boolean gdpr) {
        this.gdpr = gdpr;
    }

    public boolean isFacilitair() {
        return facilitair;
    }
    public void setFacilitair(boolean facilitair) {
        this.facilitair = facilitair;
    }

}
