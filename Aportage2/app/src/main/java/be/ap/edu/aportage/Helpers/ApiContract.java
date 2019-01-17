package be.ap.edu.aportage.helpers;

import android.util.Log;

public class ApiContract {
    public static String API_BASE_URL = "https://api.mlab.com/api/1";
    public static String API_KEY = "OlG7Ic3_9_iemwwMvmErBnkK-N0DrZs4";
    public static String DATABASE = "campusdb";
    public static String CAMPUS_AFK = "campusafk";
    public static String VERDIEP_NR = "verdiepnr";
    public static String LOKAAL_NR = "lokaalnr";
    public static String CAMPUS_NAAM = "campusnaam";
    public static String MELDER_ID = "melderid";


    public static String MELDING_ID = "_id";


    public static String createCollectionUrlMetApi(be.ap.edu.aportage.helpers.MongoCollections coll){


        //https://api.mlab.com/api/1/databases/my-db/collections/my-coll?apiKey=myAPIKey
        String collectionUrl = ApiContract.API_BASE_URL+"/databases/"+ApiContract.DATABASE+"/collections/"+coll.toString()+"?apiKey="+ ApiContract.API_KEY;
        Log.d("ApiContract createCollUrl", collectionUrl);
        return collectionUrl;
    }

    public static String createCollectionUrl(MongoCollections coll){
        return ApiContract.API_BASE_URL+"/databases/"+ApiContract.DATABASE+"/collections/"+coll.toString()+"/";
    }

    public static String createMeldingenQueryUrl(String campus, String verdiep, String lok ){
        //https://api.mlab.com/api/1/databases/my-db/collections/my-coll?q={"active": true}&apiKey=myAPIKey

        String meldingenUrl = ApiContract.API_BASE_URL+"/databases/"+ApiContract.DATABASE+"/collections/"+ MongoCollections.MELDINGEN;
        String meldingenQueryUrl = meldingenUrl+createQueryUrl(campus, verdiep, lok)+"&apiKey="+ ApiContract.API_KEY;
        Log.d("ApiContract queryUrl", meldingenQueryUrl);
        return meldingenQueryUrl;

    }

    public static String createQueryUrl(String campus, String verdiep, String lok) {
        //q={"active": true}
        String query = "?q={\""+CAMPUS_AFK+"\":\""+campus.toUpperCase()+"\",\""+VERDIEP_NR+"\":\""+verdiep.toLowerCase()+"\",\""+LOKAAL_NR+"\":\""+lok.toLowerCase()+"\"}";

        Log.d("ApiContract", query);

        return query;

    }

    public static String createMeldingIDQueryUrl(String meldingid){
        //https://api.mlab.com/api/1/databases/my-db/collections/my-coll/4e7315a65e4ce91f885b7dde?apiKey=myAPIKey
        String meldingenUrl = ApiContract.API_BASE_URL+"/databases/"+ApiContract.DATABASE+"/collections/"+ MongoCollections.MELDINGEN+"/";
        String meldingenQueryUrl = meldingenUrl+meldingid+"?apiKey="+ ApiContract.API_KEY;
        Log.d("ApiContract melding id url", meldingenQueryUrl);
        return meldingenQueryUrl;
    }

   public static String createMeldingIdQuery(String meldingid){
        String q = "?q={\""+MELDING_ID+"\":\""+meldingid+"\"}";

       return q;
   }

    public static String createLokaalQuery(String campus, String verdiep, String lok) {
        String query = createQueryUrl(campus, verdiep, lok);
        String lokalenUrl = ApiContract.API_BASE_URL+"/databases/"+ApiContract.DATABASE+"/collections/"+ MongoCollections.LOKALEN + query;
        Log.d("ApiContract query",lokalenUrl );
        return lokalenUrl;

    }


    public static String createUrlMetMelderIdQuery(String objectID){
        //todo_one: melding object id query afmaken
        String query = "?q={\""+MELDER_ID+"\":\""+objectID+"\"}";
        String url = ApiContract.API_BASE_URL+"/databases/"+ApiContract.DATABASE+"/collections/"+ MongoCollections.GEBRUIKERS + query +"&apiKey="+ ApiContract.API_KEY;;
        Log.d("ApiContract url", url);
        return url;
    }

    public static String createUrlMetObjectIdQuery(String objectID){

        String query = "?q={\""+MELDER_ID+"\":\""+objectID+"\"}";
        String url = ApiContract.API_BASE_URL+"/databases/"+ApiContract.DATABASE+"/collections/"+ MongoCollections.MELDINGEN + query +"&apiKey="+ ApiContract.API_KEY;;
        Log.d("ApiContract url", url);
        return url;
    }


    public static String createUrlMetMelderId(String mlabId){
        //https://api.mlab.com/api/1/databases/my-db/collections/my-coll/4e7315a65e4ce91f885b7dde?apiKey=myAPIKey
        String url = ApiContract.API_BASE_URL+"/databases/"+ApiContract.DATABASE+"/collections/"+ MongoCollections.GEBRUIKERS + "/"+mlabId +"?apiKey="+ ApiContract.API_KEY;
        return url;
    }




}
