package com.example.weatherapp.service

import com.example.weatherapp.data.CityData
import com.example.weatherapp.util.Constants.API_KEY
import com.example.weatherapp.util.Constants.BASE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("weather?appid=${API_KEY}")
    suspend fun getCityData(@Query("q") city: String): Response<CityData>

    @GET("weather?appid=${API_KEY}")
    suspend fun getCityData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Response<CityData>

    //get the retrofit instance
    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService? {
            if (retrofitService == null) {
                retrofitService =
                    Retrofit.Builder().baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(RetrofitService::class.java)
            }
            return retrofitService
        }
    }
}