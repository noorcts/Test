package com.example.weatherapp.service

import com.example.weatherapp.data.CityData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("weather?appid=0256c627339d0e0c3b7bf4bbc23c4a51")
    suspend fun getCityData(@Query("q") city: String) : Response<CityData>

    @GET("weather?appid=0256c627339d0e0c3b7bf4bbc23c4a51")
    suspend fun getCityData(@Query("lat") lat: Double, @Query("lon") lon: Double) : Response<CityData>


    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService? {
            if (retrofitService == null) {
                retrofitService = Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create()).build()
                    .create(RetrofitService::class.java)
            }
            return retrofitService
        }
    }
}