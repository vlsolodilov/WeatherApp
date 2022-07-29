package com.solodilov.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solodilov.weatherapp.Converter
import com.solodilov.weatherapp.R
import com.solodilov.weatherapp.databinding.ItemDailyForecastBinding
import com.solodilov.weatherapp.domain.entity.DailyForecast

class DailyForecastAdapter(
) : ListAdapter<DailyForecast, DailyForecastViewHolder>(DailyForecastDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder =
        DailyForecastViewHolder(ItemDailyForecastBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        )

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DailyForecastViewHolder(private val binding: ItemDailyForecastBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(dailyForecast: DailyForecast) {
        binding.apply {
            date.text = Converter.getFormattedDate(dailyForecast.date)
            maxMinTemp.text = itemView.context.getString(
                R.string.max_min_temp_format,
                dailyForecast.maxTemp,
                dailyForecast.minTemp)
        }

        Glide
            .with(itemView)
            .load("https:${dailyForecast.iconCondition}")
            .into(binding.iconConditionDay)
    }
}

private class DailyForecastDiffCallback : DiffUtil.ItemCallback<DailyForecast>() {

    override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
        return oldItem == newItem
    }
}
