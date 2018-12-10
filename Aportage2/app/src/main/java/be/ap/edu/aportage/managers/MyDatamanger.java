package be.ap.edu.aportage.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.android.gms.common.api.Api;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import be.ap.edu.aportage.models.Campus;
import be.ap.edu.aportage.models.Lokaal;
import be.ap.edu.aportage.models.Melding;
import be.ap.edu.aportage.models.MongoCollections;
import be.ap.edu.aportage.models.Verdiep;


import static be.ap.edu.aportage.models.MongoCollections.CAMPUSSEN;

public class MyDatamanger {
    public static String TAG_DM = "MyDataManager ";
    protected static MyDatamanger mInstance = null;
    private static Context mContext;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
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
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    private static void initialiseerData() {

    }

    public JsonArrayRequest createRequest(String url, MongoCollections collection) {
        JsonArrayRequest jsonArrayR = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG_DM, response.toString());
                        handleJsonResponse(response, collection);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d(TAG_DM + collection, "something went wrong");

                    }
                });

        return jsonArrayR;
    }



    public List<Verdiep> getVerdiepenLijst(String afk) {
        List<Verdiep> temp = new ArrayList<>();
        for (Verdiep v: this.mVerdiepen) {
            if(v.campus_afk == afk)
                temp.add(v);
        }
        return temp;
    }


    public int[] getLokalenLijst(String afk, int verdiep) {
        return new int[0];
    }



    public List<Campus> getCampussenLijst() {
        return this.mCampussen;
    }

    public List<Melding> getMeldingenLijst(){
        return null;
    }

    private void parseToCorrectList(JSONObject obj, MongoCollections coll) throws JSONException {
        switch(coll){

            case CAMPUSSEN :  createCampusAndAddToList(obj);
            break;
            case MELDINGEN: createMeldingAndAddToList(obj);
            break;
            case VERDIEPEN: createVerdiepAndAddToList(obj);
            break;
            case LOKALEN: createLokaalAndAddToList(obj);
            default: throw new JSONException("couldn't create object from json");
        }
    }

    private void createLokaalAndAddToList(JSONObject obj) {
    }

    private void createCampusAndAddToList(JSONObject obj) {
        try {
            Campus campus = new Campus(
                    obj.get(ApiContract.CAMPUS_NAAM).toString(),
                    obj.get(ApiContract.CAMPUS_AFK).toString()
            );
            this.mCampussen.add(campus);

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    private void handleJsonResponse(JSONArray elements, MongoCollections coll){
        for (int i = 0; i < elements.length(); i++) {
            try {
                JSONObject obj = elements.getJSONObject(i);
                this.parseToCorrectList(obj, coll );

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void createMeldingAndAddToList(JSONObject obj){

        try {
            Melding melding = new Melding(
                    obj.get("titel").toString(),
                    obj.get("omschrijving").toString(),
                    new String[]{
                            obj.get("campusafk").toString(),
                            obj.get("verdiepnr").toString(),
                            obj.get("lokaalnr").toString()
                    },
                    obj.get("status").toString(),
                   obj.get("datum").toString());
            this.mMeldingen.add(melding);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void createVerdiepAndAddToList(JSONObject obj){
        try {
            Verdiep verdiep = new Verdiep(
                    Integer.parseInt(obj.get(ApiContract.VERDIEP_NR).toString()),
                    obj.get(ApiContract.CAMPUS_AFK).toString()
            );
            this.mVerdiepen.add(verdiep);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
