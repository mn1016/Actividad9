package com.trabajo.activity_9;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URL;
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, IWorkerListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private static String OPEN_WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?id=3530597&&units=metric&appid=662b171652ad4dcedcbc4fa6f39d1577";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        try {
            mSwipeRefreshLayout.setRefreshing(true);
            new Worker(MainActivity.this).execute(new URL(OPEN_WEATHER_URL));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //Actividades
        //a) Crea una cuenta en OpenWeather y obten tu appid (API KEY)
        //Reemplaza tu API KEY en la URL OPEN_WEATHER_URL en este mismo archivo
        //b) Android permite mostrar imagenes en formato de vectores. En el foro hay una carpeta con los iconos correspondientes a cada estado del clima
        //proporcionado por OpenWeather. En la carpeta res->drawable se ha incluido una imagen de tipo vector (ic_w01d.xml)
        //Para convertir los archivos svg al formato de android, deberan archivo por archivo, dar click derecho en res->drawable->new->Vector asset.
        //En el cuadro de dialogo seleccionan asset type = local y luego eligen el archivo deseado.
        //El nombre del archivo debera ser "ic" + nombre del svg como se muestra en ic_w01d
        //Para ver el porque de estos nombre pueden consultar https://openweathermap.org/weather-conditions

        //c) Ejecuta la aplicacion
        //d) Que permiso se le tuvo que otorgar a la aplicacion en el archivo AndroidManifest.xml?
        //e) Para que sirve el control SwipeRefreshLayout, cuantos hijos puede tener?
        //f) Cuando se ejecuta el metodo onRefresh?
        //g) En que formato devuelve los datos OpenWeather?
        //h) Explica que ocurre en el metodo onNetworkSuccess
        //i) Anota tus respuestas y las capturas de pantalla en un documento en Word
        //j) Sube tu codigo al repositorio.
        //k) Sube el documento Word a la plataforma Moodle. Incluye la liga a tu repositorio


    }


    private void setDrawable(ImageView iv, String drawableName) {

        int drawable = getApplicationContext().getResources().getIdentifier(drawableName, "drawable", getPackageName());
        if (iv != null) {
            iv.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), drawable));
        }

    }


    @Override
    public void onRefresh() {
        try {
            new Worker(MainActivity.this).execute(new URL(OPEN_WEATHER_URL));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onNetworkSuccess(JSONObject jsonObject) {

        try {
            JSONArray weatherArray = jsonObject.getJSONArray("weather");
            JSONObject weather = weatherArray.getJSONObject(0);
            JSONObject main = jsonObject.getJSONObject("main");

            String temp = main.getString("temp");
            String icon = weather.getString("icon");
            setValues(icon, temp);


        } catch (Exception e) {
            e.printStackTrace();
        }

        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void setValues(String iconValue, String tempValue) {
        final TextView tv = findViewById(R.id.textView);

        String temp = String.format(getResources().getString(R.string.temp_text),tempValue);
        tv.setText(temp);

        ImageView iv = findViewById(R.id.appCompatImageView);
        setDrawable(iv, "ic_w01d");
    }
    @Override
    public void onNetworkError(String error) {
        mSwipeRefreshLayout.setRefreshing(false);
        Snackbar snackbar = Snackbar.make(mSwipeRefreshLayout, error, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
