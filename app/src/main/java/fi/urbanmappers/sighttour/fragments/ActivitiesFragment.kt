package fi.urbanmappers.sighttour.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import fi.urbanmappers.sighttour.R
import fi.urbanmappers.sighttour.databinding.FragmentActivitiesBinding
import fi.urbanmappers.sighttour.databinding.FragmentPlacesBinding

class ActivitiesFragment : Fragment() {
    private lateinit var binding: FragmentActivitiesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentActivitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToActivitiesListFragment(ActivitiesListFragment())
    }

    private fun btnToActivitiesList() {
        requireActivity().supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<ActivitiesListFragment>(R.id.fragmentContainer)
            addToBackStack(null)
        }
    }

    private fun goToActivitiesListFragment(fragment: Fragment) {
        //  requireActivity() gets the reference from mainActivity

        binding.btnSportsId.setOnClickListener {
            btnToActivitiesList()
        }
        binding.btnNatureEvId.setOnClickListener {
            btnToActivitiesList()
        }
        binding.btnMusicId.setOnClickListener {
            btnToActivitiesList()
        }
        binding.btnSightseeingId.setOnClickListener {
            btnToActivitiesList()
        }
        binding.btnArtsId.setOnClickListener {
            btnToActivitiesList()
        }
        binding.btnNightlifeId.setOnClickListener {
            btnToActivitiesList()
        }
    }
}

