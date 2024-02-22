package com.cevdetkilickeser.weather

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.cevdetkilickeser.weather.adapter.ForecastAdapter
import com.cevdetkilickeser.weather.databinding.ActivityMainBinding
import com.cevdetkilickeser.weather.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var forecastAdapter: ForecastAdapter
    private lateinit var savedCity: String
    private lateinit var currentTime: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setContentView(binding.root)

        binding.mainActivity = this

        val tempViewModel : MainViewModel by viewModels()
        mainViewModel = tempViewModel

        loadData()

        getLiveData()

        refresh()

        onClickEnter()
    }

    private fun getLiveData() {
        mainViewModel.weatherData.observe(this){
            binding.weatherModel = it
            Glide.with(this).load("https://api.openweathermap.org/img/w/${it.weather[0].icon}").into(binding.ivWeatherStatu)
        }

        mainViewModel.forecastData.observe(this){
            forecastAdapter = ForecastAdapter(this,it.list)
            binding.forecastAdapter = forecastAdapter
        }

        mainViewModel.currentTime.observe(this){
            binding.updatedTime = it
        }

        mainViewModel.error.observe(this){
            throwError(it)
        }
    }

    private fun throwError (error:Boolean){
        if (error){
            binding.updated.visibility = View.INVISIBLE
            binding.status.visibility = View.INVISIBLE
            binding.ivWeatherStatu.visibility = View.INVISIBLE
            binding.temp.visibility = View.INVISIBLE
            binding.minTemp.visibility = View.INVISIBLE
            binding.textViewMinTemp.visibility = View.INVISIBLE
            binding.maxTemp.visibility = View.INVISIBLE
            binding.textViewMaxTemp.visibility = View.INVISIBLE
            binding.sunrise.visibility = View.INVISIBLE
            binding.sunset.visibility = View.INVISIBLE
            binding.humidity.visibility = View.INVISIBLE
            binding.pressure.visibility = View.INVISIBLE
            binding.wind.visibility = View.INVISIBLE
            binding.country.visibility = View.INVISIBLE
            binding.city.visibility = View.INVISIBLE
            binding.recyclerView.visibility = View.INVISIBLE
            binding.txtError.visibility = View.VISIBLE
        }
        else{
            binding.updated.visibility = View.VISIBLE
            binding.status.visibility = View.VISIBLE
            binding.ivWeatherStatu.visibility = View.VISIBLE
            binding.temp.visibility = View.VISIBLE
            binding.minTemp.visibility = View.VISIBLE
            binding.textViewMinTemp.visibility = View.VISIBLE
            binding.maxTemp.visibility = View.VISIBLE
            binding.textViewMaxTemp.visibility = View.VISIBLE
            binding.sunrise.visibility = View.VISIBLE
            binding.sunset.visibility = View.VISIBLE
            binding.humidity.visibility = View.VISIBLE
            binding.pressure.visibility = View.VISIBLE
            binding.wind.visibility = View.VISIBLE
            binding.country.visibility = View.VISIBLE
            binding.city.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.VISIBLE
            binding.txtError.visibility = View.INVISIBLE
        }
    }

    private fun refresh(){
        binding.swipeRefreshLayout.setOnRefreshListener {
            loadData()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    fun onClickSearch(city: String){
        hideKeyboard()
        if (city.isNotEmpty()){
            if(city.isNotEmpty()){
                val sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.apply {
                    putString("city",city)
                }.apply()
                mainViewModel.getWeatherDataFromAPI(city)
            }
        }
    }

    fun loadData(){
        val sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE)
        savedCity = sharedPreferences.getString("city","istanbul")!!
        mainViewModel.getWeatherDataFromAPI(savedCity)
        binding.searchBoxCity = savedCity
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view: View = currentFocus ?: View(this)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun onClickEnter(){
        binding.edtLocation.setOnEditorActionListener { view, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                val city = view.text.toString()
                if(city.isNotEmpty()){
                    onClickSearch(city)
                }
            }
            true
        }
    }

    fun convertUnixTimestampToDate(unixTimestamp: Long): String {
        val timestampMillis = unixTimestamp * 1000
        return Date(timestampMillis).toString()
    }

}