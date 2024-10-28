package com.cevdetkilickeser.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cevdetkilickeser.weather.api.WeatherAPI
import com.cevdetkilickeser.weather.model.forecast_model.ForecastModel
import com.cevdetkilickeser.weather.model.weather_model.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private var weatherApi:WeatherAPI): ViewModel() {

    var weatherData = MutableLiveData<WeatherModel>()
    var forecastData = MutableLiveData<ForecastModel>()
    var currentTime = MutableLiveData<String>()
    var error = MutableLiveData<Boolean>()
    var lon = ""
    var lat = ""

    fun getWeatherDataFromAPI(cityName: String){
        weatherApi.getWeatherData(cityName).enqueue(object: Callback<WeatherModel>{
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                if (response.body() != null){
                    val data = response.body()!!
                    //val data = WeatherModel(r.base,r.clouds,r.cod,r.coord,r.dt,r.id,r.main,r.name,r.sys,r.timezone,r.visibility,r.weather,r.wind)
                    weatherData.value = data
                    currentTime.value  = getCurrentTime()
                    error.value = false
                    lon = data.coord.lon.toString()
                    lat = data.coord.lat.toString()
                    getForecastDataFromAPI(lon,lat)
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

    fun getForecastDataFromAPI(lon:String, lat:String){
        weatherApi.getForecastData(lon, lat).enqueue(object: Callback<ForecastModel>{
            override fun onResponse(call: Call<ForecastModel>, response: Response<ForecastModel>) {
                if (response.body() != null){
                    val forecast = response.body()!!
                    forecastData.value = forecast
                }
            }

            override fun onFailure(call: Call<ForecastModel>, t: Throwable) {
                TODO("Not yet implemented")
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