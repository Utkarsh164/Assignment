package com.example.weatherapp.api2


import com.example.jetpackcomposeshoppingapp.api2.smartphoneModel
import retrofit2.Response
import retrofit2.http.GET


interface SmartphoneApi
{
    @GET("products/category/smartphones")
    suspend fun getSmartphone(
    ):Response<smartphoneModel>

}