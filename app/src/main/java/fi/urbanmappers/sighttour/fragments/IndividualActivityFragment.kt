package fi.urbanmappers.sighttour.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import fi.urbanmappers.sighttour.databinding.FragmentIndividualActivityBinding
import fi.urbanmappers.sighttour.datamodels.Activity
import fi.urbanmappers.sighttour.viewmodels.ActivitiesViewModel

class IndividualActivityFragment : Fragment() {
    private lateinit var binding: FragmentIndividualActivityBinding
    private var activityId: String? = null
    private val activitiesViewModel: ActivitiesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityIdArg = requireArguments().getString("activityId")
        activityId = activityIdArg
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentIndividualActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activityId = activityId
        if (activityId != null) {
            activitiesViewModel.getActivityById(activityId)
            activitiesViewModel.activityById.observe(viewLifecycleOwner) { activity ->
                setDataToUI(activity)
            }
        }
    }

    private fun setDataToUI(activity: Activity) {
        binding.individualActivityTitleTextView.text = activity.descriptions["en"]?.name ?: activity.descriptions["fi"]?.name
    }
}
