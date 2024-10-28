package com.cevdetkilickeser.weather.api

import com.cevdetkilickeser.weather.model.forecast_model.ForecastModel
import com.cevdetkilickeser.weather.model.weather_model.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("data/2.5/weather?units=metric&APPID=df567c20b44a0b4626a5402ce5fccc4b")
    fun getWeatherData(@Query("q") cityName: String) : Call<WeatherModel>

    @GET("data/2.5/forecast?units=metric&appid=df567c20b44a0b4626a5402ce5fccc4b")
    fun getForecastData(@Query("lon") lon: String,
                        @Query("lat") lat: String) : Call<ForecastModel>
}