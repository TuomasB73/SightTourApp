package fi.urbanmappers.sighttour.datamodels

data class PlacesData(
    val data: List<Place>,
    val tags: Map<String, String>
)

data class Place(
    val id: String,
    val name: Name,
    val infoUrl: String?,
    val location: Location,
    val description: Description,
    val tags: List<Tag>?
)
