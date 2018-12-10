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
import be.ap.edu.aportage.models.Melding;
import be.ap.edu.aportage.models.Verdiep;

import static be.ap.edu.aportage.managers.ApiContract.MELDINGEN_COLL;

public class MyDatamanger {
    public static String TAG_DM = "MyDataManager ";
    protected static MyDatamanger mInstance = null;
    private static Context mContext;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private List<Melding> mMeldingen;



    private MyDatamanger(Context ctx){
        mContext = ctx;
        mRequestQueue = getRequestQueue();
        mMeldingen = new ArrayList<>();

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


    public static void setMeldingenLijst() {

        //todo: meldingen lijst parsen uit response van Volley


    }

    public JsonArrayRequest createRequest(String url, String collection) {
        JsonArrayRequest jsonArrayR = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG_DM, response.toString());
                        parseJsontoMeldingen(response);


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d(TAG_DM, "something went wrong");

                    }
                });

        return jsonArrayR;
    }

    public static void setCampussenLijst() {

    }


    public static void setVerdiepenLijst() {

    }


    public void setLokalenLijst() {

    }





    public List<Verdiep> getVerdiepenLijst(String afk) {
        return null;
    }


    public int[] getLokalenLijst(String afk, int verdiep) {
        return new int[0];
    }


    public List<Verdiep> getVerdiepLijst(int campusID) {
        return null;
    }

    public List<Campus> getCampussenLijst() {
        return null;
    }

    public List<Melding> getMeldingenLijst(){
        return null;
    }

    private void parseJsontoMeldingen(JSONArray elements){
        for (int i = 0; i < elements.length(); i++) {
            try {
                JSONObject obj = elements.getJSONObject(i);
                Log.d("JSONObject titel", obj.get("titel").toString());
                this.mMeldingen.add(createMeldingFromJson(obj));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private Melding createMeldingFromJson(JSONObject obj){

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
            return melding;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    private Campus createCampusFromJson(JSONObject obj){
        try {
            Campus campus = new Campus(
                    obj.get(ApiContract.CAMPUS_NAAM).toString(),
                    obj.get(ApiContract.CAMPUS_AFK).toString()
            );
            return campus;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Verdiep createVerdiepFromJson(JSONObject obj){
        try {
            Verdiep verdiep = new Campus(
                    obj.get().toString(),
                    obj.get().toString()
            );
            return verdiep;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }



}
