package be.ap.edu.aportage.managers;


import android.app.Application;
import android.content.Context;

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

    public boolean checkOfMelderBestaat(){

        return true;
    }

    public void registreerMelder(){

    }

}
