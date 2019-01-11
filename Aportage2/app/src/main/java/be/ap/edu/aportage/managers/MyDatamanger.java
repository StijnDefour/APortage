package be.ap.edu.aportage.managers;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.ap.edu.aportage.helpers.ApiContract;
import be.ap.edu.aportage.helpers.MongoCollections;
import be.ap.edu.aportage.helpers.Statussen;
import be.ap.edu.aportage.interfaces.IVolleyCallback;
import be.ap.edu.aportage.models.Campus;
import be.ap.edu.aportage.models.Lokaal;
import be.ap.edu.aportage.models.Melder;
import be.ap.edu.aportage.models.Melding;
import be.ap.edu.aportage.models.Verdiep;

public class MyDatamanger extends Application {
    public static String TAG_DM = "MyDataManager ";
    protected static MyDatamanger mInstance = null;
    private static Context mContext;

    private RequestQueue mRequestQueue;
    private List<Melding> mMeldingen;
    private List<Verdiep> mVerdiepen;
    private List<Campus> mCampussen;
    private List<Lokaal> mLokalen;



    private MyDatamanger(Context ctx){
        mContext = ctx;
        mRequestQueue = getRequestQueue();
        mMeldingen = new ArrayList<>();
        mVerdiepen = new ArrayList<>();
        mCampussen = new ArrayList<>();
        mLokalen = new ArrayList<>();
        Log.v(TAG_DM, "initialised!");
    }

    public static synchronized MyDatamanger getInstance(Context ctx){

        if(mInstance == null){
            mInstance = new MyDatamanger(ctx);

        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        this.getRequestQueue().add(req);
    }

    private void parseToCorrectList(JSONObject obj, MongoCollections coll,  IVolleyCallback callback) throws JSONException {
        switch(coll){
            case CAMPUSSEN :  createCampusAndAddToList(obj, callback);
                break;
            case MELDINGEN: createMeldingAndAddToList(obj, callback);
                break;
            case VERDIEPEN: createVerdiepAndAddToList(obj, callback);
                break;
            case LOKALEN: createLokaalAndAddToList(obj, callback);
            default: throw new JSONException("couldn't create object from json");
        }


    }



    public JsonArrayRequest createGetRequest(String url, MongoCollections collection, IVolleyCallback volleycallback) {


        JsonArrayRequest jsonArrayR = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG_DM, response.toString());
                        handleJsonResponse(response, collection, volleycallback);
                        //volleycallbackcallback.onCustomSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // done: Handle error
                        Log.e(TAG_DM + collection, "something went wrong");

                    }
                });

        return jsonArrayR;
    }

    public JsonObjectRequest postMelder(Melder melder){
        JSONObject melderObject = new JSONObject();
        JsonObjectRequest req = null;
        try {


            melderObject.put("naam", melder.getNaam());
            melderObject.put("melderid", melder.getID());
            melderObject.put("facilitair", melder.isFacilitair());
            melderObject.put("gdpr", melder.isGdpr());

            Log.d("postTest", "test json to array");
            req = new JsonObjectRequest(
                    Request.Method.POST,
                    ApiContract.createCollectionUrlMetApi(MongoCollections.GEBRUIKERS),
                    melderObject,
                    response -> {
                        Log.d("post req", response.toString());

                    }, error -> {
                //throw new JSONException("er is iets misgelopen tijdens het posten van de melder");
                Log.e("volleyerror", error.getMessage());

            });

            Log.d("postTest", "test json to array");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return req;
    }

    public JsonObjectRequest createPostRequest(MongoCollections collection, Melding melding, IVolleyCallback callback) {
        JSONObject meldingObject = new JSONObject();
        JsonObjectRequest jsonArrayR = null;
        try {

            meldingObject.put("titel",melding.titel);
            meldingObject.put("omschrijving", melding.omschrijving);
            meldingObject.put("datum", melding.datumString);
            meldingObject.put("melderid", melding.melderId);
            meldingObject.put(ApiContract.CAMPUS_AFK, melding.locatie[0]);
            meldingObject.put(ApiContract.VERDIEP_NR, melding.locatie[1]);
            meldingObject.put(ApiContract.LOKAAL_NR, melding.locatie[2]);
            meldingObject.put("status", melding.status.toString());
            meldingObject.put("imgUrl", melding.imgUrl);

            Log.d("postTest", "test json to array");
            jsonArrayR = new JsonObjectRequest(
                    Request.Method.POST,
                    ApiContract.createCollectionUrlMetApi(collection),
                    meldingObject,
                    response -> {
                        Log.d("post req", response.toString());

                        callback.onPostSuccess(response);
                    }, error -> {
                        //throw new JSONException("er is iets misgelopen tijdens het posten van de melding");
                        Log.e("volleyerror", error.getMessage());
                        callback.onFailure();

                    });

            Log.d("postTest", "test json to array");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonArrayR;
    }

    public void checkLokaalExists(String afk, String verdiep, String lokaal, IVolleyCallback callback){
        String url = ApiContract.createLokaalQuery(afk, verdiep, lokaal);


        JsonRequest jsonArrayR = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG_DM, response.toString());
                        callback.onCustomSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // done: Handle error
                        Log.e(TAG_DM, "fetching lokaal failed");
                        callback.onFailure();
                    }
                });




    }


    private void handleJsonResponse(JSONArray elements, MongoCollections coll,  IVolleyCallback callback){
        //todo: herschrijven dat dit hier de volledige jSOn array meegeeft aan parseToCorrList
        for (int i = 0; i < elements.length(); i++) {
            try {
                JSONObject obj = elements.getJSONObject(i);
                this.parseToCorrectList(obj, coll , callback);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void createLokaalAndAddToList(JSONObject obj, IVolleyCallback callback) {
        //todo: obj moet array zijn en hier alles laten loopen en toevoegen aan lists

        try {
            Lokaal lokaal = new Lokaal(

                    obj.get(ApiContract.CAMPUS_AFK).toString(),
                    Integer.parseInt(obj.get(ApiContract.VERDIEP_NR).toString()),
                    Integer.parseInt(obj.get(ApiContract.LOKAAL_NR).toString().replaceAll("\\D", ""))
            );
            this.mLokalen.add(lokaal);
            callback.onCustomSuccess(lokaal);


        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    private void createCampusAndAddToList(JSONObject obj, IVolleyCallback callback) {
        try {
            Campus campus = new Campus(
                    obj.get(ApiContract.CAMPUS_NAAM).toString(),
                    obj.get(ApiContract.CAMPUS_AFK).toString()
            );
            this.mCampussen.add(campus);
            callback.onCustomSuccess(campus);

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }


    private void createMeldingAndAddToList(JSONObject obj, IVolleyCallback callback){

        try {
            Melding melding = new Melding(
                    obj.get("titel").toString(),
                    obj.get("omschrijving").toString(),
                    new String[]{
                            obj.get("campusafk").toString(),
                            obj.get("verdiepnr").toString(),
                            obj.get("lokaalnr").toString()
                    },
                    Statussen.getStatus(obj.get("status").toString()),
                    obj.get("datum").toString(),
                    obj.get("imgUrl").toString());
            melding.setId((obj.getJSONObject("_id").get("$oid")).toString());
            this.mMeldingen.add(melding);
            callback.onCustomSuccess(melding);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void createVerdiepAndAddToList(JSONObject obj, IVolleyCallback callback){
        try {
            Verdiep verdiep = new Verdiep(
                    Integer.parseInt(obj.get(ApiContract.VERDIEP_NR).toString()),
                    obj.get(ApiContract.CAMPUS_AFK).toString()
            );
            this.mVerdiepen.add(verdiep);
            //adapter.notifyDataSetChanged();
            callback.onCustomSuccess(verdiep);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    public List<Melding> getMeldingenLijst(){

        return this.mMeldingen;
    }

    public List<Verdiep> getVerdiepenLijst(String afk) {
        List<Verdiep> temp = new ArrayList<>();
        for (Verdiep v: this.mVerdiepen) {
            if(afk.toUpperCase().equals(v.campus_afk.toUpperCase()))
                temp.add(v);
        }
        return temp;
    }


    public List<Lokaal> getLokalenLijst(String afk, int verdiep) {
        List<Lokaal> temp = new ArrayList<>();
        for (Lokaal l: this.mLokalen) {
            if(afk.toUpperCase().equals(l.mAfk.toUpperCase()) && verdiep == l.mVerdiep)
                temp.add(l);
        }
        return temp;
    }

    public List<Campus> getCampussenLijst() {
        return this.mCampussen;
    }


    public JsonArrayRequest getMelderMetMelderId(String objectID, IVolleyCallback callback){
        String url = ApiContract.createUrlMetMelderIdQuery(objectID);
        JsonArrayRequest jsonArrayR = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG_DM, response.toString());
                        try {
                            callback.onCustomSuccess(parseMelderJson(response.getJSONObject(0)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // done: Handle error
                        Log.e(TAG_DM, "something went wrong getting melder with id"+objectID);
                    }
                });

        return jsonArrayR;
    }

    public Melder parseMelderJson(JSONObject obj) {

        Melder melder = new Melder();


        try {
            melder.setNaam(obj.get("naam").toString());
            melder.setMelderid(obj.get("melderid").toString());
            melder.setGdpr(Boolean.parseBoolean(obj.get("gdpr").toString()));
            melder.setFacilitair(Boolean.parseBoolean(obj.get("facilitair").toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return melder;

    }

    public JsonArrayRequest getMeldingenLijstMetId(String objectId, IVolleyCallback callback) {
        //https://api.mlab.com/api/1/databases/my-db/collections/my-coll?q={"active": true}&apiKey=myAPIKey

        String url = ApiContract.createUrlMetObjectIdQuery(objectId);
        JsonArrayRequest jsonArrayR = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG_DM, response.toString());
                        callback.onCustomSuccess(response);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // done: Handle error
                        Log.e(TAG_DM, "something went wrong getting melding with id"+objectId);
                    }
                });

        return jsonArrayR;

    }

    public List<Melding> geefParsedMeldingen(JSONArray meldingenJSON){
        List<Melding> meldingenList = new ArrayList<>();
        for(int i = 0; i < meldingenJSON.length(); i++){
            try {
                JSONObject obj = meldingenJSON.getJSONObject(i);
                meldingenList.add(parseMeldingJson(obj));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return meldingenList;
    }

    public Melding parseMeldingJson(JSONObject obj) {
        Melding melding = null;
        try {
           melding = new Melding(
                    obj.get("titel").toString(),
                    obj.get("omschrijving").toString(),
                    new String[]{
                            obj.get("campusafk").toString(),
                            obj.get("verdiepnr").toString(),
                            obj.get("lokaalnr").toString()
                    },
                    Statussen.getStatus(obj.get("status").toString()),
                    obj.get("datum").toString(), //datum: "Thu Jan 10 18:05:05 GMT+01:00 2019" kan niet worden gelezen
                   obj.get("imgUrl").toString());
          melding.setId((obj.getJSONObject("_id").get("$oid")).toString());
          melding.setMelderId((obj.get("melderid").toString()));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return melding;
    }

    public JsonObjectRequest getMeldingMetId(String id, IVolleyCallback callback){
        //todo_done : maak request
        String url = ApiContract.createMeldingIDQueryUrl(id);
        JsonObjectRequest req = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG_DM, response.toString());
                        callback.onCustomSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // done: Handle error
                        Log.e(TAG_DM, "something went wrong getting melding with id"+id);
                    }
                });

        return req;
    }
}
