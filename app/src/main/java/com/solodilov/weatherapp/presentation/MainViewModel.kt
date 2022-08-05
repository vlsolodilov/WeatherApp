package com.solodilov.weatherapp.presentation

import android.util.Log
import androidx.lifecycle.*
import com.solodilov.weatherapp.domain.entity.WeatherInfo
import com.solodilov.weatherapp.domain.usecase.GetWeatherInfoUseCase
import com.solodilov.weatherapp.domain.usecase.RefreshWeatherInfoUseCase
import com.solodilov.weatherapp.extension.LiveEvent
import com.solodilov.weatherapp.extension.MutableLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getWeatherInfoUseCase: GetWeatherInfoUseCase,
    private val refreshWeatherInfoUseCase: RefreshWeatherInfoUseCase,
) : ViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _weatherInfo = MutableLiveData<WeatherInfo>()
    val weatherInfo: LiveData<WeatherInfo> = _weatherInfo

    private val _weatherInfoErrorEvent = MutableLiveEvent()
    val weatherInfoErrorEvent: LiveEvent = _weatherInfoErrorEvent

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    init {
        refresh(null)
    }

    fun getWeatherInfo() {
        viewModelScope.launch(exceptionHandler) {
            _loading.value = true
            _weatherInfo.value = getFormattedWeatherInfo(getWeatherInfoUseCase().first())
            _loading.value = false
            Log.d("TAG", "getWeatherInfo: ${weatherInfo.value}")
        }
    }

    fun refresh(location: String?) {
        viewModelScope.launch(exceptionHandler) {
            _loading.value = true
            refreshWeatherInfoUseCase(location)
            getWeatherInfo()
            _loading.value = false
            Log.d("TAG", "refresh: ${weatherInfo.value}")
        }
    }

    private fun getFormattedWeatherInfo(weatherInfo: WeatherInfo): WeatherInfo {
        val currentTime = Date()
        val hourlyForecastSublist = weatherInfo.hourlyForecastList.filter { hourlyForecast ->
            hourlyForecast.time.after(currentTime)
        }.sortedBy { hourlyForecast ->
            hourlyForecast.time
        }.take(24)

        return weatherInfo.copy(hourlyForecastList = hourlyForecastSublist)
    }

    private fun handleError(error: Throwable) {
        Log.d("TAG", "handleError: $error")
        error.printStackTrace()
        _loading.value = false
        _weatherInfoErrorEvent()
    }
}