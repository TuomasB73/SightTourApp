package fi.urbanmappers.sighttour.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import fi.urbanmappers.sighttour.datamodels.Event
import fi.urbanmappers.sighttour.datamodels.EventsData
import fi.urbanmappers.sighttour.repositories.MyHelsinkiOpenApiRepository
import kotlinx.coroutines.Dispatchers

class EventsViewModel : ViewModel() {
    private val myHelsinkiOpenApiRepository = MyHelsinkiOpenApiRepository()
    lateinit var events: LiveData<EventsData>
    lateinit var eventById: LiveData<Event>

    fun getEvents(tags: String? = null, limit: Int? = null) {
        events = liveData(Dispatchers.IO) {
            try {
                val retrievedEvents = myHelsinkiOpenApiRepository.getEvents(tags, limit)
                emit(retrievedEvents)
            } catch (e: Exception) {
                Log.e("PlacesViewModel getEvents error", e.toString())
            }
        }
    }

    fun getEventById(eventId: String) {
        eventById = liveData(Dispatchers.IO) {
            try {
                val retrievedEventById = myHelsinkiOpenApiRepository.getEventById(eventId)
                emit(retrievedEventById)
            } catch (e: Exception) {
                Log.e("PlacesViewModel getEventById error", e.toString())
            }
        }
    }
}
