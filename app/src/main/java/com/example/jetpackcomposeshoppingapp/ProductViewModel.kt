package com.example.jetpackcomposeshoppingapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposeshoppingapp.api.productModel
import com.example.jetpackcomposeshoppingapp.api2.smartphoneModel
import com.example.weatherapp.api.NetworkResponse
import com.example.weatherapp.api.RetrofitInstances
import kotlinx.coroutines.launch


class ProductViewModel : ViewModel() {
    private val productApi = RetrofitInstances.productApi

    private val _productResult = MutableLiveData<NetworkResponse<productModel>>()
    val productResult: LiveData<NetworkResponse<productModel>> = _productResult

    fun fetchProducts() {
        viewModelScope.launch {
            _productResult.value = NetworkResponse.Loading
            try {
                val response = productApi.getProducts()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _productResult.value = NetworkResponse.Success(it)
                    } ?: run {
                        _productResult.value = NetworkResponse.Error("Empty response")
                    }
                } else {
                    _productResult.value = NetworkResponse.Error("Failed to fetch products")
                }
            } catch (e: Exception) {
                _productResult.value = NetworkResponse.Error("Network error")
            }
        }
    }
}
