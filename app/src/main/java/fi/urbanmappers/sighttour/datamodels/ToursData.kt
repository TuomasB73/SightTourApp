package fi.urbanmappers.sighttour.datamodels

import fi.urbanmappers.sighttour.utils.ToursCategory
import fi.urbanmappers.sighttour.utils.ToursMobilityMethod

data class ToursData(
    val tours: List<Tour>
)

data class Tour(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val tripStages: List<TripStage>,
    val categories: List<ToursCategory>
) {
    fun getTotalTourDistance(): Double {
        var totalDistance = 0.0
        tripStages.forEach { tripStage ->
            totalDistance += tripStage.lengthInKm
        }
        return String.format("%.2f", totalDistance).toDouble()
    }

    fun getTotalTourDuration(): Int {
        var totalDuration = 0
        tripStages.forEach { tripStage ->
            totalDuration += tripStage.durationInMinutes
        }
        return totalDuration
    }
}

data class TripStage(
    val startLocation: TripStageLocation,
    val endLocation: TripStageLocation,
    val mobilityMethod: ToursMobilityMethod,
    val lengthInKm: Double,
    val durationInMinutes: Int,
    val tourStageSequence: Int
)

data class TripStageLocation(
    val lat: Double,
    val lon: Double,
    val placeName: String
)
