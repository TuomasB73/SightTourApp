package fi.urbanmappers.sighttour.datamodels

data class EventsData(
    val data: List<Event>
)

data class Event(
    val id: String,
    val name: Name,
    val infoUrl: String?,
    val location: Location,
    val description: Description,
    val tags: List<Tag>?
)
