package fi.urbanmappers.sighttour.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import fi.urbanmappers.sighttour.R
import androidx.fragment.app.viewModels
import fi.urbanmappers.sighttour.databinding.FragmentActivitiesAndEventsBinding
import fi.urbanmappers.sighttour.utils.ActivitiesAndEventsCategory
import fi.urbanmappers.sighttour.viewmodels.ActivitiesViewModel

class ActivitiesAndEventsFragment : Fragment() {
    private lateinit var binding: FragmentActivitiesAndEventsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentActivitiesAndEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindCategoryButtonClicks()
    }

    private fun bindCategoryButtonClicks() {
        binding.btnSportsId.setOnClickListener {
            navigateToActivitiesListFragment(ActivitiesAndEventsCategory.Sports)
        }
        binding.btnNatureEvId.setOnClickListener {
            navigateToActivitiesListFragment(ActivitiesAndEventsCategory.Nature)
        }
        binding.btnMusicId.setOnClickListener {
            navigateToActivitiesListFragment(ActivitiesAndEventsCategory.Music)
        }
        binding.btnSightseeingId.setOnClickListener {
            navigateToActivitiesListFragment(ActivitiesAndEventsCategory.Sightseeing)
        }
        binding.btnArtsId.setOnClickListener {
            navigateToActivitiesListFragment(ActivitiesAndEventsCategory.Arts)
        }
        binding.btnNightlifeId.setOnClickListener {
            navigateToActivitiesListFragment(ActivitiesAndEventsCategory.Nightlife)
        }
    }

    private fun navigateToActivitiesListFragment(activitiesAndEventsCategory: ActivitiesAndEventsCategory) {
        val bundle = bundleOf("category" to activitiesAndEventsCategory.toString())

        requireActivity().supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<ActivitiesAndEventsListFragment>(R.id.fragmentContainer, args = bundle)
            addToBackStack(null)
        }
    }
}
