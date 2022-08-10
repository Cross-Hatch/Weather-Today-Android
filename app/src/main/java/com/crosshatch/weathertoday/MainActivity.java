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

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView cityName, temperature, description;
    private EditText search_text;
    private ImageView weather_icon;
    private Button button;
    private RequestQueue mQueue;

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
        String url = "https://api.openweathermap.org/data/2.5/weather?q=Toronto&units=metric&appid=68907bf609585ee2ef82afc4cdc53829";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Getting the array i want from the JSON
                    JSONObject jsonobj = response.getJSONObject("main");
                    //Choosing the index i want (in this case it's the temperature
//                    JSONObject temp = jsonobj.getJSONObject("temp");

                   double temp_string = jsonobj.getDouble("temp");
                    Log.d("went","button"+temp_string);
                    System.out.println(temp_string);

                    //temperature.append(temp_string);
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