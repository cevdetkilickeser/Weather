package com.cevdetkilickeser.weather

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.cevdetkilickeser.weather.databinding.ActivityMainBinding
import com.cevdetkilickeser.weather.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setContentView(binding.root)

        binding.mainActivity = this
        binding.city = "kars"

        val tempViewModel : MainViewModel by viewModels()
        mainViewModel = tempViewModel

        mainViewModel.getWeatherDataFromAPI("kars")

        mainViewModel.weatherData.observe(this){
            binding.weatherModel = it
            Glide.with(this).load("https://api.openweathermap.org/img/w/${it.weather[0].icon}").into(binding.ivWeatherStatu)
        }
    }

}

//df567c20b44a0b4626a5402ce5fccc4b