package com.cevdetkilickeser.weather.api

import com.cevdetkilickeser.weather.model.forecast_model.ForecastModel
import com.cevdetkilickeser.weather.model.weather_model.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    //https://api.openweathermap.org/data/2.5/weather?q=istanbul&APPID=04a42b96398abc8e4183798ed22f9485
    //https://api.openweathermap.org/data/2.5/forecast?units=metric&appid=df567c20b44a0b4626a5402ce5fccc4b
    @GET("data/2.5/weather?units=metric&APPID=df567c20b44a0b4626a5402ce5fccc4b")
    fun getWeatheData(@Query("q") cityName: String) : Call<WeatherModel>

    @GET("data/2.5/forecast?units=metric&appid=df567c20b44a0b4626a5402ce5fccc4b")
    fun getForecastData(@Query("lon") lon: String,
                        @Query("lat") lat: String) : Call<ForecastModel>
}