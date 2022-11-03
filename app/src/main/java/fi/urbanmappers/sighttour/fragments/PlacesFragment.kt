package fi.urbanmappers.sighttour.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import fi.urbanmappers.sighttour.databinding.FragmentPlacesBinding
import fi.urbanmappers.sighttour.datamodels.PlacesData
import fi.urbanmappers.sighttour.viewmodels.PlacesViewModel

class PlacesFragment : Fragment() {
    private lateinit var binding: FragmentPlacesBinding

    private lateinit var placesViewModel: PlacesViewModel

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

        placesViewModel = ViewModelProvider(this)[PlacesViewModel::class.java]
        placesViewModel.getPlacesData("accommodation", 100)
        placesViewModel.placesData.observe(viewLifecycleOwner) { placesData ->
            logPlacesData(placesData)
        }
        binding.placeholderTextView.text = "Test"
    }

    private fun logPlacesData(places: PlacesData) {
        places.data.forEach {
            Log.d("PlacesData", it.name.en)
        }
    }
}
