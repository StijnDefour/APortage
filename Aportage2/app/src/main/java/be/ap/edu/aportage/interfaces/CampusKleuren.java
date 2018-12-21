package be.ap.edu.aportage.interfaces;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import be.ap.edu.aportage.R;

public class CampusKleuren {

    public int getCampusColor(String campus_afk, Context context) {
        Integer bgcolor;
        switch (campus_afk) {
            case "ell":
                bgcolor = ContextCompat.getColor(context, R.color.Ellerman);
                break;
            case "noo":
                bgcolor = ContextCompat.getColor(context, R.color.Noorderplaats);
                break;
            case "mei":
                bgcolor = ContextCompat.getColor(context, R.color.Meistraat);
                break;
            default:
                bgcolor = ContextCompat.getColor(context, R.color.Meistraat);
                break;
        }
        return bgcolor;
    }

    public int getVerdiepingColor(String campus_afk, Context context) {
        return createAlphaColor(campus_afk, context, 0.7);
    }

    public int getLokaalColor(String campus_afk, Context context) {
        return createAlphaColor(campus_afk, context, 0.4);
    }

    public int createAlphaColor(String campus_afk, Context context, double newAlpha) {
        Integer bgcolor;
        bgcolor = getCampusColor(campus_afk, context);


        int alpha = (int) (Color.alpha(bgcolor)*newAlpha);
        int red = Color.red(bgcolor);
        int green = Color.green(bgcolor);
        int blue = Color.blue(bgcolor);

        bgcolor = Color.argb(alpha, red, green, blue);

        return bgcolor;
    }
}
