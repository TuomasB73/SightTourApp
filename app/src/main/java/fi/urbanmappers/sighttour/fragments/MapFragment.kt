package fi.urbanmappers.sighttour.fragments

import android.content.Context
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
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import fi.urbanmappers.sighttour.R
import fi.urbanmappers.sighttour.databinding.FragmentMapBinding
import fi.urbanmappers.sighttour.datamodels.Event
import fi.urbanmappers.sighttour.utils.TagCategories
import fi.urbanmappers.sighttour.viewmodels.EventsViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

class MapFragment : Fragment(), LocationListener {
    private lateinit var binding: FragmentMapBinding
    private lateinit var myLocationMarker: Marker
    private var latitude = 0.00
    private var longitude = 0.00
    private lateinit var lm: LocationManager
    private val eventsViewModel: EventsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        binding.fabSport.setOnClickListener {
            getEventsData(TagCategories.eventsSportsTags)
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
        binding.map.controller.setCenter(GeoPoint(location.latitude, location.longitude))
        myLocationMarker.setInfoWindow(null)
        binding.map.overlays.add(myLocationMarker)
        binding.map.invalidate()
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
        eventsViewModel.getEvents(tags = tagCategory, distance = "$latitude,$longitude,5")
        eventsViewModel.events.observe(viewLifecycleOwner) { eventsData ->
            setEventMarkersOnMap(eventsData.data)
        }
    }

    private fun setEventMarkersOnMap(events: List<Event>) {
        events.forEach { event ->
            if (event.location.lat != null && event.location.lon != null) {
                val eventMarker = Marker(binding.map)
                eventMarker.position = GeoPoint(event.location.lat, event.location.lon)
                eventMarker.icon = AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.fab_vector_nature
                )
                eventMarker.title = event.name.en ?: event.name.fi ?: ""
                eventMarker.snippet = "${event.eventDates?.startingDay} - ${event.eventDates?.endingDay}"
                eventMarker.subDescription = event.description.intro
                // TODO: Do this maybe later
                /*eventMarker.setOnMarkerClickListener { marker, mapView ->
                    event.id
                }*/
                binding.map.overlays.add(eventMarker)
            }
        }
        binding.map.invalidate()
    }
}
