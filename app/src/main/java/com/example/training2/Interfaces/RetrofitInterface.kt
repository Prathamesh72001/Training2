package com.example.training2.Interfaces

import com.example.training2.HelperClasses.StudentHelperClass
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitInterface {
    @GET("getData")
    fun getData(): Call<JsonObject>

    @POST("postData")
    fun postData(@Body body:String): Call<JsonObject>
}