package be.ap.edu.aportage.interfaces;

import org.json.JSONArray;

public interface IVolleyCallback {

    void onSuccess(JSONArray data);
    void onCustomSuccess(JSONArray data);
}
