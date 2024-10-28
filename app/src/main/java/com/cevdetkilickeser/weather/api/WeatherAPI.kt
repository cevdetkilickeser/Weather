package com.cevdetkilickeser.weather.api

import com.cevdetkilickeser.weather.model.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("data/2.5/weather?units=metric&APPID=04a42b96398abc8e4183798ed22f9485")
    fun getWeatherData(@Query("q") cityName: String) : Call<WeatherModel>
}