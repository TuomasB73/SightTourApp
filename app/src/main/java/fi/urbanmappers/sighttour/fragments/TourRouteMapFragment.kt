package fi.urbanmappers.sighttour.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
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
import fi.urbanmappers.sighttour.datamodels.TripStageLocation
import fi.urbanmappers.sighttour.utils.ToursMobilityMethod
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
import org.osmdroid.views.overlay.Polyline

class TourRouteMapFragment : Fragment(), LocationListener {
    private lateinit var binding: FragmentTourRouteMapBinding
    private var tourId: String? = null
    private val toursViewModel: ToursViewModel by viewModels()
    private lateinit var lm: LocationManager
    private lateinit var myLocationMarker: Marker
    private var myLocationFound = false
    private lateinit var roadManager: RoadManager
    private var routeToTourStartOverlay: Polyline? = null

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

                    withContext(Dispatchers.Main) {
                        binding.routeToTourStartButton.setOnClickListener {
                            openMobilityMethodDialog(tour.tripStages.first { it.tourStageSequence == 1 }.startLocation)
                        }
                    }
                }
            }
        }

        binding.myLocationButton.setOnClickListener {
            binding.tourRouteMap.controller.setCenter(
                GeoPoint(myLocationMarker.position.latitude, myLocationMarker.position.longitude)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        lm.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        myLocationMarker.position =
            GeoPoint(location.latitude, location.longitude, location.altitude)
        binding.tourRouteMap.overlays.add(myLocationMarker)
        myLocationMarker.setInfoWindow(null)
        binding.tourRouteMap.invalidate()
        if (!myLocationFound) {
            myLocationFound = true
            binding.myLocationButton.visibility = View.VISIBLE
            binding.routeToTourStartButton.visibility = View.VISIBLE
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
        Configuration.getInstance()
            .load(context, PreferenceManager.getDefaultSharedPreferences(context))

        binding.tourRouteMap.setTileSource(TileSourceFactory.MAPNIK)
        binding.tourRouteMap.setMultiTouchControls(true)
        binding.tourRouteMap.controller.setZoom(17.0)

        myLocationMarker = Marker(binding.tourRouteMap)
        myLocationMarker.icon = AppCompatResources.getDrawable(
            requireContext(),
            R.drawable.ic_baseline_location_on_48
        )
        myLocationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
    }

    private suspend fun setTripRouteOnMap(tripStages: List<TripStage>) {
        val polylineColors =
            listOf(Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.RED, Color.YELLOW)
        var colorIndex = 0

        tripStages.forEach { tripStage ->
            val waypoints = ArrayList<GeoPoint>()
            val startPoint = GeoPoint(tripStage.startLocation.lat, tripStage.startLocation.lon)
            waypoints.add(startPoint)
            val endPoint = GeoPoint(tripStage.endLocation.lat, tripStage.endLocation.lon)
            waypoints.add(endPoint)

            val routeMean = when (tripStage.mobilityMethod) {
                ToursMobilityMethod.Walking -> OSRMRoadManager.MEAN_BY_FOOT
                ToursMobilityMethod.Bicycling, ToursMobilityMethod.EScooter -> OSRMRoadManager.MEAN_BY_BIKE
                else -> OSRMRoadManager.MEAN_BY_CAR
            }
            (roadManager as OSRMRoadManager).setMean(routeMean)

            val road = roadManager.getRoad(waypoints)
            val roadOverlay = RoadManager.buildRoadOverlay(road)
            roadOverlay.outlinePaint.color = polylineColors[colorIndex]
            colorIndex++
            roadOverlay.outlinePaint.strokeWidth = 18f
            binding.tourRouteMap.overlays.add(roadOverlay)

            val isLastStage = tripStages.last() == tripStage
            val tourEndPoint = if (isLastStage) endPoint else null
            setTripStageMarker(tripStage, startPoint, tourEndPoint)
        }

        binding.tourRouteMap.invalidate()

        withContext(Dispatchers.Main) {
            binding.tourRouteMap.controller.setCenter(
                GeoPoint(
                    tripStages.first { it.tourStageSequence == 1 }.startLocation.lat,
                    tripStages.first { it.tourStageSequence == 1 }.startLocation.lon
                )
            )
        }
    }

    private fun setTripStageMarker(tripStage: TripStage, startPoint: GeoPoint, tourEndPoint: GeoPoint?) {
        val tripStageMarkerDrawable = when (tripStage.mobilityMethod) {
            ToursMobilityMethod.Walking -> R.drawable.walking_icon_small
            ToursMobilityMethod.Bicycling -> R.drawable.bicycle_icon_small
            ToursMobilityMethod.EScooter -> R.drawable.escooter_icon_small
            ToursMobilityMethod.Bus -> R.drawable.bus_icon_small
            ToursMobilityMethod.Tram -> R.drawable.tram_icon_small
            ToursMobilityMethod.Metro -> R.drawable.metro_icon_small
            ToursMobilityMethod.Ferry -> R.drawable.ferry_icon_small
        }
        val tripStageMarkerIcon = AppCompatResources.getDrawable(
            requireContext(),
            tripStageMarkerDrawable
        )
        val tripStageMarker = Marker(binding.tourRouteMap)
        tripStageMarker.position = startPoint
        tripStageMarker.icon = tripStageMarkerIcon
        tripStageMarker.title = getString(
            R.string.tour_start_end_points_text, tripStage.startLocation.placeName,
            tripStage.endLocation.placeName
        )
        tripStageMarker.snippet = tripStage.mobilityMethod.toString()
        tripStageMarker.subDescription = getString(
            R.string.distance_duration_text,
            tripStage.lengthInKm.toString(), tripStage.durationInMinutes.toString()
        )
        binding.tourRouteMap.overlays.add(tripStageMarker)

        if (tripStage.tourStageSequence == 1) {
            val tripStartMarker = Marker(binding.tourRouteMap)
            tripStartMarker.position = GeoPoint(startPoint.latitude - 0.0001, startPoint.longitude)
            tripStartMarker.icon = AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.tour_start
            )
            tripStartMarker.setInfoWindow(null)
            binding.tourRouteMap.overlays.add(tripStartMarker)
        }

        if (tourEndPoint != null) {
            val tripEndMarker = Marker(binding.tourRouteMap)
            tripEndMarker.position = tourEndPoint
            tripEndMarker.icon = AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.tour_end
            )
            tripEndMarker.setInfoWindow(null)
            binding.tourRouteMap.overlays.add(tripEndMarker)
        }
    }

    private fun openMobilityMethodDialog(tourStartLocation: TripStageLocation) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(R.string.select_mobility_method_text)
            .setItems(
                R.array.route_mobility_methods_array
            ) { _, which ->
                val mobilityMean = when (which) {
                    0 -> OSRMRoadManager.MEAN_BY_FOOT
                    1 -> OSRMRoadManager.MEAN_BY_BIKE
                    else -> OSRMRoadManager.MEAN_BY_CAR
                }
                lifecycleScope.launch(Dispatchers.IO) {
                    setRouteToTourStart(mobilityMean, tourStartLocation)
                }
            }
        builder.create()
        builder.show()
    }

    private suspend fun setRouteToTourStart(mobilityMean: String, tourStartLocation: TripStageLocation) {
        (roadManager as OSRMRoadManager).setMean(mobilityMean)
        val road = roadManager.getRoad(
            arrayListOf(
                GeoPoint(myLocationMarker.position.latitude, myLocationMarker.position.longitude),
                GeoPoint(tourStartLocation.lat, tourStartLocation.lon)
            )
        )
        val roadOverlay = RoadManager.buildRoadOverlay(road)
        roadOverlay.outlinePaint.color = ContextCompat.getColor(
            requireContext(),
            R.color.orange
        )
        roadOverlay.outlinePaint.strokeWidth = 22f
        binding.tourRouteMap.overlays.add(roadOverlay)
        if (routeToTourStartOverlay != null) {
            binding.tourRouteMap.overlays.removeAt(binding.tourRouteMap.overlays.indexOf(routeToTourStartOverlay))
        }
        routeToTourStartOverlay = roadOverlay
        binding.tourRouteMap.invalidate()
        withContext(Dispatchers.Main) {
            binding.tourRouteMap.controller.setCenter(
                GeoPoint(myLocationMarker.position.latitude, myLocationMarker.position.longitude)
            )
        }
    }
}
