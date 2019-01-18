package be.ap.edu.aportage.helpers;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import be.ap.edu.aportage.R;

public class StatusKleurBepaler {

    public static int getKleur(Statussen status, Context context) {
        Integer bgcolor = null;
        switch (status) {
            case BEHANDELING:
                bgcolor = ContextCompat.getColor(context, R.color.status_behandeling);
                break;
            case ONTVANGEN:
                bgcolor = ContextCompat.getColor(context, R.color.status_ontvangen);
                break;
            case AFGEHANDELD:
                bgcolor = ContextCompat.getColor(context, R.color.status_afgehandeld);
                break;
            default:
                bgcolor = ContextCompat.getColor(context, R.color.status_ontvangen);
                break;
        }
        return bgcolor;
    }
}
