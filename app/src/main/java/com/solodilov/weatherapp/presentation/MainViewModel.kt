package com.solodilov.weatherapp.presentation

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.solodilov.weatherapp.R
import com.solodilov.weatherapp.domain.entity.Location
import com.solodilov.weatherapp.domain.entity.WeatherInfo
import com.solodilov.weatherapp.domain.usecase.*
import com.solodilov.weatherapp.extension.LiveEvent
import com.solodilov.weatherapp.extension.MutableLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getWeatherInfoUseCase: GetWeatherInfoUseCase,
    private val refreshWeatherInfoUseCase: RefreshWeatherInfoUseCase,
    private val getLocationListUseCase: GetLocationListUseCase,
    private val deleteLocationUseCase: DeleteLocationUseCase,
    private val deleteAllLocationUseCase: DeleteAllLocationUseCase,
    private val application: Application,
) : ViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _weatherInfo = MutableLiveData<WeatherInfo>()
    val weatherInfo: LiveData<WeatherInfo> = _weatherInfo

    val locationList: LiveData<List<Location>> = getLocationListUseCase().asLiveData()

    private val _weatherInfoSuccessEvent = MutableLiveEvent()
    val weatherInfoSuccessEvent: LiveEvent = _weatherInfoSuccessEvent

    private val _weatherInfoErrorEvent = MutableLiveEvent()
    val weatherInfoErrorEvent: LiveEvent = _weatherInfoErrorEvent

    private val _selectLocationEvent = MutableLiveEvent()
    val selectLocationEvent: LiveEvent = _selectLocationEvent

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    fun getWeatherInfo() {
        viewModelScope.launch(exceptionHandler) {
            _loading.value = true
            val info = getWeatherInfoUseCase().firstOrNull()
            if (info != null) {
                _weatherInfo.value = getFormattedWeatherInfo(info)
            } else {
                _selectLocationEvent()
            }
            _loading.value = false
            Log.d("TAG", "getWeatherInfo: ${weatherInfo.value}")
        }
    }

    fun refresh(location: String?) {
        viewModelScope.launch(exceptionHandler) {
            _loading.value = true
            if (location != null) {
                refreshWeatherInfoUseCase(location)
                _weatherInfoSuccessEvent()
            } else {
                getWeatherInfoUseCase().firstOrNull()?.let { info ->
                    refreshWeatherInfoUseCase(getFormattedLocation(
                        info.location.latitude,
                        info.location.longitude
                    ))
                    getWeatherInfo()
                }
            }
            _loading.value = false
            Log.d("TAG", "refresh: ${weatherInfo.value}")
        }
    }

    private fun getFormattedLocation(latitude: Double, longitude: Double): String =
        "$latitude,$longitude"

    @SuppressLint("MissingPermission")
    fun refreshWithCurrentLocation() {
        LocationServices
            .getFusedLocationProviderClient(application).lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    refresh(getFormattedLocation(location.latitude, location.longitude))
                } else {
                    _weatherInfoErrorEvent()
                }
            }
    }

    fun deleteLocation(location: Location) =
        viewModelScope.launch(exceptionHandler) {
            _loading.value = true
            deleteLocationUseCase(location.id)
            _loading.value = false
        }

    fun deleteAllLocation() =
        viewModelScope.launch(exceptionHandler) {
            _loading.value = true
            deleteAllLocationUseCase()
            _loading.value = false
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