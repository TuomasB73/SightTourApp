package fi.urbanmappers.sighttour.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import fi.urbanmappers.sighttour.R
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
        binding.titleTextView.text =
            activity.descriptions["en"]?.name ?: activity.descriptions["fi"]?.name ?: ""

        binding.descriptionTextView.text =
            activity.descriptions["en"]?.description ?: activity.descriptions["fi"]?.description
                    ?: ""

        binding.companyTextView.text = getString(
            R.string.company_text, "\n${activity.company.name}, " +
                    "${activity.company.email}, ${activity.company.phone}"
        )

        var openText = ""
        activity.open.forEach { openingHours ->
            if (openingHours.value.from != null && openingHours.value.to != null) {
                openText += "${openingHours.key}: ${openingHours.value.from} - ${openingHours.value.to},\n"
            }
        }
        binding.openTextView.text = if (openText.isNotEmpty()) getString(R.string.open_text, "\n${openText}") else ""

        binding.addressTextView.text = getString(
            R.string.address_text, "${activity.address?.streetAddress ?: ""}, " +
                    "${activity.address?.postalCode ?: ""}, ${activity.address?.locality ?: ""}, ${activity.address?.neighbourhood ?: ""}"
        )

        binding.siteUrlTextView.text = getString(R.string.site_url_text, activity.siteUrl)

        binding.priceTextView.text =
            if (activity.priceEUR.from != null && activity.priceEUR.to != null)
                getString(
                    R.string.price_text, activity.priceEUR.from.toString(),
                    activity.priceEUR.to.toString(), activity.priceEUR.pricingType
                ) else ""

        binding.durationTextView.text =
            if (activity.duration != null && activity.duration.isNotEmpty()) getString(R.string.duration_text, activity.duration)
            else ""

        if (activity.media.isNotEmpty()) {
            Glide.with(requireContext()).load(activity.media.first().originalUrl).centerCrop()
                .into(binding.imageView)
        }

        binding.showOnMapButton.visibility = View.VISIBLE
    }
}
