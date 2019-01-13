package be.ap.edu.aportage.interfaces;

import org.json.JSONObject;

public interface IUserCallback {

    void success(JSONObject response);

    void gefaald();
}
