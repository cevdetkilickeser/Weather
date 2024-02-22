package com.cevdetkilickeser.weather.model.forecast_model

data class ForecastModel(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ForecastList>,
    val message: Int
)