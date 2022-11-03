package fi.urbanmappers.sighttour.datamodels

data class PlacesData(
    val data: List<Place>
)

data class Place(
    val id: String,
    val name: PlaceName,
    val info_url: String,
    val location: PlaceLocation,
    val description: PlaceDescription,
    val tags: List<PlaceTag>
)

data class PlaceName(
    val en: String
)

data class PlaceLocation(
    val lat: Double,
    val lon: Double,
    val address: PlaceAddress
)

data class PlaceAddress(
    val street_address: String,
    val postal_code: String,
    val locality: String,
    val neighbourhood: String
)

data class PlaceDescription(
    val intro: String,
    val body: String,
    val images: List<PlaceImage>
)

data class PlaceImage(
    val url: String
)

data class PlaceTag(
    val id: String,
    val name: String
)
