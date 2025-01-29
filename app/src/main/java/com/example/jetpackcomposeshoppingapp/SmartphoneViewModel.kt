package com.example.jetpackcomposeshoppingapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposeshoppingapp.api2.smartphoneModel
import com.example.weatherapp.api.NetworkResponse
import com.example.weatherapp.api2.RetrofitInstance
import kotlinx.coroutines.launch

class SmartphoneViewModel : ViewModel() {
    private val smartphoneApi = RetrofitInstance.smartphoneApi

    private val _smartphoneResult = MutableLiveData<NetworkResponse<smartphoneModel>>()
    val smartphoneResult: LiveData<NetworkResponse<smartphoneModel>> = _smartphoneResult

    fun fetchSmartphones() {
        viewModelScope.launch {
            _smartphoneResult.value = NetworkResponse.Loading
            try {
                val response = smartphoneApi.getSmartphone()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _smartphoneResult.value = NetworkResponse.Success(it)
                    } ?: run {
                        _smartphoneResult.value = NetworkResponse.Error("Empty response")
                    }
                } else {
                    _smartphoneResult.value = NetworkResponse.Error("Failed to fetch smartphones")
                }
            } catch (e: Exception) {
                _smartphoneResult.value = NetworkResponse.Error("Network error")
            }
        }
    }
}
