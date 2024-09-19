package com.example.pract_22_weather

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val key = "45e5ddee51fe35348d239a416d4a4703"
    private lateinit var cityEditT: EditText
    private lateinit var getWeatherB: Button
    private lateinit var weatherText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityEditT = findViewById(R.id.name)
        getWeatherB = findViewById(R.id.getWeather)
        weatherText = findViewById(R.id.dannie)

        getWeatherB.setOnClickListener {
            getResult(cityEditT.text.toString())
        }
    }

    fun getResult(city: String) {
        if (city.isNotEmpty()) {
                val url = ("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+key+"&units=metric&lang=ru").toString()
            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(
                Request.Method.GET,
                url,
                { response ->
                    try {
                        val obj = JSONObject(response)
                        val main = obj.getJSONObject("main")
                        val temperature = main.getString("temp")
                        val pressure = main.getString("pressure")
                        val wind = obj.getJSONObject("wind")
                        val windSpeed = wind.getString("speed")
                        val cityName = obj.getString("name")
                        weatherText.text = "Город: $cityName\nТемпература: $temperature°C\nДавление: $pressure мм рт.ст.\nСкорость ветра: $windSpeed м/с"

                    } catch (e: Exception) {
                        Log.d("MyLog", "JSON error: $e")
                    }
                },
                { error ->
                    Log.d("MyLog", "Volley error: $error")
                }
            )
            queue.add(stringRequest)
        } else {
            val sn = Snackbar.make(findViewById(android.R.id.content), "Пожалуйста, введите город", Snackbar.LENGTH_LONG)
            sn.setActionTextColor(Color.RED)
            sn.show()
        }
    }
}