package fi.urbanmappers.sighttour.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import fi.urbanmappers.sighttour.datamodels.ActivitiesData
import fi.urbanmappers.sighttour.datamodels.Activity
import fi.urbanmappers.sighttour.repositories.MyHelsinkiOpenApiRepository
import kotlinx.coroutines.Dispatchers

class ActivitiesViewModel : ViewModel() {
    private val myHelsinkiOpenApiRepository = MyHelsinkiOpenApiRepository()
    lateinit var activities: LiveData<ActivitiesData>
    lateinit var activityById: LiveData<Activity>

    fun getActivities(tags: String? = null, limit: Int? = null) {
        activities = liveData(Dispatchers.IO) {
            try {
                val retrievedActivities = myHelsinkiOpenApiRepository.getActivities(tags, limit)
                emit(retrievedActivities)
            } catch (e: Exception) {
                Log.e("PlacesViewModel getActivities error", e.toString())
            }
        }
    }

    fun getActivityById(activityId: String) {
        activityById = liveData(Dispatchers.IO) {
            try {
                val retrievedActivityById = myHelsinkiOpenApiRepository.getActivityById(activityId)
                emit(retrievedActivityById)
            } catch (e: Exception) {
                Log.e("PlacesViewModel getActivityById error", e.toString())
            }
        }
    }
}
