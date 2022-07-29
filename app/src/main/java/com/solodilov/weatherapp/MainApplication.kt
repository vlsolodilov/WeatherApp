package com.solodilov.weatherapp

import android.app.Application
import com.solodilov.weatherapp.di.DaggerApplicationComponent

class MainApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}