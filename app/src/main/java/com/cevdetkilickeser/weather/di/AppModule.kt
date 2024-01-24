package com.cevdetkilickeser.weather.di

import com.cevdetkilickeser.weather.api.ApiUtils
import com.cevdetkilickeser.weather.api.WeatherAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule  {
    @Provides
    @Singleton
    fun provideWeatherAPI() : WeatherAPI{
        return ApiUtils.getWeatherAPI()
    }
}