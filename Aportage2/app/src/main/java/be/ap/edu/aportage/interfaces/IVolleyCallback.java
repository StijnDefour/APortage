package be.ap.edu.aportage.interfaces;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IVolleyCallback {

    void onSuccess(Object data);
    void onCustomSuccess(Object data);

    void onPostSuccess(JSONObject response);
}
