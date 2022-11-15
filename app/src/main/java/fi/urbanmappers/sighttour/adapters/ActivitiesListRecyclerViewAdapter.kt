package fi.urbanmappers.sighttour.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fi.urbanmappers.sighttour.R
import fi.urbanmappers.sighttour.datamodels.Activity

class ActivitiesListRecyclerViewAdapter(
    private val activityItems: List<Activity>,
    private val activityItemClickListener: ActivityItemClickListener
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
        val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.titleTextView.text = activityItems[position].descriptions["en"]?.name ?: activityItems[position].descriptions["fi"]?.name
        holder.descriptionTextView.text = activityItems[position].descriptions["en"]?.description ?: activityItems[position].descriptions["fi"]?.name?.take(50).plus("...")

        var tagsString = "Tags: "
        activityItems[position].tags.forEach { tag ->
            tagsString += "$tag, "
        }
        holder.tagsTextView.text = tagsString

        holder.itemView.setOnClickListener {
            activityItemClickListener.onActivityItemClick(activityItems[position].id)
        }
    }

    interface ActivityItemClickListener {
        fun onActivityItemClick(activityId: String)
    }
}
