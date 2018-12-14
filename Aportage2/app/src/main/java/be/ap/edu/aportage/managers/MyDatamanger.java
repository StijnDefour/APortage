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
        initListsCampusVerdiepLokaal();

        Log.v(TAG_DM, "initialised!");
    }

    public static synchronized MyDatamanger getInstance(Context ctx){

        if(mInstance == null){
            mInstance = new MyDatamanger(ctx);
            mContext = ctx;
        }
        return mInstance;
    }

    private void initListsCampusVerdiepLokaal(){
        //dit wordt eenmalig uitgevoerd als de instantie van MyDataManager nog niet bestaat.
        //Deze data is statisch dus hoeft maar eenmaal binnengehaald te worden.
        JsonArrayRequest campusReq = this.createGetRequest(ApiContract.createCollectionUrl(MongoCollections.CAMPUSSEN ), MongoCollections.CAMPUSSEN, null);
        JsonArrayRequest verdiepReq = this.createGetRequest(ApiContract.createCollectionUrl(MongoCollections.VERDIEPEN ), MongoCollections.VERDIEPEN, null);
        JsonArrayRequest lokaalReq = this.createGetRequest(ApiContract.createCollectionUrl(MongoCollections.LOKALEN), MongoCollections.LOKALEN, null);
        //JsonArrayRequest meldingenReq = this.dataManager.createGetRequest(ApiContract.createCollectionUrl(MongoCollections.MELDINGEN), MongoCollections.MELDINGEN, null);


        this.addToRequestQueue(campusReq);
        this.addToRequestQueue(verdiepReq);
        this.addToRequestQueue(lokaalReq);
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

    public JsonArrayRequest createGetRequest(String url, MongoCollections collection, RecyclerView.Adapter adapter) {
        JsonArrayRequest jsonArrayR = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG_DM, response.toString());
                        handleJsonResponse(response, collection, adapter);
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
    public JsonObjectRequest createPostRequest(MongoCollections collection, Melding melding) {
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



    public List<Verdiep> getVerdiepenLijst(String afk) {
        List<Verdiep> temp = new ArrayList<>();
        for (Verdiep v: this.mVerdiepen) {
            if(afk.equals(v.campus_afk))
                temp.add(v);
        }
        return temp;
    }


    public List<Lokaal> getLokalenLijst(String afk, int verdiep) {
        List<Lokaal> temp = new ArrayList<>();
        for (Lokaal l: this.mLokalen) {
            if(afk.toUpperCase().equals(l.mAfk.toUpperCase()) && verdiep ==l.mVerdiep)
                temp.add(l);
        }
        return temp;
    }



    public List<Campus> getCampussenLijst() {
        return this.mCampussen;
    }

    public List<Melding> getMeldingenLijst(String afk, String verdiep, String lokaal, RecyclerView.Adapter adapter){

        String url = ApiContract.createMeldingenQueryUrl(afk, verdiep, lokaal);
        JsonArrayRequest req = createGetRequest(url, MongoCollections.MELDINGEN, adapter);
        this.addToRequestQueue(req);

        return this.mMeldingen;
    }

    private void parseToCorrectList(JSONObject obj, MongoCollections coll,  RecyclerView.Adapter adapter) throws JSONException {
        switch(coll){

            case CAMPUSSEN :  createCampusAndAddToList(obj, adapter);
            break;
            case MELDINGEN: createMeldingAndAddToList(obj, adapter);
            break;
            case VERDIEPEN: createVerdiepAndAddToList(obj, adapter);
            break;
            case LOKALEN: createLokaalAndAddToList(obj, adapter);
            default: throw new JSONException("couldn't create object from json");
        }


    }

    private void createLokaalAndAddToList(JSONObject obj, RecyclerView.Adapter adapter) {

        try {
            Lokaal lokaal = new Lokaal(
                    obj.get(ApiContract.CAMPUS_AFK).toString(),
                    Integer.parseInt(obj.get(ApiContract.VERDIEP_NR).toString()),
                    Integer.parseInt(obj.get(ApiContract.LOKAAL_NR).toString())
            );
            this.mLokalen.add(lokaal);
            //adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    private void createCampusAndAddToList(JSONObject obj,  RecyclerView.Adapter adapter) {
        try {
            Campus campus = new Campus(
                    obj.get(ApiContract.CAMPUS_NAAM).toString(),
                    obj.get(ApiContract.CAMPUS_AFK).toString()
            );
            this.mCampussen.add(campus);
            //adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    private void handleJsonResponse(JSONArray elements, MongoCollections coll,  RecyclerView.Adapter adapter){
        for (int i = 0; i < elements.length(); i++) {
            try {
                JSONObject obj = elements.getJSONObject(i);
                this.parseToCorrectList(obj, coll , adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void createMeldingAndAddToList(JSONObject obj, RecyclerView.Adapter adapter){

        this.mMeldingen.clear();
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
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void createVerdiepAndAddToList(JSONObject obj, RecyclerView.Adapter adapter){
        try {
            Verdiep verdiep = new Verdiep(
                    Integer.parseInt(obj.get(ApiContract.VERDIEP_NR).toString()),
                    obj.get(ApiContract.CAMPUS_AFK).toString()
            );
            this.mVerdiepen.add(verdiep);
            //adapter.notifyDataSetChanged();
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

}
