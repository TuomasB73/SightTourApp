package fi.urbanmappers.sighttour.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import fi.urbanmappers.sighttour.datamodels.*
import fi.urbanmappers.sighttour.repositories.MyHelsinkiOpenApiRepository
import kotlinx.coroutines.Dispatchers

class PlacesViewModel : ViewModel() {
    private val myHelsinkiOpenApiRepository = MyHelsinkiOpenApiRepository()
    lateinit var places: LiveData<PlacesData>
    lateinit var placeById: LiveData<Place>

    fun getPlaces(tags: String? = null, limit: Int? = null) {
        places = liveData(Dispatchers.IO) {
            try {
                val retrievedPlaces = myHelsinkiOpenApiRepository.getPlaces(tags, limit)
                emit(retrievedPlaces)
            } catch (e: Exception) {
                Log.e("PlacesViewModel getPlaces error", e.toString())
            }
        }
    }

    fun getPlaceById(placeId: String) {
        placeById = liveData(Dispatchers.IO) {
            try {
                val retrievedPlaceById = myHelsinkiOpenApiRepository.getPlaceById(placeId)
                emit(retrievedPlaceById)
            } catch (e: Exception) {
                Log.e("PlacesViewModel getPlaceById error", e.toString())
            }
        }
    }
}
