package com.example.weatherapp.api2

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance
{


    fun getInstance() : Retrofit
    {
        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val smartphoneApi : SmartphoneApi = getInstance().create(SmartphoneApi::class.java)
}