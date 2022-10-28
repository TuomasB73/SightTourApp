package fi.urbanmappers.sighttour.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fi.urbanmappers.sighttour.R
import fi.urbanmappers.sighttour.datamodels.PlacesData
import fi.urbanmappers.sighttour.viewmodels.PlacesViewModel

class PlacesFragment : Fragment() {
    private lateinit var placesViewModel: PlacesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_places, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        placesViewModel = ViewModelProvider(this)[PlacesViewModel::class.java]
        placesViewModel.getPlacesData("accommodation", 10)
        placesViewModel.placesData.observe(viewLifecycleOwner) { placesData ->
            logPlacesData(placesData)
        }
    }

    private fun logPlacesData(places: PlacesData) {
        places.data.forEach {
            Log.d("PlacesData", it.name.en)
        }
    }
}