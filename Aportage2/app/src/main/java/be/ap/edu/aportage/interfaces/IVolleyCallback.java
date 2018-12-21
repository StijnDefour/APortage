package be.ap.edu.aportage.interfaces;

import org.json.JSONObject;

public interface IVolleyCallback {

    void onCustomSuccess(Object data);

    void onPostSuccess(JSONObject response);

    void onFailure();
}
