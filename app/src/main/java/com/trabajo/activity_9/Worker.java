package com.trabajo.activity_9;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class Worker extends AsyncTask<URL, Void, NetworkResult> {

    private WeakReference<AppCompatActivity> activityWeakReference;

    public Worker(AppCompatActivity appCompatActivity) {
        this.activityWeakReference = new WeakReference<>(appCompatActivity);
    }
    @Override
    protected NetworkResult doInBackground(URL... urls) {
        HttpURLConnection urlConnection = null;
        NetworkResult networkResult = new NetworkResult();
        try {
            URL url = urls[0];
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            networkResult.setCode(urlConnection.getResponseCode());
            String line;
            StringBuilder sb = new StringBuilder();
            InputStream is;

            if (networkResult.getCode() == HttpURLConnection.HTTP_OK) {
                is = urlConnection.getInputStream();
            } else {
                is = urlConnection.getErrorStream();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String result = sb.toString();
            if (networkResult.getCode() == HttpURLConnection.HTTP_OK) {
                JSONObject jsonObject = new JSONObject(result);
                networkResult.setResult(jsonObject);

            }else{
                networkResult.setError(result);
            }
        } catch (Exception e) {
            networkResult.setError(e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return networkResult;
    }
    @Override
    protected void onPostExecute(NetworkResult networkResult) {
        //El metodo onPostExecute se ejecuta en el UI thread
        if (this.activityWeakReference.get() != null && networkResult != null) {
            AppCompatActivity appCompatActivity = this.activityWeakReference.get();
            if (appCompatActivity instanceof IWorkerListener) {
                IWorkerListener iWorkerListener = (IWorkerListener) appCompatActivity;
                if (networkResult.getResult() != null) {
                    iWorkerListener.onNetworkSuccess(networkResult.getResult());
                } else {
                    iWorkerListener.onNetworkError(networkResult.getError());
                }
            }
        }
    }
}

