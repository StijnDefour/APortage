package be.ap.edu.aportage.models;


import java.util.ArrayList;

class Verdiep {

    int verdiepnr;
    int[] lokalen;


    public Verdiep(int verdiepnr, int[] lok) {
        this.verdiepnr = verdiepnr;
        this.lokalen = lok;
    }

    public String getVerdiepNaam(){
        return String.format("%02d", verdiepnr);
    }

}
