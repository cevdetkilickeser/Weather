package com.cevdetkilickeser.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cevdetkilickeser.weather.R
import com.cevdetkilickeser.weather.databinding.ForecastHolderBinding
import com.cevdetkilickeser.weather.model.forecast_model.ForecastList

class ForecastAdapter(private var forecastList: List<ForecastList>) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    inner class ForecastViewHolder(var binding: ForecastHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForecastAdapter.ForecastViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ForecastHolderBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.forecast_holder, parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastAdapter.ForecastViewHolder, position: Int) {
        val forecast = forecastList[position]
        val b = holder.binding
        b.forecast = forecast

        Glide.with(b.root)
            .load("https://api.openweathermap.org/img/w/${forecast.weather[0].icon}.png")
            .into(b.imageView7)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }
}