package fi.urbanmappers.sighttour.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fi.urbanmappers.sighttour.R
import fi.urbanmappers.sighttour.datamodels.Tour
import fi.urbanmappers.sighttour.utils.ToursCategory
import fi.urbanmappers.sighttour.utils.ToursMobilityMethod

class ToursListRecyclerViewAdapter(
    private val tourItems: List<Tour>,
    private val tourItemClickListener: TourItemClickListener,
    private val context: Context
) :
    RecyclerView.Adapter<ToursListRecyclerViewAdapter.TourViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tour_recycler_row, parent, false)
        return TourViewHolder(view)
    }

    override fun getItemCount() = tourItems.size

    inner class TourViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val routeStartEndPointsTextView: TextView = view.findViewById(R.id.routeStartEndPointsTextView)
        val distanceDurationTextView: TextView = view.findViewById(R.id.distanceDurationTextView)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val tourCategoryIconsContainer: LinearLayout = view.findViewById(R.id.tourCategoryIconsContainer)
        val mobilityMethodIconsContainer: LinearLayout = view.findViewById(R.id.mobilityMethodIconsContainer)
    }

    override fun onBindViewHolder(holder: TourViewHolder, position: Int) {
        Log.d("TourName", tourItems[position].name)
        holder.titleTextView.text = tourItems[position].name
        holder.routeStartEndPointsTextView.text = "${tourItems[position].tripStages.first().startLocation.placeName} - " +
                "${tourItems[position].tripStages.last().endLocation.placeName}"
        var totalDistance = 0.0
        tourItems[position].tripStages.forEach { tripStage ->
            totalDistance += tripStage.lengthInKm
        }
        var totalDuration = 0
        tourItems[position].tripStages.forEach { tripStage ->
            totalDuration += tripStage.durationInMinutes
        }
        holder.distanceDurationTextView.text = "$totalDistance km, $totalDuration min"

        Glide.with(context).load(tourItems[position].imageUrl).centerCrop().into(holder.imageView)

        val categories = tourItems[position].categories

        if (categories.any { it == ToursCategory.Nature }) {
            holder.tourCategoryIconsContainer.findViewById<ImageView>(R.id.natureCategoryImageView).visibility = View.VISIBLE
        }
        if (categories.any { it == ToursCategory.Shopping }) {
            holder.tourCategoryIconsContainer.findViewById<ImageView>(R.id.shoppingCategoryImageView).visibility = View.VISIBLE
        }
        if (categories.any { it == ToursCategory.City }) {
            holder.tourCategoryIconsContainer.findViewById<ImageView>(R.id.cityCategoryImageView).visibility = View.VISIBLE
        }
        if (categories.any { it == ToursCategory.Culture }) {
            holder.tourCategoryIconsContainer.findViewById<ImageView>(R.id.cultureCategoryImageView).visibility = View.VISIBLE
        }
        if (categories.any { it == ToursCategory.Sports }) {
            holder.tourCategoryIconsContainer.findViewById<ImageView>(R.id.sportsCategoryImageView).visibility = View.VISIBLE
        }
        if (categories.any { it == ToursCategory.Sightseeing }) {
            holder.tourCategoryIconsContainer.findViewById<ImageView>(R.id.sightseeingCategoryImageView).visibility = View.VISIBLE
        }

        val mobilityMethods = tourItems[position].tripStages.map {
            it.mobilityMethod
        }

        if (mobilityMethods.any { it == ToursMobilityMethod.Walking }) {
            holder.mobilityMethodIconsContainer.findViewById<ImageView>(R.id.walkingMobilityMethodImageView).visibility = View.VISIBLE
        }
        if (mobilityMethods.any { it == ToursMobilityMethod.Bicycling }) {
            holder.mobilityMethodIconsContainer.findViewById<ImageView>(R.id.bicyclingMobilityMethodImageView).visibility = View.VISIBLE
        }
        if (mobilityMethods.any { it == ToursMobilityMethod.EScooter }) {
            holder.mobilityMethodIconsContainer.findViewById<ImageView>(R.id.escooterMobilityMethodImageView).visibility = View.VISIBLE
        }
        if (mobilityMethods.any { it == ToursMobilityMethod.Bus }) {
            holder.mobilityMethodIconsContainer.findViewById<ImageView>(R.id.busMobilityMethodImageView).visibility = View.VISIBLE
        }
        if (mobilityMethods.any { it == ToursMobilityMethod.Tram }) {
            holder.mobilityMethodIconsContainer.findViewById<ImageView>(R.id.tramMobilityMethodImageView).visibility = View.VISIBLE
        }
        if (mobilityMethods.any { it == ToursMobilityMethod.Metro }) {
            holder.mobilityMethodIconsContainer.findViewById<ImageView>(R.id.metroMobilityMethodImageView).visibility = View.VISIBLE
        }
        if (mobilityMethods.any { it == ToursMobilityMethod.Ferry }) {
            holder.mobilityMethodIconsContainer.findViewById<ImageView>(R.id.ferryMobilityMethodImageView).visibility = View.VISIBLE
        }

        holder.itemView.setOnClickListener {
            tourItemClickListener.onTourItemClick(tourItems[position].id)
        }
    }

    interface TourItemClickListener {
        fun onTourItemClick(tourId: String)
    }
}
