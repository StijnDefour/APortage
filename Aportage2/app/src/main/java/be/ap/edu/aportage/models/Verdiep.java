package be.ap.edu.aportage.models;


import java.util.ArrayList;

class Verdiep {

    int verdiepnr;
<<<<<<< HEAD
    int[] lokalen;

    public Verdiep(int verdiepnr, int[] lok) {
        this.verdiepnr = verdiepnr;
        this.lokalen = lok;
    }


=======
    ArrayList<Integer> lokalen = new ArrayList<Integer>();
>>>>>>> e88fd09f5822575a731a4beb7cbd4028f40e35fb

    public Verdiep(int nr, ArrayList<Integer> lokalen) {
        this.verdiepnr = nr;
        this.lokalen = lokalen;
    }
}
