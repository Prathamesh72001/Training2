package com.example.training2.Activities

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.training2.Classes.RetrofitClass
import com.example.training2.Interfaces.RetrofitInterface
import com.example.training2.R
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity4 : AppCompatActivity(), Callback<JsonObject> {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val idET=findViewById<TextInputEditText>(R.id.idET)
        val nameET=findViewById<TextInputEditText>(R.id.nameET)
        val ageET=findViewById<TextInputEditText>(R.id.ageET)
        val sub1ET=findViewById<TextInputEditText>(R.id.sub1ET)
        val sub2ET=findViewById<TextInputEditText>(R.id.sub2ET)
        val sub3ET=findViewById<TextInputEditText>(R.id.sub3ET)
        val submit=findViewById<Button>(R.id.button)

        submit.setOnClickListener {
            val id=idET.text.toString()
            val name=nameET.text.toString()
            val age=ageET.text.toString()
            val sub1=sub1ET.text.toString()
            val sub2=sub2ET.text.toString()
            val sub3=sub3ET.text.toString()
            if(inputCheck(id) && inputCheck(name) && inputCheck(age) && inputCheck(sub1) && inputCheck(sub2) && inputCheck(sub3)){
                postDataUsingVolley(id.toInt(),name,age.toInt(),sub1.toInt(),sub2.toInt(),sub3.toInt())
                //postDataUsingRetrofit(id.toInt(),name,age.toInt(),sub1.toInt(),sub2.toInt(),sub3.toInt())
            }
            else{
                Toast.makeText(this, "Please Enter All Details", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun postDataUsingRetrofit(id: Int, name: String, age: Int, sub1: Int, sub2: Int, sub3: Int) {
        val retrofitInterface= RetrofitClass.getRetrofit().create(RetrofitInterface::class.java)
        val obj1 = JSONObject()
        val array1=JSONArray()
        val array2=JSONArray()
        val obj2=JSONObject()
        try {
            obj2.put("id", id)
            obj2.put("name", name)
            obj2.put("age", age)

            array2.put(sub1)
            array2.put(sub2)
            array2.put(sub3)

            obj2.put("marks", array2)

            array1.put(obj2)
            obj1.put("Students", array1)

            val userCall: Call<JsonObject> = retrofitInterface.postData(obj1.toString())
            userCall.enqueue(this)
        }
        catch(e:Exception){
            Log.d("tag",e.message.toString())
        }
    }

    private fun postDataUsingVolley(id: Int, name: String, age: Int, sub1: Int, sub2: Int, sub3: Int) {

        val queue = Volley.newRequestQueue(this)
        val obj1 = JSONObject()
        val array1=JSONArray()
        val array2=JSONArray()
        val obj2=JSONObject()
        try {
            obj2.put("id",id)
            obj2.put("name",name)
            obj2.put("age",age)

            array2.put(sub1)
            array2.put(sub2)
            array2.put(sub3)

            obj2.put("marks",array2)

            array1.put(obj2)
            obj1.put("Students",array1)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, "http://demo3102633.mockable.io/postData", obj1,
            { response ->
                response.put("Object",obj1)
                Toast.makeText(this,"Data Posted Successfully",Toast.LENGTH_LONG).show()
            Log.d("tag",response.toString())},
            {Toast.makeText(this,"Error",Toast.LENGTH_LONG).show() })
        queue.add(jsonObjectRequest)
    }


    fun inputCheck(text: String): Boolean {
        return !TextUtils.isEmpty(text)
    }

    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
        Toast.makeText(this,"Data Posted Successfully",Toast.LENGTH_LONG).show()
        Log.d("tag",response.toString())
    }

    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
        Toast.makeText(this,"Error : $t",Toast.LENGTH_LONG).show()
        Log.d("tag","Error : $t")
    }

}