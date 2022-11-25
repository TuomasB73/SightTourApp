package fi.urbanmappers.sighttour.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import fi.urbanmappers.sighttour.R
import fi.urbanmappers.sighttour.databinding.FragmentIndividualTourBinding
import fi.urbanmappers.sighttour.databinding.FragmentToursBinding
import fi.urbanmappers.sighttour.datamodels.Tour
import fi.urbanmappers.sighttour.utils.ToursMobilityMethod
import fi.urbanmappers.sighttour.viewmodels.ActivitiesViewModel
import fi.urbanmappers.sighttour.viewmodels.ToursViewModel

class IndividualTourFragment : Fragment() {
    private lateinit var binding: FragmentIndividualTourBinding
    private var tourId: String? = null
    private val toursViewModel: ToursViewModel by viewModels()

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
        binding = FragmentIndividualTourBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tourId = tourId
        if (tourId != null) {
            toursViewModel.getTourById(tourId)
            toursViewModel.tourById.observe(viewLifecycleOwner) { tour ->
                setDataToUI(tour)
            }
        }

        binding.showTourOnMapButton.setOnClickListener {
            val bundle = bundleOf("tourId" to tourId)

            requireActivity().supportFragmentManager.commit {
                setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                setReorderingAllowed(true)
                add<TourRouteMapFragment>(R.id.fragmentContainer, args = bundle)
                addToBackStack(null)
            }
        }
    }

    private fun setDataToUI(tour: Tour) {
        binding.tourTitleTextView.text = tour.name
        binding.tourDescriptionTextView.text = tour.description

        Glide.with(requireContext()).load(tour.imageUrl).centerCrop().into(binding.tourImageView)

        binding.showTourOnMapButton.visibility = View.VISIBLE

        tour.tripStages.forEach { tripStage ->
            val tourStageLayout = layoutInflater.inflate(R.layout.tour_stage_layout, binding.tripStagesContainer, false)

            tourStageLayout.findViewById<TextView>(R.id.startLocationTextView).text = tripStage.startLocation.placeName
            if (tour.tripStages.last() == tripStage) {
                tourStageLayout.findViewById<TextView>(R.id.endLocationTextView).text = tripStage.endLocation.placeName
                tourStageLayout.findViewById<View>(R.id.routeEndLocationLine).visibility = View.VISIBLE
            }
            tourStageLayout.findViewById<TextView>(R.id.tourStageLengthTextView).text =
                getString(R.string.tour_stage_distance_text, tripStage.lengthInKm.toString())
            tourStageLayout.findViewById<TextView>(R.id.tourStageDurationTextView).text =
                getString(R.string.tour_stage_duration_text, tripStage.durationInMinutes.toString())

            tourStageLayout.findViewById<ShapeableImageView>(R.id.tourStageMobilityMethodImageView).setImageResource(
                when (tripStage.mobilityMethod) {
                    ToursMobilityMethod.Walking -> R.drawable.walking_icon
                    ToursMobilityMethod.Bicycling -> R.drawable.bicycle_icon
                    ToursMobilityMethod.EScooter -> R.drawable.escooter_icon
                    ToursMobilityMethod.Bus -> R.drawable.bus_icon
                    ToursMobilityMethod.Tram -> R.drawable.tram_icon
                    ToursMobilityMethod.Metro -> R.drawable.metro_icon
                    ToursMobilityMethod.Ferry -> R.drawable.ferry_icon
                }
            )

            binding.tripStagesContainer.addView(tourStageLayout)
        }
    }
}
