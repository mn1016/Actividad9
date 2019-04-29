package com.trabajo.activity_9;
import org.json.JSONObject;
public class NetworkResult {
    private int code;
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    private String error = null;
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public JSONObject getResult() {
        return result;
    }
    public void setResult(JSONObject result) {
        this.result = result;
    }
    private JSONObject result;
}