package be.ap.edu.aportage.interfaces;

import android.util.Log;

public class ApiContract {
    public static String API_BASE_URL = "https://api.mlab.com/api/1";
    public static String API_KEY = "OlG7Ic3_9_iemwwMvmErBnkK-N0DrZs4";
    public static String DATABASE = "campusdb";
    public static String CAMPUS_AFK = "campusafk";
    public static String VERDIEP_NR = "verdiepnr";
    public static String LOKAAL_NR = "lokaalnr";
    public static String CAMPUS_NAAM = "campusnaam";

    public static String createCollectionUrl(MongoCollections coll){
        //https://api.mlab.com/api/1/databases/my-db/collections/my-coll?apiKey=myAPIKey
        String collectionUrl = ApiContract.API_BASE_URL+"/databases/"+ApiContract.DATABASE+"/collections/"+coll.toString()+"?apiKey="+ ApiContract.API_KEY;
        Log.d("ApiContract createCollUrl", collectionUrl);
        return collectionUrl;
    }

    private String createMeldingenQueryUrl(String campus, String verdiep, String lok ){
        //https://api.mlab.com/api/1/databases/my-db/collections/my-coll?q={"active": true}&apiKey=myAPIKey

        String meldingenUrl = ApiContract.API_BASE_URL+"/databases/"+ApiContract.DATABASE+"/collections/"+ MongoCollections.MELDINGEN;
        String meldingenQueryUrl = meldingenUrl+createQueryUrl(campus, verdiep, lok)+"&apiKey="+ ApiContract.API_KEY;
        Log.d("ApiContract queryUrl", meldingenQueryUrl);
        return meldingenQueryUrl;

    }

    public static String createQueryUrl(String campus, String verdiep, String lok) {
        //q={"active": true}
        String query = "?q={"+CAMPUS_AFK+":"+campus.toUpperCase()+","+VERDIEP_NR+":"+verdiep.toLowerCase()+","+LOKAAL_NR+":"+lok.toLowerCase()+"}";

        Log.d("ApiContract", query);

        return query;

    }



}
