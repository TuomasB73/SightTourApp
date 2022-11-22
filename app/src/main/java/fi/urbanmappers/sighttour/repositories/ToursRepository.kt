package fi.urbanmappers.sighttour.repositories

import android.util.Log
import fi.urbanmappers.sighttour.datamodels.Tour
import fi.urbanmappers.sighttour.datamodels.ToursData
import fi.urbanmappers.sighttour.utils.ToursDatabase

class ToursRepository {
    fun getTours(): ToursData {
        return ToursDatabase.toursData
    }

    fun getTourById(tourId: String): Tour {
        val tour = ToursDatabase.toursData.tours.firstOrNull { it.id == tourId }
        if (tour != null) {
            return tour
        } else {
            throw Exception("ToursRepository getTourById error: Tour not found by id")
        }
    }
}
