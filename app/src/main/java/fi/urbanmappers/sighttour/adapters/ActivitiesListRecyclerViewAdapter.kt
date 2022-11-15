package fi.urbanmappers.sighttour.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fi.urbanmappers.sighttour.R
import fi.urbanmappers.sighttour.datamodels.Activity

class ActivitiesListRecyclerViewAdapter(
    private val activityItems: List<Activity>,
    private val activityItemClickListener: ActivityItemClickListener,
    private val context: Context
) :
    RecyclerView.Adapter<ActivitiesListRecyclerViewAdapter.ActivityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_recycler_row, parent, false)
        return ActivityViewHolder(view)
    }

    override fun getItemCount() = activityItems.size

    inner class ActivityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val tagsTextView: TextView = view.findViewById(R.id.tagsTextView)
        val descriptionTextView: TextView = view.findViewById(R.id.individualPlaceAndEventDescriptionTextView)
        val timeTextView: TextView = view.findViewById(R.id.timeTextView)
        val imageTextView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.titleTextView.text = activityItems[position].descriptions["en"]?.name ?: activityItems[position].descriptions["fi"]?.name
        holder.descriptionTextView.text = activityItems[position].descriptions["en"]?.description ?: activityItems[position].descriptions["fi"]?.name?.take(50).plus("...")

        var tagsString = "Tags: "
        activityItems[position].tags.forEach { tag ->
            tagsString += "$tag, "
        }
        holder.tagsTextView.text = tagsString

        var timeText = "Time: "
        activityItems[position].open.forEach { openingHours ->
            timeText += "${openingHours.key}: ${openingHours.value.from} - ${openingHours.value.to},\n"
        }
        holder.timeTextView.text = timeText

        if (activityItems[position].media.isNotEmpty()) {
            Glide.with(context).load(activityItems[position].media.first().smallUrl).centerCrop().into(holder.imageTextView)
        }

        holder.itemView.setOnClickListener {
            activityItemClickListener.onActivityItemClick(activityItems[position].id)
        }
    }

    interface ActivityItemClickListener {
        fun onActivityItemClick(activityId: String)
    }
}
