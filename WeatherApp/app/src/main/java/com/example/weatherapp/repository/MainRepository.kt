package com.example.weatherapp.repository

import com.example.weatherapp.service.RetrofitService

class MainRepository(private val retrofitService: RetrofitService) {
    //get the city data based on city name
    suspend fun getCityData(city: String) = retrofitService.getCityData(city)

    //get the city data based on lat and lon of the location
    suspend fun getCityData(lat: Double, lon: Double) = retrofitService.getCityData(lat, lon)
}