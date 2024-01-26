package com.cevdetkilickeser.weather.viewmodel

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cevdetkilickeser.weather.MainActivity
import com.cevdetkilickeser.weather.api.WeatherAPI
import com.cevdetkilickeser.weather.model.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.currentCoroutineContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(var weatherApi:WeatherAPI): ViewModel() {

    var weatherData = MutableLiveData<WeatherModel>()
    var currentTime = MutableLiveData<String>()
    var error = MutableLiveData<Boolean>()

    fun getWeatherDataFromAPI(cityName: String){
        weatherApi.getWeatheData(cityName).enqueue(object: Callback<WeatherModel>{
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                if (response.body() != null){
                    val r = response.body()!!
                    val data = WeatherModel(r.base,r.clouds,r.cod,r.coord,r.dt,r.id,r.main,r.name,r.sys,r.timezone,r.visibility,r.weather,r.wind)
                    weatherData.value = data
                    currentTime.value  = getCurrentTime()
                    error.value = false
                }else{
                    error.value = true
                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                error.value = true
                currentTime.value  = getCurrentTime()
            }

        })
    }

    fun getCurrentTime(): String {
        val currentTime = LocalTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val formattedTime = currentTime.format(formatter)

        return formattedTime
    }
}