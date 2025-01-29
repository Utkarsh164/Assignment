package com.example.jetpackcomposeshoppingapp.api

data class productModel(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)