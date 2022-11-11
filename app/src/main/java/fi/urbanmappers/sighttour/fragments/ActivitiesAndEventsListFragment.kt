package fi.urbanmappers.sighttour.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import fi.urbanmappers.sighttour.R
import fi.urbanmappers.sighttour.adapters.ActivitiesListRecyclerViewAdapter
import fi.urbanmappers.sighttour.adapters.EventsListRecyclerViewAdapter
import fi.urbanmappers.sighttour.databinding.FragmentActivitiesAndEventsListBinding
import fi.urbanmappers.sighttour.utils.ActivitiesAndEventsCategory
import fi.urbanmappers.sighttour.utils.TagCategories
import fi.urbanmappers.sighttour.viewmodels.ActivitiesViewModel
import fi.urbanmappers.sighttour.viewmodels.EventsViewModel

class ActivitiesAndEventsListFragment : Fragment(), ActivitiesListRecyclerViewAdapter.ActivityItemClickListener,
    EventsListRecyclerViewAdapter.EventItemClickListener {

    private lateinit var binding: FragmentActivitiesAndEventsListBinding
    private val activitiesViewModel: ActivitiesViewModel by viewModels()
    private val eventsViewModel: EventsViewModel by viewModels()
    private var category: ActivitiesAndEventsCategory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val categoryArg = requireArguments().getString("category")
        if (categoryArg != null) {
            category = ActivitiesAndEventsCategory.valueOf(categoryArg)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentActivitiesAndEventsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.activitiesEventsListTitleTextView.text = getString(R.string.activities_and_events_list_title, category.toString())
        binding.activitiesEventsListRecyclerView.layoutManager = LinearLayoutManager(this.context)
        initializeActivitiesRecyclerView()
        bindTabLayoutClicks()
    }

    private fun bindTabLayoutClicks() {
        binding.activitiesEventsTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab == binding.activitiesEventsTabLayout.getTabAt(0)) {
                    initializeActivitiesRecyclerView()
                } else {
                    initializeEventsRecyclerView()
                }
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun initializeActivitiesRecyclerView() {
        val tags = when (category) {
            ActivitiesAndEventsCategory.Sports -> TagCategories.activitiesSportsTags
            ActivitiesAndEventsCategory.Nature -> TagCategories.activitiesNatureTags
            ActivitiesAndEventsCategory.Music -> TagCategories.activitiesMusicTags
            ActivitiesAndEventsCategory.Sightseeing -> TagCategories.activitiesSightseeingTags
            ActivitiesAndEventsCategory.Arts -> TagCategories.activitiesArtsTags
            ActivitiesAndEventsCategory.Nightlife -> TagCategories.activitiesNightlifeTags
            else -> null
        }

        if (tags != null) {
            activitiesViewModel.getActivities(tags)
            activitiesViewModel.activities.observe(viewLifecycleOwner) { activitiesData ->
                binding.activitiesEventsListRecyclerView.adapter = ActivitiesListRecyclerViewAdapter(activitiesData.rows, this)
            }
        }
    }

    private fun initializeEventsRecyclerView() {
        val tags = when (category) {
            ActivitiesAndEventsCategory.Sports -> TagCategories.eventsSportsTags
            ActivitiesAndEventsCategory.Nature -> TagCategories.eventsNatureTags
            ActivitiesAndEventsCategory.Music -> TagCategories.eventsMusicTags
            ActivitiesAndEventsCategory.Sightseeing -> TagCategories.eventsSightseeingTags
            ActivitiesAndEventsCategory.Arts -> TagCategories.eventsArtsTags
            ActivitiesAndEventsCategory.Nightlife -> TagCategories.eventsNightlifeTags
            else -> null
        }

        if (tags != null) {
            eventsViewModel.getEvents(tags)
            eventsViewModel.events.observe(viewLifecycleOwner) { eventsData ->
                binding.activitiesEventsListRecyclerView.adapter = EventsListRecyclerViewAdapter(eventsData.data, this)
            }
        }
    }

    override fun onActivityItemClick(activityId: String) {

    }

    override fun onEventItemClick(eventId: String) {

    }
}
