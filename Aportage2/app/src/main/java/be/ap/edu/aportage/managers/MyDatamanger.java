package be.ap.edu.aportage.managers;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.ap.edu.aportage.activities.ScanMelding;
import be.ap.edu.aportage.interfaces.ApiContract;
import be.ap.edu.aportage.interfaces.IVolleyCallback;
import be.ap.edu.aportage.interfaces.Statussen;
import be.ap.edu.aportage.models.Campus;
import be.ap.edu.aportage.models.Lokaal;
import be.ap.edu.aportage.models.Melding;
import be.ap.edu.aportage.interfaces.MongoCollections;
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
            mContext = ctx;
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
        //todo: array meegegven ipv van obj
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

    public JsonObjectRequest createPostRequest(MongoCollections collection, Melding melding, IVolleyCallback callback) {
        JSONObject meldingObject = new JSONObject();
        JsonObjectRequest jsonArrayR = null;
        try {

            meldingObject.put("titel",melding.titel);
            meldingObject.put("omschrijving", melding.omschrijving);
            meldingObject.put("datum", melding.datum.toString());
            meldingObject.put("melderid", melding.melder.getID());
            meldingObject.put(ApiContract.CAMPUS_AFK, melding.locatie[0].toString());
            meldingObject.put(ApiContract.VERDIEP_NR, melding.locatie[1].toString());
            meldingObject.put(ApiContract.LOKAAL_NR, melding.locatie[2].toString());
            meldingObject.put("status", melding.status.toString());


            jsonArrayR = new JsonObjectRequest(
                    Request.Method.POST,
                    ApiContract.createCollectionUrl(collection),
                    meldingObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("post req", response.toString());

                            callback.onPostSuccess(response);
                        }

                    },new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        //throw new JSONException("er is iets misgelopen tijdens het posten van de melding");
                            Log.e("volleyerror", error.getMessage());

                    }
            });



        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonArrayR;
    }

    public void checkLokaalExists(String afk, String verdiep, String lokaal){
        String url = ApiContract.createLokaalQuery(afk, verdiep, lokaal);
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
                    Integer.parseInt(obj.get(ApiContract.LOKAAL_NR).toString())
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
                   obj.get("datum").toString());
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

}
