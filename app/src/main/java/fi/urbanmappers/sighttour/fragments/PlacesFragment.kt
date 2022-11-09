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
        goToPlacesListFragment(PlacesListFragment())
        //binding.placeholderTextView.text = "Test"
    }

    private fun logPlacesData(places: PlacesData) {
        places.data.forEach {
            Log.d("PlacesData", it.name.en)
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

