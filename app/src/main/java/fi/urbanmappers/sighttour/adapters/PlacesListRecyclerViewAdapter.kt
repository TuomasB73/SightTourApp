package fi.urbanmappers.sighttour.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fi.urbanmappers.sighttour.R
import fi.urbanmappers.sighttour.datamodels.Place

class PlacesListRecyclerViewAdapter(
    private val placeItems: List<Place>,
    private val placeItemClickListener: PlaceItemClickListener
) :
    RecyclerView.Adapter<PlacesListRecyclerViewAdapter.PlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_recycler_row, parent, false)
        return PlaceViewHolder(view)
    }

    override fun getItemCount() = placeItems.size

    inner class PlaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val tagsTextView: TextView = view.findViewById(R.id.tagsTextView)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.titleTextView.text = placeItems[position].name.en ?: placeItems[position].name.fi

        var tagsString = "Tags: "
        placeItems[position].tags?.forEach { tag ->
            tagsString += "${tag.name}, "
        }
        holder.tagsTextView.text = tagsString

        holder.itemView.setOnClickListener {
            placeItemClickListener.onPlaceItemClick(placeItems[position].id)
        }
    }

    interface PlaceItemClickListener {
        fun onPlaceItemClick(placeId: String)
    }
}
