package be.ap.edu.aportage.managers;


import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.parse.ParseUser;

import org.json.JSONObject;

import be.ap.edu.aportage.helpers.ApiContract;
import be.ap.edu.aportage.helpers.MongoCollections;
import be.ap.edu.aportage.interfaces.IUserCallback;
import be.ap.edu.aportage.interfaces.IVolleyCallback;
import be.ap.edu.aportage.models.Melder;

public class MyUserManager extends Application {

    //todo: MyUserManager in MyDataManager integreren, enkel het bijhouden van objectid is belangrijk

    protected static MyUserManager mInstance = null;
    private static Context mContext;


    private RequestQueue mRequestQueue;

    private MyUserManager(Context ctx) {

        this.mContext = ctx;

        this.mRequestQueue = getRequestQueue();

    }


    public static synchronized MyUserManager getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new MyUserManager(ctx);
        }

        return mInstance;
    }

    public JsonObjectRequest checkOfMelderBestaat(Melder melder, IUserCallback callback) {

        //https://api.mlab.com/api/1/databases/my-db/collections/my-coll?q={"active": true}&fo=true&apiKey=myAPIKey

        //todo: query meegeven met objectid van ParseUser
        String url = ApiContract.createCollectionUrl(MongoCollections.MELDINGEN); // ->
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, url ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("melder req", response.toString());
                callback.success(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                callback.gefaald();
            }
        });

        return jsonReq;


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

    public void meldGebruikerAan(){

    }


}
