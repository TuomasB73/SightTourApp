package fi.urbanmappers.sighttour.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import fi.urbanmappers.sighttour.datamodels.*
import fi.urbanmappers.sighttour.repositories.ToursRepository
import kotlinx.coroutines.Dispatchers

class ToursViewModel : ViewModel() {
    private val toursRepository = ToursRepository()
    lateinit var tours: LiveData<ToursData>
    lateinit var tourById: LiveData<Tour>

    fun getTours() {
        tours = liveData(Dispatchers.IO) {
            try {
                val retrievedTours = toursRepository.getTours()
                emit(retrievedTours)
            } catch (e: Exception) {
                Log.e("ToursViewModel getTours error", e.toString())
            }
        }
    }

    fun getTourById(tourId: String) {
        tourById = liveData(Dispatchers.IO) {
            try {
                val retrievedTourById = toursRepository.getTourById(tourId)
                emit(retrievedTourById)
            } catch (e: Exception) {
                Log.e("ToursViewModel getTourById error", e.toString())
            }
        }
    }
}
