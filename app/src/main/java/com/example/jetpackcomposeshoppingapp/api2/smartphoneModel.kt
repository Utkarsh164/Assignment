package com.example.jetpackcomposeshoppingapp.api2

data class smartphoneModel(
    val limit: Int,
    val products: List<pProduct>,
    val skip: Int,
    val total: Int
)