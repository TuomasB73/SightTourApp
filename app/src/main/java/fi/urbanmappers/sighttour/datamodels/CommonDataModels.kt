package fi.urbanmappers.sighttour.datamodels

data class Name(
    val fi: String?,
    val en: String?
)

data class Location(
    val lat: Double?,
    val lon: Double?,
    val address: Address
)

data class Address(
    val streetAddress: String?,
    val postalCode: String?,
    val locality: String?,
    val neighbourhood: String?
)

data class Description(
    val intro: String?,
    val body: String?,
    val images: List<Image>?
)

data class Image(
    val url: String
)

data class Tag(
    val id: String,
    val name: String
)
