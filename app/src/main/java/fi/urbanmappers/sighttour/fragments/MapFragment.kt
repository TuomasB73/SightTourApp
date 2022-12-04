package fi.urbanmappers.sighttour.fragments

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import fi.urbanmappers.sighttour.R
import fi.urbanmappers.sighttour.databinding.FragmentMapBinding
import fi.urbanmappers.sighttour.datamodels.Event
import fi.urbanmappers.sighttour.utils.TagCategories
import fi.urbanmappers.sighttour.viewmodels.EventsViewModel
import fi.urbanmappers.sighttour.viewmodels.PlacesViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

class MapFragment : Fragment(), LocationListener {
    private lateinit var binding: FragmentMapBinding
    private lateinit var myLocationMarker: Marker
    private var myLocationFound = false
    private var latitude = 0.00
    private var longitude = 0.00
    private lateinit var lm: LocationManager
    private val eventsViewModel: EventsViewModel by viewModels()
    private val placesViewModel: PlacesViewModel by viewModels()
    private var placeId: String? = null
    private var eventId: String? = null
    private var eventMarkersOnMap = mutableListOf<Marker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val placeIdArg = arguments?.getString("placeId")
        val eventIdArg = arguments?.getString("eventId")
        placeId = placeIdArg
        eventId = eventIdArg
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermissionAndInitLocationTracking()
        initializeMap()

        val placeId = placeId
        val eventId = eventId
        if (placeId != null) {
            placesViewModel.getPlaceById(placeId)
            placesViewModel.placeById.observe(viewLifecycleOwner) { place ->
                showIndividualPlaceOrEventMarker("place", place.id, place.name.en ?: place.name.fi,
                    place.location.address.streetAddress, place.description.intro, place.location.lat, place.location.lon)
            }
        } else if (eventId != null) {
            eventsViewModel.getEventById(eventId)
            eventsViewModel.eventById.observe(viewLifecycleOwner) { event ->
                val infoText = if (event.eventDates?.startingDay != null && event.eventDates.endingDay != null)
                    "${event.eventDates.startingDay} - ${event.eventDates.endingDay}" else null
                showIndividualPlaceOrEventMarker("event", event.id, event.name.en ?: event.name.fi,
                    infoText, event.description.intro, event.location.lat, event.location.lon)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        lm.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude
        longitude = location.longitude
        myLocationMarker.position = GeoPoint(location.latitude, location.longitude, location.altitude)
        myLocationMarker.setInfoWindow(null)
        binding.map.overlays.add(myLocationMarker)
        binding.map.invalidate()
        if (!myLocationFound) {
            myLocationFound = true
            // If the map is opened to show an individual place or event on the map, it won't be centered
            // on the user's location, and the events fab menu won't be made visible.
            if (placeId == null && eventId == null) {
                binding.map.controller.setCenter(myLocationMarker.position)
                binding.floatingActionsMenu.visibility = View.VISIBLE
                binding.fabSport.setOnClickListener {
                    getEventsData(TagCategories.eventsSportsTags)
                }
                binding.fabNatureEv.setOnClickListener {
                    getEventsData(TagCategories.eventsNatureTags)
                }
                binding.fabMusic.setOnClickListener {
                    getEventsData(TagCategories.eventsMusicTags)
                }
                binding.fabSightseeing.setOnClickListener {
                    getEventsData(TagCategories.eventsSightseeingTags)
                }
                binding.fabArts.setOnClickListener {
                    getEventsData(TagCategories.eventsArtsTags)
                }
                binding.fabNightlife.setOnClickListener {
                    getEventsData(TagCategories.eventsNightlifeTags)
                }
            }
        }
    }

    private fun checkPermissionAndInitLocationTracking() {
        if ((ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
                    PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
        }

        lm = requireActivity().getSystemService(Context.LOCATION_SERVICE) as
                LocationManager
        lm.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            5000,
            1f,
            this
        )
    }
    
    private fun initializeMap() {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))

        binding.map.setTileSource(TileSourceFactory.MAPNIK)
        binding.map.setMultiTouchControls(true)
        binding.map.controller.setZoom(17.0)

        myLocationMarker = Marker(binding.map)
        myLocationMarker.icon = AppCompatResources.getDrawable(
            requireContext(),
            R.drawable.ic_baseline_location_on_48
        )
        myLocationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
    }

    private fun getEventsData(tagCategory: String) {
        binding.floatingActionsMenu.collapse()
        eventsViewModel.getEvents(tags = tagCategory, distance = "$latitude,$longitude,5")
        eventsViewModel.events.observe(viewLifecycleOwner) { eventsData ->
            setEventMarkersOnMap(eventsData.data, tagCategory)
        }
    }

    private fun setEventMarkersOnMap(events: List<Event>, tagCategory: String) {
        binding.map.overlays.removeAll(eventMarkersOnMap)
        eventMarkersOnMap.clear()
        events.forEach { event ->
            if (event.location.lat != null && event.location.lon != null) {
                val eventMarker = Marker(binding.map)
                eventMarker.position = GeoPoint(event.location.lat, event.location.lon)
                val markerDrawable = when (tagCategory) {
                    TagCategories.eventsSportsTags -> R.drawable.fab_vector_sports
                    TagCategories.eventsNatureTags -> R.drawable.fab_vector_nature
                    TagCategories.eventsMusicTags -> R.drawable.fab_vector_music
                    TagCategories.eventsSightseeingTags -> R.drawable.fab_vector_sightseeing
                    TagCategories.eventsArtsTags -> R.drawable.fab_vector_arts
                    TagCategories.eventsNightlifeTags -> R.drawable.fab_vector_nightlife
                    else -> R.drawable.ic_baseline_location_on_orange_48
                }
                eventMarker.icon = AppCompatResources.getDrawable(
                    requireContext(),
                    markerDrawable
                )
                eventMarker.title = event.name.en ?: event.name.fi ?: ""
                if (event.eventDates?.startingDay != null && event.eventDates.endingDay != null) {
                    eventMarker.snippet = "${event.eventDates.startingDay} - ${event.eventDates.endingDay}"
                }
                eventMarker.subDescription = event.description.intro
                binding.map.overlays.add(eventMarker)
                eventMarkersOnMap.add(eventMarker)
            }
        }
        binding.map.invalidate()
    }

    private fun showIndividualPlaceOrEventMarker(itemType: String, id: String, title: String?, infoText: String?,
    description: String?, lat: Double?, lon: Double?) {
        if (lat != null && lon != null) {
            val marker = Marker(binding.map)
            marker.position = GeoPoint(lat, lon)
            marker.icon = AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_location_on_orange_48
            )
            if (title != null) marker.title = title
            if (infoText != null) marker.snippet = infoText
            if (description != null) marker.subDescription = description.take(120).plus("...")
            binding.map.overlays.add(marker)
            binding.map.invalidate()
            binding.map.controller.setCenter(marker.position)
        }
    }
}
