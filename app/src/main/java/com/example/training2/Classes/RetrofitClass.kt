package com.example.training2.Classes

import android.content.Context
import com.example.training2.Interfaces.RetrofitInterface
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClass {

    companion object {
        private var retrofit: Retrofit? = null
        private var gson: Gson? = null

        @Synchronized
        fun getRetrofit(): Retrofit {
            gson=GsonBuilder().create()
            if(retrofit==null) {
                retrofit = Retrofit.Builder().baseUrl("http://demo3102633.mockable.io/")
                    .addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit!!
        }
    }
}