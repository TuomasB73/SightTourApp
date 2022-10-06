package fi.urbanmappers.sighttour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import fi.urbanmappers.sighttour.fragments.ActivitiesFragment
import fi.urbanmappers.sighttour.fragments.FavoritesFragment
import fi.urbanmappers.sighttour.fragments.HomeFragment
import fi.urbanmappers.sighttour.fragments.RoutesFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fragmentContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        fragmentContainer = findViewById(R.id.fragmentContainer)

        val homeFragment = HomeFragment()
        val routesFragment = RoutesFragment()
        val favoritesFragment = FavoritesFragment()
        val activitiesFragment = ActivitiesFragment()

        setCurrentFragment(homeFragment)

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> setCurrentFragment(homeFragment)
                R.id.routes -> setCurrentFragment(routesFragment)
                R.id.favorites -> setCurrentFragment(favoritesFragment)
                R.id.activities -> setCurrentFragment(activitiesFragment)
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
