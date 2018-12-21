package be.ap.edu.aportage.managers;


import android.content.Context;

import be.ap.edu.aportage.models.Melder;

public class UserManager {


    protected static UserManager mInstance = null;
    private static Context mContext;
    private static Melder mMelder;
    private UserManager(Context ctx, Melder melder){

        this.mContext = ctx;
        this.mMelder = melder;

    }


    public synchronized UserManager getInstance(Context ctx, Melder melder){
        if(this.mInstance == null){
            this.mInstance = new UserManager(ctx, melder);
        }

        return this.mInstance;
    }

    public void meldMelderAan(){

    }

    public boolean checkOfMelderBestaat(){

        return true;
    }

    public void registreerMelder(){

    }

}
