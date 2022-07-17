package com.example.training2.Activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.training2.Adapters.RetrofitAdapter
import com.example.training2.Adapters.VolleyAdapter
import com.example.training2.Classes.RetrofitClass
import com.example.training2.HelperClasses.StudentHelperClass
import com.example.training2.HelperClasses.StudentHelperClass2
import com.example.training2.Interfaces.RetrofitInterface
import com.example.training2.R
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.lang.Exception
import javax.security.auth.callback.Callback


class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        recycler=findViewById(R.id.recycler)
        getData()
    }

    fun getData() {
        val retrofitInterface=RetrofitClass.getRetrofit().create(RetrofitInterface::class.java)
        val call:Call<JsonObject> = retrofitInterface.getData()
        call.enqueue(object:retrofit2.Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    val list=ArrayList<StudentHelperClass2>()
                    val jsonObject=response.body()
                    val jsonArray = jsonObject!!.getAsJsonArray("class")
                    for (i in 0 until jsonArray!!.size()) {
                        val obj=jsonArray.get(i) as JsonObject
                        list.add(StudentHelperClass2(obj))
                    }
                    recycler!!.adapter=RetrofitAdapter(list)
                } catch (e: JSONException) {
                    Log.d("tag",e.message.toString())
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(this@MainActivity3, "Error$t",Toast.LENGTH_LONG).show()
                Log.d("tag", "Error : $t")
            }


        })

    }

    companion object{
        var recycler:RecyclerView?=null
    }
}