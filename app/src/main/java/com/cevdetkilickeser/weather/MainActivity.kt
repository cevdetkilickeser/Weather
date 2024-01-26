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
import com.cevdetkilickeser.weather.databinding.ActivityMainBinding
import com.cevdetkilickeser.weather.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
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

        mainViewModel.currentTime.observe(this){
            binding.updatedTime = it
        }

        mainViewModel.error.observe(this){
            if (it){
                binding.updated.visibility = View.GONE
                binding.midData.visibility = View.GONE
                binding.bottomData.visibility = View.GONE
                binding.txtError.visibility = View.VISIBLE
            }else{
                binding.updated.visibility = View.VISIBLE
                binding.midData.visibility = View.VISIBLE
                binding.bottomData.visibility = View.VISIBLE
                binding.txtError.visibility = View.GONE
            }
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
        val sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("city",city)
        }.apply()

        mainViewModel.getWeatherDataFromAPI(city)
    }

    fun loadData(){
        val sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE)
        savedCity = sharedPreferences.getString("city","istanbul")!!
        mainViewModel.getWeatherDataFromAPI(savedCity)
        binding.city = savedCity
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
                onClickSearch(city)
            }
            true
        }
    }

    fun convertUnixTimestampToDate(unixTimestamp: Long): String {
        val timestampMillis = unixTimestamp * 1000
        return Date(timestampMillis).toString()
    }

}

//df567c20b44a0b4626a5402ce5fccc4b