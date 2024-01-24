package com.cevdetkilickeser.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cevdetkilickeser.weather.api.WeatherAPI
import com.cevdetkilickeser.weather.model.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class MainViewModel @Inject constructor(var weatherApi:WeatherAPI): ViewModel() {

    var weatherData = MutableLiveData<WeatherModel>()

    fun getWeatherDataFromAPI(cityName: String){
        weatherApi.getWeatheData(cityName).enqueue(object: Callback<WeatherModel>{
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                if (response.body() != null){
                    val r = response.body()!!/*
                    r.main.temp = r.main.temp.roundToInt().toDouble()
                    r.main.tempMin = r.main.tempMin.roundToInt().toDouble()
                    r.main.tempMax = r.main.tempMax.roundToInt().toDouble()*/
                    val data = WeatherModel(r.base,r.clouds,r.cod,r.coord,r.dt,r.id,r.main,r.name,r.sys,r.timezone,r.visibility,r.weather,r.wind)
                    weatherData.value = data
                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {

            }

        })
    }
}