package com.solodilov.weatherapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodilov.weatherapp.domain.entity.CurrentForecast
import com.solodilov.weatherapp.domain.entity.DailyForecast
import com.solodilov.weatherapp.domain.entity.HourlyForecast
import com.solodilov.weatherapp.domain.usecase.GetWeatherInfoUseCase
import com.solodilov.weatherapp.extension.LiveEvent
import com.solodilov.weatherapp.extension.MutableLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getWeatherInfoUseCase: GetWeatherInfoUseCase,
) : ViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _currentForecast = MutableLiveData<CurrentForecast>()
    val currentForecast: LiveData<CurrentForecast> = _currentForecast

    private val _dailyForecastList = MutableLiveData<List<DailyForecast>>()
    val dailyForecastList: LiveData<List<DailyForecast>> = _dailyForecastList

    private val _hourlyForecastList = MutableLiveData<List<HourlyForecast>>()
    val hourlyForecastList: LiveData<List<HourlyForecast>> = _hourlyForecastList

    private val _weatherInfoErrorEvent = MutableLiveEvent()
    val weatherInfoErrorEvent: LiveEvent = _weatherInfoErrorEvent

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    fun getWeatherInfo(cityName: String) {
        viewModelScope.launch(exceptionHandler) {
            _loading.value = true
            val weatherInfo =  getWeatherInfoUseCase(cityName)
            _currentForecast.value = weatherInfo.currentForecast
            _dailyForecastList.value = weatherInfo.dailyForecastList
            _hourlyForecastList.value = getHourlyForecastSublist(weatherInfo.hourlyForecastList)

            _loading.value = false
            Log.d("TAG", "getWeatherInfo: $weatherInfo")
        }
    }

    private fun getHourlyForecastSublist(hourlyForecastList: List<HourlyForecast>): List<HourlyForecast> {
        val currentTime = Date()
        return hourlyForecastList.filter { hourlyForecast ->
            hourlyForecast.time.after(currentTime)
        }.sortedBy { hourlyForecast ->
            hourlyForecast.time
        }.take(24)
    }

    private fun handleError(error: Throwable) {
        Log.d("TAG", "handleError: $error")
        _loading.value = false
        _weatherInfoErrorEvent()
    }
}