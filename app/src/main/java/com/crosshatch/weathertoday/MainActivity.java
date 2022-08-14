package com.crosshatch.weathertoday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView cityName, temperature, description;
    private EditText search_text;
    private ImageView weather_icon;
    private Button button;
    private RequestQueue mQueue;
    private String APIKEY = "68907bf609585ee2ef82afc4cdc53829";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = findViewById(R.id.city);
        temperature = findViewById(R.id.temprature);
        description = findViewById(R.id.description_weather);
        search_text = findViewById(R.id.search_box);
        button = findViewById(R.id.button);
        weather_icon = findViewById(R.id.weather_icon);

        //Instance of a requestqueue
        mQueue = Volley.newRequestQueue(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse();
                Toast.makeText(MainActivity.this,"Clicked",Toast.LENGTH_SHORT).show();

            }
        });
    }
    //Setting our parse method
    private void jsonParse(){
        String url = "https://api.openweathermap.org/data/2.5/weather?q=Accra&appid="+APIKEY+"&units=metric";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Getting the array i want from the JSON
                    //For temperature
                    JSONObject jsonobj = response.getJSONObject("main");
                    int tempint = (int) jsonobj.getDouble("temp");
                    temperature.setText(tempint+"°");
                    //For Description
                    JSONArray weather = response.getJSONArray("weather");
                    JSONObject objwet = weather.getJSONObject(0);
                    String desc = objwet.getString("description");
                    description.setText(desc);
                    //For City name
                    //JSONArray cname = response.getJSONArray("name");
                    String name = (String) response.getString("name");
                    cityName.setText(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}