package be.ap.edu.aportage.models;


import android.support.annotation.NonNull;

import java.util.ArrayList;

public class Verdiep  implements Comparable<Verdiep>  {

    public String campus_afk;
    public int verdiepnr;

    public Verdiep(int verdiepnr, String campus_afk) {
        this.verdiepnr = verdiepnr;
        this.campus_afk = campus_afk;
    }

    public Verdiep(int verdiepnr) {
        this.verdiepnr = verdiepnr;
    }

    public String getVerdiepNaam(){
        return (this.verdiepnr<0?"-":"") + String.format("%02d", Math.abs(this.verdiepnr));
    }

    @Override
    public int compareTo(@NonNull Verdiep o) {
        return this.verdiepnr - o.verdiepnr;
    }
}
