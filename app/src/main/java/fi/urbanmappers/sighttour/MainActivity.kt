package fi.urbanmappers.sighttour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import fi.urbanmappers.sighttour.databinding.ActivityMainBinding
import fi.urbanmappers.sighttour.fragments.ActivitiesAndEventsFragment
import fi.urbanmappers.sighttour.fragments.PlacesFragment
import fi.urbanmappers.sighttour.fragments.ToursFragment
import fi.urbanmappers.sighttour.fragments.MapFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val toursFragment = ToursFragment()
        val mapFragment = MapFragment()
        val placesFragment = PlacesFragment()
        val activitiesAndEventsFragment = ActivitiesAndEventsFragment()

        setCurrentFragment(toursFragment)

        binding.bottomTabNavBar.menuBtnTours.setOnClickListener {
            setCurrentFragment(toursFragment)
        }
        binding.bottomTabNavBar.menuBtnMap.setOnClickListener {
            setCurrentFragment(mapFragment)
        }
        binding.bottomTabNavBar.menuBtnPlaces.setOnClickListener {
            setCurrentFragment(placesFragment)
        }
        binding.bottomTabNavBar.menuBtnActivities.setOnClickListener {
            setCurrentFragment(activitiesAndEventsFragment)
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainer, fragment)
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack(
                    supportFragmentManager.getBackStackEntryAt(0).id,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }
        }

        binding.bottomTabNavBar.menuBtnTours.background =
            AppCompatResources.getDrawable(this, R.drawable.btn_tours)
        binding.bottomTabNavBar.menuBtnMap.background =
            AppCompatResources.getDrawable(this, R.drawable.btn_map)
        binding.bottomTabNavBar.menuBtnPlaces.background =
            AppCompatResources.getDrawable(this, R.drawable.btn_places)
        binding.bottomTabNavBar.menuBtnActivities.background =
            AppCompatResources.getDrawable(this, R.drawable.btn_activities)

        when (fragment) {
            is ToursFragment -> binding.bottomTabNavBar.menuBtnTours.background =
                AppCompatResources.getDrawable(this, R.drawable.btn_tours_selected)
            is MapFragment -> binding.bottomTabNavBar.menuBtnMap.background =
                AppCompatResources.getDrawable(this, R.drawable.btn_map_selected)
            is PlacesFragment -> binding.bottomTabNavBar.menuBtnPlaces.background =
                AppCompatResources.getDrawable(this, R.drawable.btn_places_selected)
            is ActivitiesAndEventsFragment -> binding.bottomTabNavBar.menuBtnActivities.background =
                AppCompatResources.getDrawable(this, R.drawable.btn_activities_selected)
        }
    }
}
