package fi.urbanmappers.sighttour.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fi.urbanmappers.sighttour.R
import fi.urbanmappers.sighttour.datamodels.Event

class EventsListRecyclerViewAdapter(
    private val eventItems: List<Event>,
    private val eventItemClickListener: EventItemClickListener
) :
    RecyclerView.Adapter<EventsListRecyclerViewAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_recycler_row, parent, false)
        return EventViewHolder(view)
    }

    override fun getItemCount() = eventItems.size

    inner class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val tagsTextView: TextView = view.findViewById(R.id.tagsTextView)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.titleTextView.text = eventItems[position].name.en ?: eventItems[position].name.fi

        var tagsString = "Tags: "
        eventItems[position].tags?.forEach { tag ->
            tagsString += "${tag.name}, "
        }
        holder.tagsTextView.text = tagsString

        holder.itemView.setOnClickListener {
            eventItemClickListener.onEventItemClick(eventItems[position].id)
        }
    }

    interface EventItemClickListener {
        fun onEventItemClick(eventId: String)
    }
}
