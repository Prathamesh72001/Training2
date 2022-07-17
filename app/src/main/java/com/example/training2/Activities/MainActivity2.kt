package com.example.training2.Activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.training2.Adapters.StudentAdapter
import com.example.training2.Adapters.VolleyAdapter
import com.example.training2.HelperClasses.StudentHelperClass
import com.example.training2.R
import org.json.JSONException


class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler)

        val queue: RequestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, "http://demo3102633.mockable.io/", null,
            { response ->
                try {
                    val list=ArrayList<StudentHelperClass>()
                    val jsonArray = response.getJSONArray("Students")
                    for (i in 0 until jsonArray.length()) {
                        val obj=jsonArray.getJSONObject(i)
                        list.add(StudentHelperClass(obj))
                    }
                    recyclerView.adapter=VolleyAdapter(list)
                } catch (e: JSONException) {

                }
            }) {
            Toast.makeText(this, "Fail to get data..", Toast.LENGTH_SHORT).show()
        }
        queue.add(jsonObjectRequest)

    }

}

