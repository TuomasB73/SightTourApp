package fi.urbanmappers.sighttour.fragments

import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fi.urbanmappers.sighttour.R
import fi.urbanmappers.sighttour.adapters.PlacesListRecyclerViewAdapter
import fi.urbanmappers.sighttour.databinding.FragmentPlacesBinding
import fi.urbanmappers.sighttour.databinding.FragmentPlacesListBinding
import fi.urbanmappers.sighttour.viewmodels.PlacesViewModel

class PlacesListFragment : Fragment(), PlacesListRecyclerViewAdapter.PlaceItemClickListener {
    private lateinit var binding: FragmentPlacesListBinding
    private val placesViewModel: PlacesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlacesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializePlacesRecyclerView()
    }

    private fun initializePlacesRecyclerView() {
        binding.placesListRecyclerView.layoutManager = LinearLayoutManager(this.context)

        placesViewModel.getPlaces(limit = 1000)
        placesViewModel.places.observe(viewLifecycleOwner) { placesData ->
            binding.placesListRecyclerView.adapter = PlacesListRecyclerViewAdapter(placesData.data, this)
        }
    }

    override fun onItemClick(placeId: String) {
        Log.d("PlaceOnClickTest", "placeId: $placeId")
        TODO("Navigate to individual place fragment here")
    }
}
