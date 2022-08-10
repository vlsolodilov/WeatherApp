package com.solodilov.weatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.solodilov.weatherapp.MainApplication
import com.solodilov.weatherapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MainApplication).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, MainFragment.newInstance())
                .commit()
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.mainContainer)
        if (fragment is MainFragment) {
            finishAffinity()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}