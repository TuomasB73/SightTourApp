package fi.urbanmappers.sighttour.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import fi.urbanmappers.sighttour.R
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
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            setReorderingAllowed(true)
            add<ActivitiesAndEventsListFragment>(R.id.fragmentContainer, args = bundle)
            addToBackStack(null)
        }
    }
}
