package com.solodilov.weatherapp

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

object Converter {

    fun getTemperature(context: Context, temp: Double): String =
        context.getString(R.string.temp_format, temp)

    private val locale = Locale.getDefault()
    private val apiDateFormatter = SimpleDateFormat("yyyy-MM-dd", locale)
    private val apiTimeFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm", locale)
    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy", locale)
    private val timeFormatter = SimpleDateFormat("HH:mm", locale)

    fun getFormattedDate(date: Date): String =
        dateFormatter.format(date)

    fun getFormattedTime(time: Date): String =
        timeFormatter.format(time)

    fun getDate(date: String): Date =
        apiDateFormatter.parse(date)

    fun getTime(time: String): Date =
        apiTimeFormatter.parse(time)
}