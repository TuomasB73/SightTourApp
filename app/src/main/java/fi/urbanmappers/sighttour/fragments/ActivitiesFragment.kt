package fi.urbanmappers.sighttour.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import fi.urbanmappers.sighttour.R
import fi.urbanmappers.sighttour.databinding.FragmentActivitiesBinding
import fi.urbanmappers.sighttour.databinding.FragmentPlacesBinding
import androidx.fragment.app.viewModels
import fi.urbanmappers.sighttour.viewmodels.ActivitiesViewModel
import fi.urbanmappers.sighttour.viewmodels.EventsViewModel

class ActivitiesFragment : Fragment() {
    private lateinit var binding: FragmentActivitiesBinding
    private val eventsViewModel: EventsViewModel by viewModels()
    private val activitiesViewModel: ActivitiesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentActivitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        goToActivitiesListFragment(ActivitiesListFragment())

        eventsViewModel.getEvents(limit = 3)
        eventsViewModel.events.observe(viewLifecycleOwner) { events ->
            Log.d("EventsData", events.toString())
        }

        eventsViewModel.getEventById("linkedevents:agg-195")
        eventsViewModel.eventById.observe(viewLifecycleOwner) { eventById ->
            Log.d("EventId", eventById.toString())
        }

        activitiesViewModel.getActivities(limit = 3)
        activitiesViewModel.activities.observe(viewLifecycleOwner) { activities ->
            Log.d("ActivitiesData", activities.toString())
        }

        activitiesViewModel.getActivityById("418816d7-07b7-4501-8139-4fe9c36e6aae")
        activitiesViewModel.activityById.observe(viewLifecycleOwner) { activityById ->
            Log.d("ActivityId", activityById.toString())

        }
    }

    private fun btnToActivitiesList() {
        requireActivity().supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<ActivitiesListFragment>(R.id.fragmentContainer)
            addToBackStack(null)
        }
    }

    private fun goToActivitiesListFragment(fragment: Fragment) {
        //  requireActivity() gets the reference from mainActivity

        binding.btnSportsId.setOnClickListener {
            btnToActivitiesList()
        }
        binding.btnNatureEvId.setOnClickListener {
            btnToActivitiesList()
        }
        binding.btnMusicId.setOnClickListener {
            btnToActivitiesList()
        }
        binding.btnSightseeingId.setOnClickListener {
            btnToActivitiesList()
        }
        binding.btnArtsId.setOnClickListener {
            btnToActivitiesList()
        }
        binding.btnNightlifeId.setOnClickListener {
            btnToActivitiesList()
        }
    }
}

