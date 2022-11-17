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
import fi.urbanmappers.sighttour.datamodels.Place

class PlacesListRecyclerViewAdapter(
    private val placeItems: List<Place>,
    private val placeItemClickListener: PlaceItemClickListener,
    private val context: Context
) :
    RecyclerView.Adapter<PlacesListRecyclerViewAdapter.PlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_and_event_recycler_row, parent, false)
        return PlaceViewHolder(view)
    }

    override fun getItemCount() = placeItems.size

    inner class PlaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val tagsTextView: TextView = view.findViewById(R.id.tagsTextView)
        val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)
        val imageTextView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.titleTextView.text = placeItems[position].name.en ?: placeItems[position].name.fi ?: ""

        // Description
        holder.descriptionTextView.text = placeItems[position].description.intro ?: ""

        // Tags
        var tagsString = "Tags: "
        placeItems[position].tags?.forEach { tag ->
            tagsString += "${tag.name}, "
        }
        holder.tagsTextView.text = tagsString

        if (placeItems[position].description.images != null && placeItems[position].description.images?.isNotEmpty() == true) {
            Glide.with(context).load(placeItems[position].description.images?.first()?.url).centerCrop().into(holder.imageTextView)
        }

        holder.itemView.setOnClickListener {
            placeItemClickListener.onPlaceItemClick(placeItems[position].id)
        }
    }

    interface PlaceItemClickListener {
        fun onPlaceItemClick(placeId: String)
    }
}
