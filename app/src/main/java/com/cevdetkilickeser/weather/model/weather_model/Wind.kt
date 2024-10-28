package com.cevdetkilickeser.weather.model.weather_model


import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("deg")
    val deg: Int,
    @SerializedName("speed")
    var speed: Double
)