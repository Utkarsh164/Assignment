package com.example.weatherapp.api

import com.example.weatherapp.api2.RetrofitInstance.smartphoneApi
import com.example.weatherapp.api2.SmartphoneApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstances {

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.burl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val productApi: ProductApi = getInstance().create(ProductApi::class.java)
}
