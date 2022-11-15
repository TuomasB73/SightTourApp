package fi.urbanmappers.sighttour.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import fi.urbanmappers.sighttour.databinding.FragmentPlacesIndividualBinding
import fi.urbanmappers.sighttour.datamodels.Place
import fi.urbanmappers.sighttour.datamodels.PlacesData
import fi.urbanmappers.sighttour.utils.PlacesCategory
import fi.urbanmappers.sighttour.viewmodels.PlacesViewModel

class PlacesIndividualFragment : Fragment() {

    private lateinit var binding: FragmentPlacesIndividualBinding
    private lateinit var placeId: String
    private val placesViewModel: PlacesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val placeIdArg = requireArguments().getString("placeId")
        if (placeIdArg != null) {
            placeId = placeIdArg
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlacesIndividualBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
placesViewModel.getPlaceById(placeId)
        placesViewModel.placeById.observe(viewLifecycleOwner) {
            placesData ->
            setData(placesData)
        }
    }

    private fun setData(place: Place) {
        binding.textPlaces.text = place.name.en?: place.name.fi
        binding.descriptionTextView.text = place.description.body
    }
}
