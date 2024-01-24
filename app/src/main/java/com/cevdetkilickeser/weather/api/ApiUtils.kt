package com.cevdetkilickeser.weather.api

import com.cevdetkilickeser.weather.model.WeatherModel

class ApiUtils {
    companion object{
        val BASE_URL = "https://api.openweathermap.org/"

        fun getWeatherAPI() : WeatherAPI {
            return RetrofitClient.getClient(BASE_URL).create(WeatherAPI::class.java)
        }
    }
}