package fi.urbanmappers.sighttour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import fi.urbanmappers.sighttour.fragments.ActivitiesAndEventsFragment
import fi.urbanmappers.sighttour.fragments.PlacesFragment
import fi.urbanmappers.sighttour.fragments.ToursFragment
import fi.urbanmappers.sighttour.fragments.MapFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fragmentContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        fragmentContainer = findViewById(R.id.fragmentContainer)

        val toursFragment = ToursFragment()
        val mapFragment = MapFragment()
        val placesFragment = PlacesFragment()
        val activitiesAndEventsFragment = ActivitiesAndEventsFragment()

        setCurrentFragment(toursFragment)

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.tours -> setCurrentFragment(toursFragment)
                R.id.map -> setCurrentFragment(mapFragment)
                R.id.places -> setCurrentFragment(placesFragment)
                R.id.activitiesAndEvents -> setCurrentFragment(activitiesAndEventsFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainer, fragment)
        }
    }
}
