package fi.urbanmappers.sighttour.utils

import fi.urbanmappers.sighttour.datamodels.Tour
import fi.urbanmappers.sighttour.datamodels.ToursData
import fi.urbanmappers.sighttour.datamodels.TripStage
import fi.urbanmappers.sighttour.datamodels.TripStageLocation

object ToursDatabase {
    val toursData = ToursData(
        listOf(
            Tour(
                id = "fje8456h",
                name = "City Centre Tour",
                description = "A tour around Helsinki centre including various places and landmarks.",
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/1/15/Catedral_Luterana_de_Helsinki%2C_Finlandia%2C_2012-08-14%2C_DD_01.JPG",
                tripStages = listOf(
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.169647,
                            lon = 24.934865,
                            placeName = "Narinkkatori"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.170672,
                            lon = 24.941515,
                            placeName = "Helsinki Railway station"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 0.5,
                        durationInMinutes = 7,
                        tourStageSequence = 1
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.170672,
                            lon = 24.941515,
                            placeName = "Helsinki Railway station"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.169800,
                            lon = 24.952229,
                            placeName = "Helsinki Cathedral"
                        ),
                        mobilityMethod = ToursMobilityMethod.EScooter,
                        lengthInKm = 0.75,
                        durationInMinutes = 4,
                        tourStageSequence = 2
                    )
                ),
                categories = listOf(
                    ToursCategory.City, ToursCategory.Shopping, ToursCategory.Sightseeing
                )
            )
        )
    )
}
