package com.example.weatherapp.repository

import com.example.weatherapp.data.CityData
import com.example.weatherapp.service.RetrofitService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class MainRepositoryTest {

    @Mock
    private lateinit var retrofitService: RetrofitService

    private lateinit var mainRepository: MainRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mainRepository = MainRepository(retrofitService)
    }

    @Test
    fun getCityDataWithName() = runBlocking {
        // Mock response data
        val cityData = CityData("City Name", "200", null, null)
        val response = Response.success(cityData)

        // Mock RetrofitService behavior
        `when`(retrofitService.getCityData("City Name")).thenReturn(response)

        // Call the method under test
        val result = mainRepository.getCityData("City Name")

        // Assert the result
        assert(result.isSuccessful)
        assert(result.body() == cityData)
    }

    @Test
    fun getCityDataLatLon() = runBlocking {
        // Mock response data
        val cityData = CityData("City Name", "200", null, null)
        val response = Response.success(cityData)

        // Mock RetrofitService behavior
        val lat = 10.0
        val lon = 20.0
        `when`(retrofitService.getCityData(lat, lon)).thenReturn(response)

        // Call the method under test
        val result = mainRepository.getCityData(lat, lon)

        // Assert the result
        assert(result.isSuccessful)
        assert(result.body() == cityData)
    }
}
