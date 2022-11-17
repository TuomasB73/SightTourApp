package fi.urbanmappers.sighttour.fragments

import android.os.Bundle
import android.text.Html
import android.util.Log
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
                val title = place.name.en ?: place.name.fi ?: ""
                val description = place.description.body ?: ""
                val imageUrl = if (place.description.images != null && place.description.images.isNotEmpty())
                    place.description.images.first().url else ""
                val eventDates = null
                setDataToUI(title, description, imageUrl, eventDates)
            }
        } else if (eventId != null) {
            eventsViewModel.getEventById(eventId)
            eventsViewModel.eventById.observe(viewLifecycleOwner) { event ->
                val title = event.name.en ?: event.name.fi ?: ""
                val description = Html.fromHtml(event.description.body).toString() ?: ""
                val imageUrl = if (event.description.images != null && event.description.images.isNotEmpty())
                    event.description.images.first().url else ""
                val eventDates = event.eventDates?.endingDay ?: ""
                setDataToUI(title, description, imageUrl, eventDates)
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
