package be.ap.edu.aportage.models;


import java.util.ArrayList;

class Verdiep {

    int verdiepnr;
    ArrayList<Integer> lokalen = new ArrayList<Integer>();

    public Verdiep(int nr, ArrayList<Integer> lokalen) {
        this.verdiepnr = nr;
        this.lokalen = lokalen;
    }
}
