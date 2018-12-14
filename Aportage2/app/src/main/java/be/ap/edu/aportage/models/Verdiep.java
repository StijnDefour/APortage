package be.ap.edu.aportage.models;


import java.util.ArrayList;

public class Verdiep {

    public int verdiepnr;
    public int[] lokalen;


    public Verdiep(int verdiepnr, int[] lok) {
        this.verdiepnr = verdiepnr;
        this.lokalen = lok;
    }

    public String getVerdiepNaam(){
        return (this.verdiepnr<0?"-":"") + String.format("%02d", Math.abs(this.verdiepnr));
    }

}
