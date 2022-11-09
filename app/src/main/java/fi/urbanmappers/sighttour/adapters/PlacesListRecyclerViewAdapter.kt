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
        var titleTextView: TextView = view.findViewById(R.id.titleTextView)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.titleTextView.text = placeItems[position].name.en

        holder.itemView.setOnClickListener {
            placeItemClickListener.onItemClick(placeItems[position].id)
        }
    }

    interface PlaceItemClickListener {
        fun onItemClick(placeId: String)
    }
}
