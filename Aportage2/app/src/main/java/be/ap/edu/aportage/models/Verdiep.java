package be.ap.edu.aportage.models;


import java.util.ArrayList;

public class Verdiep {

    public String campus_afk;
    public int verdiepnr;
    public int[] lokalen;


    public Verdiep(int verdiepnr, String campus_afk) {
        this.verdiepnr = verdiepnr;
        this.campus_afk = campus_afk;
    }
    public Verdiep(int verdiepnr, int[] lok) {
        this.verdiepnr = verdiepnr;
        this.lokalen = lok;
    }

    public String getVerdiepNaam(){
        return (this.verdiepnr<0?"-":"") + String.format("%02d", Math.abs(this.verdiepnr));
    }

}
