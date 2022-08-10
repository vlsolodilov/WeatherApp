package com.solodilov.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.solodilov.weatherapp.databinding.ItemLocationBinding
import com.solodilov.weatherapp.domain.entity.Location

class LocationAdapter(
    private val onClick: (Location) -> Unit,
) : ListAdapter<Location, LocationViewHolder>(LocationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder =
        LocationViewHolder(ItemLocationBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false),
            onClick
        )

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class LocationViewHolder(
    private val binding: ItemLocationBinding,
    private val onClick: (Location) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(location: Location) {
        binding.apply {
            cityName.text = location.cityName
            region.text = location.regionName
        }
        itemView.setOnClickListener { onClick(location) }
    }
}

private class LocationDiffCallback : DiffUtil.ItemCallback<Location>() {

    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem == newItem
    }
}
