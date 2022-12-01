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
            ),
            Tour(
                id = "kf48fn58fj",
                name = "Helsinki Park Tour Southeastern",
                description = "A tour around Helsinki’s southern and eastern parks.",
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/15/Kaisaniemen_puisto_2020-10-02.jpg/800px-Kaisaniemen_puisto_2020-10-02.jpg?20210924132046",
                tripStages = listOf(
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.17527453808402,
                            lon = 24.945778831231394,
                            placeName = "Kaisaniemi"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.167472913199944,
                            lon = 24.94822599039567,
                            placeName = "Esplanadi"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 1.2,
                        durationInMinutes = 15,
                        tourStageSequence = 1
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.167472913199944,
                            lon = 24.94822599039567,
                            placeName = "Esplanadi"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.161907737379046,
                            lon = 24.95165875178322,
                            placeName = "Tähtitorninvuori"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 0.6,
                        durationInMinutes = 9,
                        tourStageSequence = 2
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.161907737379046,
                            lon = 24.95165875178322,
                            placeName = "Tähtitorninvuori"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.15566926464715,
                            lon = 24.9556260843444,
                            placeName = "Kaivopuisto"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 0.9,
                        durationInMinutes = 12,
                        tourStageSequence = 3
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.15566926464715,
                            lon = 24.9556260843444,
                            placeName = "Kaivopuisto"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.15516289253486,
                            lon = 24.945966262980455,
                            placeName = "Meripuisto"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 0.7,
                        durationInMinutes = 8,
                        tourStageSequence = 4
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.15516289253486,
                            lon = 24.945966262980455,
                            placeName = "Meripuisto"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.16203374647221,
                            lon = 24.93277441032145,
                            placeName = "Sinebrychoffin puisto"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 1.3,
                        durationInMinutes = 16,
                        tourStageSequence = 5
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.16203374647221,
                            lon = 24.93277441032145,
                            placeName = "Sinebrychoffin puisto"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.16605376524313,
                            lon = 24.939821738671245,
                            placeName = "Vanha kirkkopuisto"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 0.6,
                        durationInMinutes = 7,
                        tourStageSequence = 6
                    ),

                    ),
                categories = listOf(
                    ToursCategory.City, ToursCategory.Nature, ToursCategory.Sightseeing, ToursCategory.Sports
                )
            ),
            Tour(
                id = "nrfn598gr7dfh",
                name = "Helsinki Park Tour Northwestern",
                description = "A tour around Helsinki’s northern and western parks.",
                imageUrl = "https://upload.wikimedia.org/wikipedia/commons/f/f5/T%C3%B6%C3%B6l%C3%B6nlahti_park_in_Kluuvi%2C_Helsinki%2C_Finland%2C_2019_September.jpg",
                tripStages = listOf(
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.17757923107719,
                            lon = 24.93646388623599,
                            placeName = "Töölönlahden puisto"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.17860024210752,
                            lon = 24.93040362676318,
                            placeName = "Hesperian puisto"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 0.55,
                        durationInMinutes = 7,
                        tourStageSequence = 1
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.17860024210752,
                            lon = 24.93040362676318,
                            placeName = "Hesperian puisto"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.18151403006019,
                            lon = 24.914339755937934,
                            placeName = "Sibeliuksen puisto"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 1.2,
                        durationInMinutes = 16,
                        tourStageSequence = 2
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.18151403006019,
                            lon = 24.914339755937934,
                            placeName = "Sibeliuksen puisto"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.173596496533605,
                            lon = 24.916588129350597,
                            placeName = "Väinämöisen puisto"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 1.4,
                        durationInMinutes = 14,
                        tourStageSequence = 3
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.173596496533605,
                            lon = 24.916588129350597,
                            placeName = "Väinämöisen puisto"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.16736084544776,
                            lon = 24.913397684975987,
                            placeName = "Lapinniemi"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 1.2,
                        durationInMinutes = 14,
                        tourStageSequence = 4
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.16736084544776,
                            lon = 24.913397684975987,
                            placeName = "Lapinniemi"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.1745660404459,
                            lon = 24.933782228619272,
                            placeName = "Hakasalmenpuisto"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 1.9,
                        durationInMinutes = 23,
                        tourStageSequence = 5
                    ),

                    ),
                categories = listOf(
                    ToursCategory.City, ToursCategory.Nature, ToursCategory.Sightseeing, ToursCategory.Sports
                )
            )
        )
    )
}
