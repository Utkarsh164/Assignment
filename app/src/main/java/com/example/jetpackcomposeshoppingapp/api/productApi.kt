package com.example.weatherapp.api

import com.example.jetpackcomposeshoppingapp.api.productModel
import retrofit2.Response
import retrofit2.http.GET


interface ProductApi
{
    @GET("products")
    suspend fun getProducts(
    ):Response<productModel>

}