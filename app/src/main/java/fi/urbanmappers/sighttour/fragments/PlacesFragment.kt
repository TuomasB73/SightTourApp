package fi.urbanmappers.sighttour.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import fi.urbanmappers.sighttour.databinding.FragmentPlacesBinding
import fi.urbanmappers.sighttour.datamodels.Place
import fi.urbanmappers.sighttour.datamodels.PlacesData
import fi.urbanmappers.sighttour.viewmodels.PlacesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlacesFragment : Fragment() {
    private lateinit var binding: FragmentPlacesBinding
    private val placesViewModel: PlacesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlacesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        placesViewModel.getPlaces(limit = 3)
        placesViewModel.places.observe(viewLifecycleOwner) { places ->
            places.data.forEach {
                Log.d("PlacesData", it.toString())
            }
        }

        placesViewModel.getPlaceById("2257")
        placesViewModel.placeById.observe(viewLifecycleOwner) { placeById ->
            Log.d("PlaceId", placeById.toString())
        }
    }
}
