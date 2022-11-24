package fi.urbanmappers.sighttour.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import fi.urbanmappers.sighttour.adapters.PlacesListRecyclerViewAdapter
import fi.urbanmappers.sighttour.adapters.ToursListRecyclerViewAdapter
import fi.urbanmappers.sighttour.databinding.FragmentToursBinding
import fi.urbanmappers.sighttour.utils.PlacesCategory
import fi.urbanmappers.sighttour.utils.TagCategories
import fi.urbanmappers.sighttour.viewmodels.ToursViewModel

class ToursFragment : Fragment(), ToursListRecyclerViewAdapter.TourItemClickListener {
    private lateinit var binding: FragmentToursBinding
    private val toursViewModel: ToursViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentToursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeToursRecyclerView()
    }

    private fun initializeToursRecyclerView() {
        binding.toursListRecyclerView.layoutManager = LinearLayoutManager(this.context)

        toursViewModel.getTours()
        toursViewModel.tours.observe(viewLifecycleOwner) { toursData ->
            binding.toursListProgressIndicator.visibility = View.GONE
            binding.toursListRecyclerView.visibility = View.VISIBLE
            binding.toursListRecyclerView.adapter = ToursListRecyclerViewAdapter(toursData.tours, this, requireContext())
        }
    }

    override fun onTourItemClick(tourId: String) {

    }
}
