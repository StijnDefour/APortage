package be.ap.edu.aportage.models;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockDataManager {

    private static MockDataManager instance = null;

    public static MockDataManager getInstance () {
        if(instance!= null) {
            return instance;
        } else {
            instance = new MockDataManager();
            return instance;
        }

    }

    public static List<Melding> getMeldingenLijst() {
        List<Melding> list = new ArrayList<>();
        list.add(new Melding("MockMelding", "Blablablablabla", "testtest", "behandeling"));
        list.add(new Melding("MockMelding2", "Blablablablabla2", "testtest2", "behandeling"));
        return list;
    }

    public static List<Campus> getCampussenLijst() {
        List<Campus> list = new ArrayList<>();
        List<Verdiep> verdiepList = new ArrayList<>();
        verdiepList.add(new Verdiep(1, new int[]{ 0, 1,2,3,4,5,6,7,8,9,10 }));
        verdiepList.add(new Verdiep(2, new int[]{0, 1,2,3,4,5,6,7,8,9,10 }));
        verdiepList.add(new Verdiep(3, new int[]{0, 1,2,3,4,5,6,7,8,9,10 }));
        list.add(new Campus("Ellerman", "ELL", verdiepList));
        return list;
    }


}
