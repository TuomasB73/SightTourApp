package fi.urbanmappers.sighttour.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import fi.urbanmappers.sighttour.databinding.FragmentIndividualPlaceAndEventBinding
import fi.urbanmappers.sighttour.viewmodels.EventsViewModel
import fi.urbanmappers.sighttour.viewmodels.PlacesViewModel

class IndividualPlaceAndEventFragment : Fragment() {
    private lateinit var binding: FragmentIndividualPlaceAndEventBinding
    private var placeId: String? = null
    private var eventId: String? = null
    private val placesViewModel: PlacesViewModel by viewModels()
    private val eventsViewModel: EventsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val placeIdArg = requireArguments().getString("placeId")
        val eventIdArg = requireArguments().getString("eventId")
        placeId = placeIdArg
        eventId = eventIdArg
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentIndividualPlaceAndEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val placeId = placeId
        val eventId = eventId
        if (placeId != null) {
            placesViewModel.getPlaceById(placeId)
            placesViewModel.placeById.observe(viewLifecycleOwner) { place ->
                setDataToUI(place.name.en ?: place.name.fi ?: "", place.description.body ?: "",
                    place.description.images?.first()?.url ?: "", null)
            }
        } else if (eventId != null) {
            eventsViewModel.getEventById(eventId)
            eventsViewModel.eventById.observe(viewLifecycleOwner) { event ->
                setDataToUI(event.name.en ?: event.name.fi ?: "", event.description.body ?: "",
                    event.description.images?.first()?.url ?: "", "Event dates: ${event.eventDates.startingDay ?: ""} - " +
                            (event.eventDates.endingDay ?: "")
                )
            }
        }
    }

    private fun setDataToUI(title: String?, description: String?, imageUrl: String?, eventDates: String?) {
        binding.individualPlaceAndEventTitleTextView.text = title
        binding.individualPlaceAndEventDescriptionTextView.text = description

        if (imageUrl != null) {
            Glide.with(requireContext()).load(imageUrl).centerCrop().into(binding.individualPlaceAndEventImageView)
        }

        if (eventDates != null) {
            binding.eventDatesTextView.visibility = View.VISIBLE
            binding.eventDatesTextView.text = eventDates
        }
    }
}
