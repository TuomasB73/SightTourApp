package fi.urbanmappers.sighttour.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import fi.urbanmappers.sighttour.R
import androidx.fragment.app.viewModels
import fi.urbanmappers.sighttour.databinding.FragmentPlacesBinding
import fi.urbanmappers.sighttour.viewmodels.PlacesViewModel

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
            Log.d("PlacesData", places.toString())
        }

        goToPlacesListFragment(PlacesListFragment())
    }

        placesViewModel.getPlaceById("2257")
        placesViewModel.placeById.observe(viewLifecycleOwner) { placeById ->
            Log.d("PlaceId", placeById.toString())
        }
    }

    private fun btnToPlacesList() {
        requireActivity().supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<PlacesListFragment>(R.id.fragmentContainer)
            addToBackStack(null)
        }
    }

    private fun goToPlacesListFragment(fragment: Fragment) {
        //  requireActivity() gets the reference from mainActivity

        binding.btnNatureId.setOnClickListener {
             btnToPlacesList()
            }
        binding.btnMuseumsId.setOnClickListener {
            btnToPlacesList()
        }
        binding.btnLandmarksId.setOnClickListener {
            btnToPlacesList()
        }
        binding.btnRestaurantsId.setOnClickListener {
            btnToPlacesList()
        }
        binding.btnBeachesId.setOnClickListener {
            btnToPlacesList()
        }
        binding.btnShopsId.setOnClickListener {
            btnToPlacesList()
            }
        }
    }

