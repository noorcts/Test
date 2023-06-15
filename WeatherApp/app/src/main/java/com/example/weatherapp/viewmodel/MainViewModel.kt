package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.CityData
import com.example.weatherapp.repository.MainRepository
import com.example.weatherapp.service.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    val cityData = MutableLiveData<CityData>()

    // calling the service with city name or lat/lon as input
    fun getCityData(city: String = "", lat: Double = 0.0, lon: Double = 0.0) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                //api call to get the data
                val response = RetrofitService.getInstance()
                    ?.let {
                        if (city.isEmpty())
                            MainRepository(it).getCityData(lat, lon)
                        else
                            MainRepository(it).getCityData(city)
                    }

                //check for the success and failure scenatios
                if (response?.isSuccessful == true) {
                    cityData.postValue(response?.body())
                } else {
                    cityData.postValue(CityData("", "401", null, null))
                    Log.e("", "service failed")
                }
            }
        }
    }
}