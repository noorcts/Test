package com.example.weatherapp.repository

import com.example.weatherapp.service.RetrofitService

class MainRepository(private val retrofitService: RetrofitService) {
     suspend fun getCityData(city: String) = retrofitService.getCityData(city)
     suspend fun getCityData(lat: Double, lon: Double) = retrofitService.getCityData(lat, lon)
}