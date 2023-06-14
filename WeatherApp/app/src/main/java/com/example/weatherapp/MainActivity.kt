package com.example.weatherapp

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.location.Location
import com.example.weatherapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private var yourLocation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get the last searched city name
        binding.edtCity.setText(getCityName())

        //get view model object for the activity
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.btnWeather.setOnClickListener {
            viewModel.getCityData(binding.edtCity.text.toString())
            setUI(View.GONE, getString(R.string.loading), getString(R.string.wait))
        }
        val locationData = Location()
        locationData.getLocationData(this, isLocationPermissionGranted())
        locationData.latLon.observe(this) {
            viewModel.getCityData("", it.get(0), it.get(1))
            yourLocation = true
            Log.d(
                "TAG11111111111111111",
                "latitude=========" + it.get(0) + " longitude========" + it.get(1)
            )
        }
        viewModel.cityData.observe(this) {
            if (yourLocation) {
                binding.edtCity.setText(getString(R.string.you_location))
                yourLocation = false
            }
            if (it.cod == "200") {
                Glide.with(this)
                    .load(
                        "https://openweathermap.org/img/wn/${
                            it.weather?.get(
                                0
                            )?.icon
                        }.png"
                    )
                    .into(binding.imgWeather)
                setUI(
                    View.VISIBLE,
                    it.main?.temp.toString() + getString(R.string.kelvin),
                    it.weather?.get(0)?.description
                )
                saveCityName(it.name)
            } else {
                setUI(View.GONE, getString(R.string.no_data_msg), "")
            }
        }

    }

    private fun setUI(visibility: Int, message1: String?, message2: String?) {
        binding.imgWeather.visibility = visibility
        binding.txtTemp.text = message1
        binding.txtDesc.text = message2
    }

    private fun saveCityName(city: String) {
        getSharedPreferences("my_preferences", MODE_PRIVATE).edit()
            .putString("city", city)
            .commit()
    }

    private fun getCityName(): String? {
        return getSharedPreferences("my_preferences", MODE_PRIVATE)
            .getString("city", "")
    }

    private fun isLocationPermissionGranted(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                0
            )
            false
        } else {
            true
        }
    }

}