package be.ap.edu.aportage.models;


import android.util.Log;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockDataManager {

    private static MockDataManager instance = null;
    private static List<Campus> campussenLijst;
    private static List<Melding> meldingenLijst;

    public static MockDataManager getInstance () {
        if(instance!= null) {
            return instance;
        } else {
            instance = new MockDataManager();
            setMeldingenLijst();
            setCampussenLijst();
            return instance;
        }

    }

    private static void setCampussenLijst() {
        MockDataManager.campussenLijst = new ArrayList<>();
        List<Verdiep> verdiepList = new ArrayList<>();
        verdiepList.add(new Verdiep(-1, new int[]{0,1,2,3,4,5,6,7,8,9,10 }));
        verdiepList.add(new Verdiep(1, new int[]{0,1,2,3,4,5,6,7,8,9,10 }));
        verdiepList.add(new Verdiep(2, new int[]{0,1,2,3,4,5,6,7,8,9,10 }));
        verdiepList.add(new Verdiep(3, new int[]{0,1,2,3,4,5,6,7,8,9,10 }));
        MockDataManager.campussenLijst.add(new Campus("Ellerman", "ELL", verdiepList));
        MockDataManager.campussenLijst.add(new Campus("Noorderplaats", "NOO", verdiepList));
    }

    private static void setMeldingenLijst(){
        MockDataManager.meldingenLijst = new ArrayList<>();
        MockDataManager.meldingenLijst.add(new Melding("MockMelding", "Blablablablabla", "testtest", "behandeling"));
        MockDataManager.meldingenLijst.add(new Melding("MockMelding2", "Blablablablabla2", "testtest2", "behandeling"));
    }

    public static List<Melding> getMeldingenLijst() {

        return MockDataManager.meldingenLijst;
    }

    public static List<Campus> getCampussenLijst() {
        return MockDataManager.campussenLijst;
    }

    public static List<Verdiep> getVerdiepenLijst(String afk) {

        List<Verdiep> list = new ArrayList<>();
        for(int i = 0; i < MockDataManager.campussenLijst.size(); i++){
            if(afk.equals(campussenLijst.get(i).afkorting)){
               list =  campussenLijst.get(i).verdiepingen;
            }
        }

        return list;


    }

    public static int[] getLokalenLijst(String afk, int verdiep){
        List<Verdiep> list = new ArrayList<>();
        int[] lokalen = null;

        for(int i = 0; i < MockDataManager.campussenLijst.size(); i++){
            if(afk.equals(campussenLijst.get(i).afkorting)){
                list =  campussenLijst.get(i).verdiepingen;
            }
        }

        for (Verdiep v: list) {
            if(v.verdiepnr == verdiep) {
                Log.d("verdiepen foreach", "verdiepnr: "+verdiep + " gevonden!");
                lokalen = v.lokalen;
            }
        }

        return lokalen;
    }


}
