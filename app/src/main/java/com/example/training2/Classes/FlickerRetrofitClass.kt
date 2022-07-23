package com.example.training2.Classes

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FlickerRetrofitClass {
    companion object {
        private var retrofit: Retrofit? = null

        @Synchronized
        fun getRetrofit(): Retrofit {
            if(retrofit==null) {
                retrofit = Retrofit.Builder().baseUrl("https://api.flickr.com/services/")
                    .addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit!!
        }
    }
}