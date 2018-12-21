package be.ap.edu.aportage.managers;


import android.app.Application;
import android.content.Context;

import be.ap.edu.aportage.interfaces.IVolleyCallback;
import be.ap.edu.aportage.models.Melder;

public class MyUserManager extends Application {


    protected static MyUserManager mInstance = null;
    private static Context mContext;
    private static Melder mMelder;

    private MyUserManager(Context ctx){

        this.mContext = ctx;


    }


    public static synchronized MyUserManager getInstance(Context ctx){
        if(mInstance == null){
            mInstance = new MyUserManager(ctx);
        }

        return mInstance;
    }

    public void meldMelderAan(){

    }

    public void checkOfMelderBestaat(Melder melder, IVolleyCallback callback){

        //https://api.mlab.com/api/1/databases/my-db/collections/my-coll?q={"active": true}&fo=true&apiKey=myAPIKey



    }

    public void registreerMelder(){

    }

    public void stuurBevestigingsMail(){

    }



}
