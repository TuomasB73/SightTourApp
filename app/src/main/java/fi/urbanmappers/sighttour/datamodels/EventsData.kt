package fi.urbanmappers.sighttour.datamodels

data class EventsData(
    val data: List<Event>,
    val tags: Map<String, String>
)

data class Event(
    val id: String,
    val name: Name,
    val infoUrl: String?,
    val location: Location,
    val description: Description,
    val tags: List<Tag>?,
    val eventDates: EventDates?
)

data class EventDates(
    val startingDay: String?,
    val endingDay: String?,
    val additionalDescription: List<AdditionalDescription>?
)

data class AdditionalDescription(
    val langCode: String,
    val text: String
)
