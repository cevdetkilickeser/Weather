package com.cevdetkilickeser.weather.model.forecast_model

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)