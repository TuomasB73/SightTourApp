package fi.urbanmappers.sighttour.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import fi.urbanmappers.sighttour.R
import fi.urbanmappers.sighttour.adapters.PlacesListRecyclerViewAdapter
import fi.urbanmappers.sighttour.databinding.FragmentPlacesListBinding
import fi.urbanmappers.sighttour.utils.PlacesCategory
import fi.urbanmappers.sighttour.utils.TagCategories
import fi.urbanmappers.sighttour.viewmodels.PlacesViewModel

class PlacesListFragment : Fragment(), PlacesListRecyclerViewAdapter.PlaceItemClickListener {
    private lateinit var binding: FragmentPlacesListBinding
    private val placesViewModel: PlacesViewModel by viewModels()
    private var category: PlacesCategory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val categoryArg = requireArguments().getString("category")
        if (categoryArg != null) {
            category = PlacesCategory.valueOf(categoryArg)
        }
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

        binding.placesListTitleTextView.text = getString(R.string.places_list_title, category.toString())
        initializePlacesRecyclerView()
    }

    private fun initializePlacesRecyclerView() {
        binding.placesListRecyclerView.layoutManager = LinearLayoutManager(this.context)

        val tags = when (category) {
            PlacesCategory.Nature -> TagCategories.placesNatureTags
            PlacesCategory.Museums -> TagCategories.placesMuseumsTags
            PlacesCategory.Landmarks -> TagCategories.placesLandmarksTags
            PlacesCategory.Restaurants -> TagCategories.placesRestaurantsTags
            PlacesCategory.Beaches -> TagCategories.placesBeachesTags
            PlacesCategory.Shops -> TagCategories.placesShopsTags
            else -> null
        }

        if (tags != null) {
            placesViewModel.getPlaces(tags)
            placesViewModel.places.observe(viewLifecycleOwner) { placesData ->
                binding.placesListRecyclerView.adapter = PlacesListRecyclerViewAdapter(placesData.data, this, requireContext())
            }
        }
    }

    override fun onPlaceItemClick(placeId: String) {

        val bundle = bundleOf("placeId" to placeId)

        requireActivity().supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<PlacesIndividualFragment>(R.id.fragmentContainer, args = bundle)
            addToBackStack(null)
        }
    }
}