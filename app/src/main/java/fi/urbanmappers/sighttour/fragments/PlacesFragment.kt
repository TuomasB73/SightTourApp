package fi.urbanmappers.sighttour.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import fi.urbanmappers.sighttour.R
import fi.urbanmappers.sighttour.databinding.FragmentPlacesBinding
import fi.urbanmappers.sighttour.utils.PlacesCategory
import fi.urbanmappers.sighttour.viewmodels.PlacesViewModel

class PlacesFragment : Fragment() {
    private lateinit var binding: FragmentPlacesBinding

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

        bindCategoryButtonClicks()
    }

    private fun bindCategoryButtonClicks() {
        binding.btnNatureId.setOnClickListener {
            navigateToPlacesListFragment(PlacesCategory.Nature)
        }
        binding.btnMuseumsId.setOnClickListener {
            navigateToPlacesListFragment(PlacesCategory.Museums)
        }
        binding.btnLandmarksId.setOnClickListener {
            navigateToPlacesListFragment(PlacesCategory.Landmarks)
        }
        binding.btnRestaurantsId.setOnClickListener {
            navigateToPlacesListFragment(PlacesCategory.Restaurants)
        }
        binding.btnBeachesId.setOnClickListener {
            navigateToPlacesListFragment(PlacesCategory.Beaches)
        }
        binding.btnShopsId.setOnClickListener {
            navigateToPlacesListFragment(PlacesCategory.Shops)
        }
    }

    private fun navigateToPlacesListFragment(placesCategory: PlacesCategory) {
        val bundle = bundleOf("category" to placesCategory.toString())

        requireActivity().supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<PlacesListFragment>(R.id.fragmentContainer, args = bundle)
            addToBackStack(null)
        }
    }
}
