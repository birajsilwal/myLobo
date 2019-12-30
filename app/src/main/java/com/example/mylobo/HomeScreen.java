package com.example.mylobo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mylobo.Lobochat.ChatActivity;
import com.example.mylobo.Marketplace.Marketplace;
import com.example.mylobo.MenuActivity.MenuActivityHomescreen;
import com.example.mylobo.myBooks.myBooks;
import com.example.mylobo.myLobos.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeScreen extends AppCompatActivity {

    Button btnMarketplace, btnMyLobos, btnMybooks, btnLobochat;
    ImageView ivMarketplace,ivMenuHomescreen;

    // For weather
    String CITY = "albuquerque,us";
    String API = "3fd75b530def3b2dd94a77f7aed0d0e3";

    TextView tvWeatherStatus, tvTemperature,tvDate;
    ImageView ivWeatherDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        btnMarketplace = findViewById(R.id.btnMarketplace);
        btnMybooks = findViewById(R.id.btnMyBooks);
        btnMyLobos = findViewById(R.id.btnMyLobos);
        ivMarketplace = findViewById(R.id.ivMarketplace);
        btnLobochat = findViewById(R.id.btnLobochat);
        ivMenuHomescreen = findViewById(R.id.ivMenuHomescreen);

        // for weather
        tvWeatherStatus = findViewById(R.id.tvWeatherStatus);
        tvTemperature = findViewById(R.id.tvTemperature);
        ivWeatherDescription = findViewById(R.id.ivWeatherDescription);
        tvDate = findViewById(R.id.tvDate);

        // for date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMM d");
        String date = simpleDateFormat.format(calendar.getTime());
        tvDate.setText(date);

        new weather().execute();

        // takes to menu
        //TODO:complete this one
        ivMenuHomescreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, MenuActivityHomescreen.class);
                startActivity(intent);
            }
        });

        // takes to mylobos
        btnMyLobos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // takes to Marketplace
        ivMarketplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, Marketplace.class);
                startActivity(intent);
            }
        });

        // takes to Marketplace
        btnMarketplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, Marketplace.class);
                startActivity(intent);
            }
        });

        // takes to myBooks
        btnMybooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, myBooks.class);
                startActivity(intent);
            }
        });

        btnLobochat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    class weather extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=imperial&appid=" + API);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                String temp = main.getString("temp_max");
                int i = Math.round(Float.parseFloat(temp));
                String new_temp = i + "°F";

                String weatherDescription = weather.getString("description");

                String icon = weather.getString("icon");
                Log.i("biraj", icon);

                /* Populating extracted data into our views */
                tvWeatherStatus.setText(weatherDescription);
                tvTemperature.setText(new_temp);

                switch (icon) {
                    case "01n":
                        ivWeatherDescription.setBackgroundResource(R.drawable.clear_sky_night);
                        break;
                    case "02n":
                        ivWeatherDescription.setBackgroundResource(R.drawable.few_clouds_night);
                        break;
                    case "03n":
                        ivWeatherDescription.setBackgroundResource(R.drawable.scattered_clouds_night);
                        break;
                    case "04n":
                        ivWeatherDescription.setBackgroundResource(R.drawable.broken_cloud_night);
                        break;
                    case "09n":
                        ivWeatherDescription.setBackgroundResource(R.drawable.shower_rain_night);
                        break;
                    case "10n":
                        ivWeatherDescription.setBackgroundResource(R.drawable.rain_night);
                        break;
                    case "11n":
                        ivWeatherDescription.setBackgroundResource(R.drawable.thunderstorm_night);
                        break;
                    case "13n":
                        ivWeatherDescription.setBackgroundResource(R.drawable.snow_night);
                        break;
                }

                /* Views populated, Hiding the loader, Showing the main design */
                findViewById(R.id.HomeScreenMain).setVisibility(View.VISIBLE);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}