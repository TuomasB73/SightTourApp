package fi.urbanmappers.sighttour.fragments

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
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
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import fi.urbanmappers.sighttour.R
import fi.urbanmappers.sighttour.databinding.FragmentMapBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

class MapFragment : Fragment(), LocationListener {

    private lateinit var marker: Marker
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private var latitude = 0.00
    private var longitude = 0.00
    private var altitude = 0.00

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Comment test

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

        val lm = requireActivity().getSystemService(Context.LOCATION_SERVICE) as
                LocationManager
        //somewhere e.g. in "start tracking" button click listener
        binding.btn.setOnClickListener {
            Toast.makeText(requireContext(), "Started tracking", Toast.LENGTH_SHORT).show()
            lm.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                1,
                1f,
                this
            )
        }

        binding.btnCancel.setOnClickListener {
            Toast.makeText(requireContext(), "Stopped tracking", Toast.LENGTH_SHORT).show()
            lm.removeUpdates(
                this
            )
            Log.d(
                "STOP",
                "TRACKING STOPPED"
            )
        }

        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))

        binding.map.setTileSource(TileSourceFactory.MAPNIK)
        binding.map.setMultiTouchControls(true)
        binding.map.controller.setZoom(17.0)


        marker = Marker(binding.map)
        marker.icon = AppCompatResources.getDrawable(
            requireContext(),
            R.drawable.ic_baseline_location_on_24
        )
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
    }


    override fun onLocationChanged(location: Location) {

        Log.d(
            "GEOLOCATION",
            "new latitude: ${location.latitude} longitude: ${location.longitude} and altitude: ${location.altitude}"
        )

        latitude = location.latitude
        longitude = location.longitude
        altitude = location.altitude

        marker.position = GeoPoint(location.latitude, location.longitude, location.altitude)
        marker.title = " ${
            getAddress(location.latitude, location.longitude) +
                    "\n Latitude: " + location.latitude.toString() +
                    "\n Longitude: " + location.longitude.toString() +
                    "\n Altitude: " + location.altitude.toString()
        }"
        binding.map.controller.setCenter(GeoPoint(location.latitude, location.longitude))
        marker.closeInfoWindow()
        binding.map.overlays.add(marker)
        binding.map.invalidate()

    }

    private fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(requireContext())
        val list = geocoder.getFromLocation(lat, lng, 1)
        return list[0].getAddressLine(0)
    }
}
