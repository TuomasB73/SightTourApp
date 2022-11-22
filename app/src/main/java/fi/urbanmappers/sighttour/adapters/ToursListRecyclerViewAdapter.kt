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

        holder.itemView.setOnClickListener {
            tourItemClickListener.onTourItemClick(tourItems[position].id)
        }
    }

    interface TourItemClickListener {
        fun onTourItemClick(tourId: String)
    }
}
