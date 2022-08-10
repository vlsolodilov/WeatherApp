package com.solodilov.weatherapp.ui

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.solodilov.weatherapp.MainApplication
import com.solodilov.weatherapp.R
import com.solodilov.weatherapp.databinding.FragmentLocationListBinding
import com.solodilov.weatherapp.presentation.MainViewModel
import com.solodilov.weatherapp.ui.adapter.LocationAdapter
import javax.inject.Inject

class LocationListFragment : Fragment() {

    companion object {
        fun newInstance(): LocationListFragment = LocationListFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private var _binding: FragmentLocationListBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentLocationListBinding is null")

    private var locationAdapter: LocationAdapter? = null

    private val requestLocationPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            viewModel.refreshWithCurrentLocation()
        } else {
            Snackbar.make(binding.root, R.string.permission_is_denied, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MainApplication).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLocationListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        initViews()
        observeViewModel()
        initSwipeListener()
    }

    private fun setupToolbar() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.topAppBar)
    }

    private fun initViews() {
        locationAdapter = LocationAdapter { location ->
            viewModel.refresh("${location.latitude},${location.longitude}")
        }
        binding.locationList.adapter = locationAdapter
        binding.currentLocation.setOnClickListener {
            requestLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        binding.clearLocationList.setOnClickListener { viewModel.deleteAllLocation() }
    }

    private fun observeViewModel() {
        viewModel.loading.observe(viewLifecycleOwner, ::toggleProgress)
        viewModel.locationList.observe(viewLifecycleOwner) { locations ->
            locationAdapter?.submitList(locations)
            toggleShowLocationList(locations.isNotEmpty())
        }
        viewModel.weatherInfoSuccessEvent.observe(viewLifecycleOwner) { startMainFragment() }
        viewModel.weatherInfoErrorEvent.observe(viewLifecycleOwner) { showError() }
    }

    private fun toggleProgress(visible: Boolean) {
        binding.locationListLoading.isVisible = visible
    }

    private fun toggleShowLocationList(visible: Boolean) {
        binding.containerLastLocations.isVisible = visible
    }

    private fun startMainFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, MainFragment.newInstance())
            .commit()
    }

    private fun showError() {
        Snackbar.make(binding.root, R.string.error, Snackbar.LENGTH_SHORT).show()
    }

    private fun initSwipeListener() {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = locationAdapter?.currentList?.get(viewHolder.adapterPosition)
                if (item != null) {
                    viewModel.deleteLocation(item)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.locationList)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val searchView = menu.findItem(R.id.searchButton).actionView as SearchView
        searchView.queryHint = getString(R.string.city)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.refresh(query)
                }
                searchView.onActionViewCollapsed()
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    override fun onDestroyView() {
        _binding = null
        locationAdapter = null
        super.onDestroyView()
    }
}