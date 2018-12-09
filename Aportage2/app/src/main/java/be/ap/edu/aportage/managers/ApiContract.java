package be.ap.edu.aportage.managers;

import android.util.Log;

public class ApiContract {
    public static String API_BASE_URL = "https://api.mlab.com/api/1";
    public static String API_KEY = "OlG7Ic3_9_iemwwMvmErBnkK-N0DrZs4";
    public static String DATABASE = "campusdb";
    public static String VERDIEP_COLL = "Verdiepen";
    public static String CAMPUS_COLL = "Campussen";
    public static String LOKALEN_COLL = "Lokalen";
    public static String MELDINGEN_COLL = "Meldingen";
    public static String USER = "lol";
    public static String PW = "lol1lol";

    public static String createCollectionUrl(String coll){
        //https://api.mlab.com/api/1/databases/my-db/collections/my-coll?apiKey=myAPIKey
        String collectionUrl = ApiContract.API_BASE_URL+"/databases/"+ApiContract.DATABASE+"/collections/"+coll+"?apiKey="+ ApiContract.API_KEY;
        return collectionUrl;
    }

    private String createMeldingenQueryUrl(String campus, String verdiep, String lok ){
        //https://api.mlab.com/api/1/databases/my-db/collections/my-coll?q={"active": true}&apiKey=myAPIKey

        String meldingenUrl = ApiContract.API_BASE_URL+"/databases/"+ApiContract.DATABASE+"/collections/"+ApiContract.MELDINGEN_COLL;
        String meldingenQueryUrl = meldingenUrl+createQueryUrl(campus, verdiep, lok)+"&apiKey="+ ApiContract.API_KEY;
        return meldingenQueryUrl;

    }

    public static String createQueryUrl(String campus, String verdiep, String lok) {
        //q={"active": true}
        String campusQ = "campusafk";
        String verdiepQ = "verdiepnr";
        String lokaalQ= "lokaalnr";
        String query = "?q={"+campusQ+":"+campus.toUpperCase()+","+verdiepQ+":"+verdiep.toLowerCase()+","+lokaalQ+":"+lok.toLowerCase()+"}";

        return query;

    }



}
