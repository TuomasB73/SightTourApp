package fi.urbanmappers.sighttour.fragments

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import fi.urbanmappers.sighttour.R
import fi.urbanmappers.sighttour.databinding.FragmentTourRouteMapBinding
import fi.urbanmappers.sighttour.datamodels.TripStage
import fi.urbanmappers.sighttour.viewmodels.ToursViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

class TourRouteMapFragment : Fragment(), LocationListener {
    private lateinit var binding: FragmentTourRouteMapBinding
    private var tourId: String? = null
    private val toursViewModel: ToursViewModel by viewModels()
    private lateinit var lm: LocationManager
    private lateinit var myLocationMarker: Marker
    private lateinit var roadManager: RoadManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tourIdArg = requireArguments().getString("tourId")
        tourId = tourIdArg
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTourRouteMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roadManager = OSRMRoadManager(requireContext(), "SIGHTour")

        checkPermissionAndInitLocationTracking()
        initializeMap()

        val tourId = tourId
        if (tourId != null) {
            toursViewModel.getTourById(tourId)
            toursViewModel.tourById.observe(viewLifecycleOwner) { tour ->
                binding.tourRouteTitleTextView.text = tour.name
                lifecycleScope.launch(Dispatchers.IO) {
                    setTripRouteOnMap(tour.tripStages)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        lm.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        myLocationMarker.position = GeoPoint(location.latitude, location.longitude, location.altitude)
        binding.tourRouteMap.overlays.add(myLocationMarker)
        myLocationMarker.setInfoWindow(null)
        binding.tourRouteMap.invalidate()
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
            1,
            1f,
            this
        )
    }

    private fun initializeMap() {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))

        binding.tourRouteMap.setTileSource(TileSourceFactory.MAPNIK)
        binding.tourRouteMap.setMultiTouchControls(true)
        binding.tourRouteMap.controller.setZoom(17.0)

        myLocationMarker = Marker(binding.tourRouteMap)
        myLocationMarker.icon = AppCompatResources.getDrawable(
            requireContext(),
            R.drawable.ic_baseline_location_on_24
        )
        myLocationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
    }

    private suspend fun setTripRouteOnMap(tripStages: List<TripStage>) {
        val waypoints = ArrayList<GeoPoint>()

        for(i in tripStages.indices) {
            val startPoint = GeoPoint(tripStages[i].startLocation.lat, tripStages[i].startLocation.lon)
            waypoints.add(startPoint)
            if (i == tripStages.size - 1) {
                val endPoint = GeoPoint(tripStages[i].endLocation.lat, tripStages[i].endLocation.lon)
                waypoints.add(endPoint)
            }
        }

        val road = roadManager.getRoad(waypoints)
        val roadOverlay = RoadManager.buildRoadOverlay(road)
        roadOverlay.width = 12f
        binding.tourRouteMap.overlays.add(roadOverlay)
        binding.tourRouteMap.invalidate()
        withContext(Dispatchers.Main) {
            binding.tourRouteMap.controller.setCenter(GeoPoint(tripStages.first().startLocation.lat, tripStages.first().startLocation.lon))
        }
    }
}
