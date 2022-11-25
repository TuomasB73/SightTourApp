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
import fi.urbanmappers.sighttour.databinding.FragmentTourRouteMapBinding
import fi.urbanmappers.sighttour.datamodels.Tour
import fi.urbanmappers.sighttour.viewmodels.ToursViewModel
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

        checkPermissionAndInitLocationTracking()
        initializeMap()

        val tourId = tourId
        if (tourId != null) {
            toursViewModel.getTourById(tourId)
            toursViewModel.tourById.observe(viewLifecycleOwner) { tour ->
                binding.tourRouteTitleTextView.text = tour.name
                setTourRouteOnMap(tour)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        lm.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        myLocationMarker.position = GeoPoint(location.latitude, location.longitude, location.altitude)
        binding.tourRouteMap.controller.setCenter(GeoPoint(location.latitude, location.longitude))
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

    private fun setTourRouteOnMap(tour: Tour) {

    }
}
