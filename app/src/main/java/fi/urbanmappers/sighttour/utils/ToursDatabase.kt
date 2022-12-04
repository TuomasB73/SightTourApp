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
                name = "City Centrum Tour",
                description = "A tour around Helsinki centrum including various places and landmarks.",
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
                id = "fg84ngjgy8",
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
                        mobilityMethod = ToursMobilityMethod.Bicycling,
                        lengthInKm = 1.4,
                        durationInMinutes = 5,
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
                        mobilityMethod = ToursMobilityMethod.Bicycling,
                        lengthInKm = 0.6,
                        durationInMinutes = 2,
                        tourStageSequence = 6
                    ),
                ),
                categories = listOf(
                    ToursCategory.City, ToursCategory.Nature, ToursCategory.Sightseeing
                )
            ),
            Tour(
                id = "kf487gj57gj",
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
                        mobilityMethod = ToursMobilityMethod.EScooter,
                        lengthInKm = 1.3,
                        durationInMinutes = 4,
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
                        mobilityMethod = ToursMobilityMethod.EScooter,
                        lengthInKm = 1.9,
                        durationInMinutes = 7,
                        tourStageSequence = 5
                    ),
                ),
                categories = listOf(
                    ToursCategory.City, ToursCategory.Nature, ToursCategory.Sightseeing
                )
            ),
            Tour(
                id = "wkl9136h",
                name = "Best Views Centrum",
                description = "See the best views and feel the vibe of Katajannokka Ferris Wheel and more in this tour.",
                imageUrl = "https://live.staticflickr.com/4713/26647506478_1d055ff80b_b.jpg",
                tripStages = listOf(
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.16951843725574,
                            lon = 24.952309748375566,
                            placeName = "Senate Square"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.16788443880121,
                            lon = 24.960971642233304,
                            placeName = "Tove Jansson Park"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 0.6,
                        durationInMinutes = 8,
                        tourStageSequence = 1
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.16951843725574,
                            lon = 24.952309748375566,
                            placeName = "Tove Jansson Park"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.16681597306523,
                            lon = 24.959461795904488,
                            placeName = "SkyWheel Helsinki"
                        ),
                        mobilityMethod = ToursMobilityMethod.EScooter,
                        lengthInKm = 0.1,
                        durationInMinutes = 1,
                        tourStageSequence = 2
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.16681597306523,
                            lon = 24.959461795904488,
                            placeName = "SkyWheel Helsinki"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.16681597306523,
                            lon = 24.959461795904488,
                            placeName = "Allas Sea Pool"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 0.1,
                        durationInMinutes = 1,
                        tourStageSequence = 3
                    )
                ),
                categories = listOf(
                    ToursCategory.City, ToursCategory.Sightseeing, ToursCategory.Culture
                )
            ),
            Tour(
                id = "wkl1872b",
                name = "Local Architecture",
                description = "Get to know some of the most beautiful historical buildings and museums located in the core of Helsinki.",
                imageUrl = "https://live.staticflickr.com/4713/26647506478_1d055ff80b_b.jpg",
                tripStages = listOf(
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.17319052170126,
                            lon = 24.92545943894807,
                            placeName = "Temppelinaukio Church"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.17506395813018,
                            lon = 24.93148185293446,
                            placeName = "The National Museum of Helsinki"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 0.7,
                        durationInMinutes = 8,
                        tourStageSequence = 1
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.17506395813018,
                            lon = 24.93148185293446,
                            placeName = "The National Museum of Helsinki"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.17015593797006,
                            lon = 24.944267339896992,
                            placeName = "Ateneum Art Museum"
                        ),
                        mobilityMethod = ToursMobilityMethod.EScooter,
                        lengthInKm = 1.0,
                        durationInMinutes = 3,
                        tourStageSequence = 2
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.17015593797006,
                            lon = 24.944267339896992,
                            placeName = "Ateneum Art Museum"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.168317544620656,
                            lon = 24.939056455746307,
                            placeName = "Yrjonkatu Swimming Hall"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 0.6,
                        durationInMinutes = 9,
                        tourStageSequence = 3
                    )
                ),
                categories = listOf(
                    ToursCategory.City, ToursCategory.Sightseeing, ToursCategory.Culture
                )
            ),
            Tour(
                id = " 1p2jkk90",
                name = "Vintage Clothing",
                description = "Get to know some of the most beautiful historical buildings and museums located in the core of Helsinki.",
                imageUrl = "https://live.staticflickr.com/4713/26647506478_1d055ff80b_b.jpg",
                tripStages = listOf(
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.16566719558398,
                            lon = 24.933060411799936,
                            placeName = "Variety Vintage"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.16621830638612,
                            lon = 24.93478713365054,
                            placeName = "UFF Frederinkinkatu"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 0.1,
                        durationInMinutes = 2,
                        tourStageSequence = 1
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.16621830638612,
                            lon = 24.93478713365054,
                            placeName = " UFF Frederinkinkatu"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.16563339200075,
                            lon = 24.935619063195983,
                            placeName = "FTA Vintage"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 0.1,
                        durationInMinutes = 2,
                        tourStageSequence = 2
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.16563339200075,
                            lon = 24.935619063195983,
                            placeName = "FTA Vintage"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.168914216582806,
                            lon = 24.930729796469873,
                            placeName = "Metro Kamppi"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 0.2,
                        durationInMinutes = 3,
                        tourStageSequence = 3
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.168914216582806,
                            lon = 24.930729796469873,
                            placeName = "Metro Kamppi"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.17946289729578,
                            lon = 24.95025307216305,
                            placeName = "Metro Hakaniemi"
                        ),
                        mobilityMethod = ToursMobilityMethod.Metro,
                        lengthInKm = 2.1,
                        durationInMinutes = 7,
                        tourStageSequence = 4
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.17946289729578,
                            lon = 24.95025307216305,
                            placeName = "Metro Hakaniemi"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.18067011751023,
                            lon = 24.95182134362997,
                            placeName = "UFF Hakaniemi"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 0.2,
                        durationInMinutes = 3,
                        tourStageSequence = 5
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.18067011751023,
                            lon = 24.95182134362997,
                            placeName = "UFF Hakaniemi"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.18324599738862,
                            lon = 24.95756388088953,
                            placeName = "Almost New"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 0.5,
                        durationInMinutes = 6,
                        tourStageSequence = 6
                    )
                ),
                categories = listOf(
                    ToursCategory.City, ToursCategory.Shopping
                )
            ),
            Tour(
                id = " 1jkda1j0",
                name = "Vintage Clothing Centrum",
                description = "Get to know some of the most beautiful historical buildings and museums located in the core of Helsinki.",
                imageUrl = "https://live.staticflickr.com/4713/26647506478_1d055ff80b_b.jpg",
                tripStages = listOf(
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.169312564945436,
                            lon = 24.938219215715687,
                            placeName = "Beyond Retro"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.16621830638612,
                            lon = 24.93478713365054,
                            placeName = "UFF Frederinkinkatu"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 0.6,
                        durationInMinutes = 7,
                        tourStageSequence = 1
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.16621830638612,
                            lon = 24.93478713365054,
                            placeName = " UFF Frederinkinkatu"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.16563339200075,
                            lon = 24.935619063195983,
                            placeName = "FTA Vintage"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 0.1,
                        durationInMinutes = 2,
                        tourStageSequence = 2
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.16563339200075,
                            lon = 24.935619063195983,
                            placeName = "FTA Vintage"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.16346203570559,
                            lon = 24.934733675120423,
                            placeName = "UFF Vintage Bulevardi"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 0.3,
                        durationInMinutes = 4,
                        tourStageSequence = 3
                    ),
                    TripStage(
                        startLocation = TripStageLocation(
                            lat = 60.16346203570559,
                            lon = 24.934733675120423,
                            placeName = "UFF Vintage Bulevardi"
                        ),
                        endLocation = TripStageLocation(
                            lat = 60.16278531439106,
                            lon = 24.940407444513102,
                            placeName = "Fida Roba"
                        ),
                        mobilityMethod = ToursMobilityMethod.Walking,
                        lengthInKm = 0.5,
                        durationInMinutes = 5,
                        tourStageSequence = 4
                    )
                ),
                categories = listOf(
                    ToursCategory.City, ToursCategory.Shopping
                )
            )
        )
    )
}
