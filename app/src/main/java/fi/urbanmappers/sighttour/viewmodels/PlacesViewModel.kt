package fi.urbanmappers.sighttour.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import fi.urbanmappers.sighttour.datamodels.PlacesData
import fi.urbanmappers.sighttour.repositories.MyHelsinkiOpenApiRepository
import kotlinx.coroutines.Dispatchers

class PlacesViewModel : ViewModel() {
    private val myHelsinkiOpenApiRepository = MyHelsinkiOpenApiRepository()
    lateinit var placesData: LiveData<PlacesData>

    fun getPlacesData(tags: String? = null, limit: Int? = null) {
        placesData = liveData(Dispatchers.IO) {
            try {
                val retrievedPlacesData = myHelsinkiOpenApiRepository.getPlaces(tags, limit)
                emit(retrievedPlacesData)
            } catch (e: Exception) {
                Log.e("PlacesViewModel error", e.toString())
            }
        }
    }
}
