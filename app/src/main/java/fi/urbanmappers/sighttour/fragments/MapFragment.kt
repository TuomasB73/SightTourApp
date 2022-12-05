package fi.urbanmappers.sighttour.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import fi.urbanmappers.sighttour.R
import fi.urbanmappers.sighttour.databinding.FragmentMapBinding
import fi.urbanmappers.sighttour.datamodels.Event
import fi.urbanmappers.sighttour.utils.TagCategories
import fi.urbanmappers.sighttour.viewmodels.EventsViewModel
import fi.urbanmappers.sighttour.viewmodels.PlacesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.osmdroid.bonuspack.routing.OSRMRoadManager
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
                binding.mapTitleTextView.text = place.name.en ?: place.name.fi ?: ""
                showIndividualPlaceOrEventMarker(
                    place.name.en ?: place.name.fi,
                    place.location.address.streetAddress,
                    place.description.intro,
                    place.location.lat,
                    place.location.lon
                )
            }
        } else if (eventId != null) {
            eventsViewModel.getEventById(eventId)
            eventsViewModel.eventById.observe(viewLifecycleOwner) { event ->
                binding.mapTitleTextView.text = event.name.en ?: event.name.fi ?: ""
                val infoText =
                    if (event.eventDates?.startingDay != null && event.eventDates.endingDay != null)
                        "${event.eventDates.startingDay} - ${event.eventDates.endingDay}" else null
                showIndividualPlaceOrEventMarker(
                    event.name.en ?: event.name.fi,
                    infoText,
                    event.description.intro,
                    event.location.lat,
                    event.location.lon
                )
            }
        } else {
            binding.mapTitleTextView.text = getString(R.string.map_title_text)
        }

        binding.myLocationButton.setOnClickListener {
            binding.map.controller.setCenter(
                myLocationMarker.position
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        lm.removeUpdates(this)
        myLocationFound = false
    }

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude
        longitude = location.longitude
        myLocationMarker.position =
            GeoPoint(location.latitude, location.longitude, location.altitude)
        myLocationMarker.setInfoWindow(null)
        binding.map.overlays.add(myLocationMarker)
        binding.map.invalidate()
        if (!myLocationFound) {
            myLocationFound = true
            binding.myLocationButton.visibility = View.VISIBLE
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

    @SuppressLint("MissingPermission")
    private fun checkPermissionAndInitLocationTracking() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                    lm = requireActivity().getSystemService(Context.LOCATION_SERVICE) as
                            LocationManager
                    lm.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        5000,
                        1f,
                        this
                    )
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                }
                else -> {
                    // No location access granted.
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun initializeMap() {
        Configuration.getInstance()
            .load(context, PreferenceManager.getDefaultSharedPreferences(context))

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
        binding.mapProgressIndicator.visibility = View.VISIBLE
        eventsViewModel.getEvents(tags = tagCategory, distance = "$latitude,$longitude,40")
        eventsViewModel.events.observe(viewLifecycleOwner) { eventsData ->
            setEventMarkersOnMap(eventsData.data, tagCategory)
            binding.mapProgressIndicator.visibility = View.GONE
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
                    TagCategories.eventsSportsTags -> R.drawable.pin_sports
                    TagCategories.eventsNatureTags -> R.drawable.pin_nature
                    TagCategories.eventsMusicTags -> R.drawable.pin_music
                    TagCategories.eventsSightseeingTags -> R.drawable.pin_sightseeing
                    TagCategories.eventsArtsTags -> R.drawable.pin_arts
                    TagCategories.eventsNightlifeTags -> R.drawable.pin_nightlife
                    else -> R.drawable.ic_baseline_location_on_orange_48
                }
                eventMarker.icon = AppCompatResources.getDrawable(
                    requireContext(),
                    markerDrawable
                )
                eventMarker.setInfoWindow(null)
                eventMarker.setOnMarkerClickListener { _, _ ->
                    openEventDialog(
                        event.id,
                        event.name.en ?: event.name.fi ?: "",
                        event.description.intro ?: ""
                    )
                    true
                }
                binding.map.overlays.add(eventMarker)
                eventMarkersOnMap.add(eventMarker)
            }
        }
        binding.map.invalidate()
    }

    private fun openEventDialog(eventId: String, title: String, description: String) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(title)
            .setMessage(description)
            .setPositiveButton(
                R.string.open_details_dialog_text
            ) { _, _ ->
                val bundle = bundleOf("eventId" to eventId)

                requireActivity().supportFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    setReorderingAllowed(true)
                    add<IndividualPlaceAndEventFragment>(R.id.fragmentContainer, args = bundle)
                    addToBackStack(null)
                }
            }
            .setNegativeButton(
                R.string.close_dialog_text
            ) { _, _ -> }
        builder.create()
        builder.show()
    }

    private fun showIndividualPlaceOrEventMarker(
        title: String?, infoText: String?, description: String?, lat: Double?, lon: Double?
    ) {
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
