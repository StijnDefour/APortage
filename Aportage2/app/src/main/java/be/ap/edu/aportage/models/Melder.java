package be.ap.edu.aportage.models;

import com.parse.ParseUser;

public class Melder extends ParseUser {
    public String naam;

    public String melderid;
    public boolean gdpr;
    public boolean facilitair;

    public void setFacilitair(boolean facilitair) {
        this.facilitair = facilitair;
    }

    public void setGdpr(boolean gdpr) {
        this.gdpr = gdpr;
    }

    public String getID(){
        return this.melderid;
    }



}
