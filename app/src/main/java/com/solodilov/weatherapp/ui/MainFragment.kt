package com.solodilov.weatherapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.solodilov.weatherapp.MainApplication
import com.solodilov.weatherapp.R
import com.solodilov.weatherapp.databinding.FragmentMainBinding
import com.solodilov.weatherapp.domain.entity.CurrentForecast
import com.solodilov.weatherapp.presentation.MainViewModel
import com.solodilov.weatherapp.Converter.getTemperature
import com.solodilov.weatherapp.ui.adapter.DailyForecastAdapter
import com.solodilov.weatherapp.ui.adapter.HourlyForecastAdapter
import javax.inject.Inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding is null")

    private var hourlyForecastAdapter: HourlyForecastAdapter? = null
    private var dailyForecastAdapter: DailyForecastAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MainApplication).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeViewModel()
    }

    private fun initViews() {
        hourlyForecastAdapter = HourlyForecastAdapter()
        binding.hourlyForecastList.adapter = hourlyForecastAdapter
        dailyForecastAdapter = DailyForecastAdapter()
        binding.dailyForecastList.adapter = dailyForecastAdapter
    }

    private fun observeViewModel() {
        viewModel.getWeatherInfo("Moscow")
        viewModel.currentForecast.observe(viewLifecycleOwner) { currentForecast->
            showCurrentForecast(currentForecast)
        }
        viewModel.loading.observe(viewLifecycleOwner, ::toggleProgress)
        viewModel.hourlyForecastList.observe(viewLifecycleOwner) { hourlyForecastList ->
            hourlyForecastAdapter?.submitList(hourlyForecastList)
        }
        viewModel.dailyForecastList.observe(viewLifecycleOwner) { dailyForecastList ->
            dailyForecastAdapter?.submitList(dailyForecastList)
        }
        viewModel.weatherInfoErrorEvent.observe(viewLifecycleOwner) { showError() }
    }

    private fun showCurrentForecast(currentForecast: CurrentForecast) {
        binding.apply {
            cityName.text = currentForecast.cityName
            currentTemp.text = getTemperature(requireContext(),currentForecast.temp)
            condition.text = currentForecast.condition
            feelsLike.text = getTemperature(requireContext(), currentForecast.feelsLikeTemp)
        }
        Glide
            .with(this)
            .load("https:${currentForecast.iconCondition}")
            .into(binding.iconCondition)
    }

    private fun toggleProgress(visible: Boolean) {
        binding.weatherInfoLoading.isVisible = visible
    }

    private fun showError() {
        Snackbar.make(binding.root, R.string.error, Snackbar.LENGTH_SHORT).show()
    }


    override fun onDestroyView() {
        _binding = null
        hourlyForecastAdapter = null
        dailyForecastAdapter = null
        super.onDestroyView()
    }
}