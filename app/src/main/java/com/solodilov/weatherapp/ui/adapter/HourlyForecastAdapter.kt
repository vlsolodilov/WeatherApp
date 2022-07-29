package com.solodilov.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solodilov.weatherapp.databinding.ItemHourlyForecastBinding
import com.solodilov.weatherapp.domain.entity.HourlyForecast
import com.solodilov.weatherapp.Converter

class HourlyForecastAdapter(
): ListAdapter<HourlyForecast, HourlyForecastViewHolder>(HourlyForecastDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastViewHolder =
        HourlyForecastViewHolder(ItemHourlyForecastBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        )

    override fun onBindViewHolder(holder: HourlyForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class HourlyForecastViewHolder(private val binding: ItemHourlyForecastBinding)
    : RecyclerView.ViewHolder(binding.root){

    fun bind(hourlyForecast: HourlyForecast) {
        binding.apply {
            hour.text = Converter.getFormattedTime(hourlyForecast.time)
            hourTemp.text = Converter.getTemperature(itemView.context, hourlyForecast.temp)
        }

        Glide
            .with(itemView)
            .load("https:${hourlyForecast.iconCondition}")
            .into(binding.iconConditionHour)
    }
}

private class HourlyForecastDiffCallback : DiffUtil.ItemCallback<HourlyForecast>() {

    override fun areItemsTheSame(oldItem: HourlyForecast, newItem: HourlyForecast): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: HourlyForecast, newItem: HourlyForecast): Boolean {
        return oldItem == newItem
    }
}
