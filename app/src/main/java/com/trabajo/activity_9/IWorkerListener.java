package com.trabajo.activity_9;
import org.json.JSONObject;

public interface IWorkerListener {
    void onNetworkSuccess(JSONObject jsonObject);
    void onNetworkError(String error);
}
