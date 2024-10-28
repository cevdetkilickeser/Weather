package com.cevdetkilickeser.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cevdetkilickeser.weather.R
import com.cevdetkilickeser.weather.databinding.ForecastHolderBinding
import com.cevdetkilickeser.weather.model.forecast_model.ForecastList
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class ForecastAdapter (var context: Context, var forecastList: List<ForecastList>)
    : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>(){

    inner class ForecastViewHolder(binding: ForecastHolderBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ForecastHolderBinding
        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastAdapter.ForecastViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding: ForecastHolderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.forecast_holder, parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastAdapter.ForecastViewHolder, position: Int) {
        val forecast = forecastList[position]
        val b = holder.binding
        b.forecast = forecast

        Glide.with(context).load("https://api.openweathermap.org/img/w/${forecast.weather[0].icon}").into(b.imageView7)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }
}