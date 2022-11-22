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
import fi.urbanmappers.sighttour.datamodels.Event

class EventsListRecyclerViewAdapter(
    private val eventItems: List<Event>,
    private val eventItemClickListener: EventItemClickListener,
    private val context: Context
) :
    RecyclerView.Adapter<EventsListRecyclerViewAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_and_event_recycler_row, parent, false)
        return EventViewHolder(view)
    }

    override fun getItemCount() = eventItems.size

    inner class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val tagsTextView: TextView = view.findViewById(R.id.tagsTextView)
        val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.titleTextView.text = eventItems[position].name.en ?: eventItems[position].name.fi ?: ""
        holder.descriptionTextView.text = eventItems[position].description.intro ?: ""

        var tagsString = "Tags: "
        eventItems[position].tags?.forEach { tag ->
            tagsString += "${tag.name}, "
        }
        holder.tagsTextView.text = tagsString

        if (eventItems[position].description.images != null && eventItems[position].description.images?.isNotEmpty() == true) {
            Glide.with(context).load(eventItems[position].description.images?.first()?.url).centerCrop().into(holder.imageView)
        }

        holder.itemView.setOnClickListener {
            eventItemClickListener.onEventItemClick(eventItems[position].id)
        }
    }

    interface EventItemClickListener {
        fun onEventItemClick(eventId: String)
    }
}
