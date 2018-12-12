package be.ap.edu.aportage.managers;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.ap.edu.aportage.interfaces.ApiContract;
import be.ap.edu.aportage.interfaces.IVolleyCallback;
import be.ap.edu.aportage.models.Campus;
import be.ap.edu.aportage.models.Lokaal;
import be.ap.edu.aportage.models.Melding;
import be.ap.edu.aportage.models.MongoCollections;
import be.ap.edu.aportage.models.Verdiep;

public class MyDatamanger extends Application {
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
        this.getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    private static void initialiseerData() {

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
                        // TODO: Handle error
                        Log.d(TAG_DM + collection, "something went wrong");

                    }
                });

        return jsonArrayR;
    }
    public JsonArrayRequest createPostRequest(String url, MongoCollections collection, RecyclerView.Adapter adapter, Melding melding) {
        JSONObject meldingObject = new JSONObject();
        meldingObject.put()
        JsonArrayRequest jsonArrayR = new JsonArrayRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG_DM, response.toString());
                        handleJsonResponse(response, collection, adapter);
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

    }

    private void createCampusAndAddToList(JSONObject obj,  RecyclerView.Adapter adapter) {
        try {
            Campus campus = new Campus(
                    obj.get(ApiContract.CAMPUS_NAAM).toString(),
                    obj.get(ApiContract.CAMPUS_AFK).toString()
            );
            this.mCampussen.add(campus);
            adapter.notifyDataSetChanged();

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
            adapter.notifyDataSetChanged();
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
