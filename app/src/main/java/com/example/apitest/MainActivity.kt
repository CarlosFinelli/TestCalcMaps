package com.example.apitest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            val queue = Volley.newRequestQueue(this)
            val url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=-19.916171,-43.940554|-19.919080,-43.938790|-19.923271,-43.936154|-19.9259,-43.934&destinations=-19.916171,-43.940554|-19.919080,-43.938790|-19.923271,-43.936154|-19.9259,-43.9348&key=AIzaSyDvBHa5BiJtyx-bgBgB78c-ND6IpUx6TF0"
            val stringRequest = StringRequest(Request.Method.GET, url, { response ->
                val jsonResponse = JSONObject(response)
                val rows = jsonResponse.getJSONArray("rows")
                var distance = 0
                for (i in 0..rows.length() - 2) {
                    val element = rows.getJSONObject(i).getJSONArray("elements")
                    val distanceObject = element.getJSONObject(i + 1).getJSONObject("distance")
                    distance += distanceObject.getInt("value")
                }
                Toast.makeText(this, "Distancia total: $distance", Toast.LENGTH_SHORT).show()
            }, { error ->
                Log.e("Request_error: ", error.toString())
            })
            queue.add(stringRequest)
        }
    }
}