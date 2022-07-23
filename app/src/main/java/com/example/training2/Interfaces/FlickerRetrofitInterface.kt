package com.example.training2.Interfaces

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickerRetrofitInterface {
    @GET("rest/?method=flickr.interestingness.getList&api_key=ca370d51a054836007519a00ff4ce59e&per_page=10&format=json")
    fun getInteresting(@Query("page") page:Int,@Query("nojsoncallback") num:Int): Call<JsonObject>

    @GET("rest/?method=flickr.photos.getRecent&api_key=ca370d51a054836007519a00ff4ce59e&per_page=10&format=json")
    fun getRecent(@Query("page") page:Int,@Query("nojsoncallback") num:Int): Call<JsonObject>
}