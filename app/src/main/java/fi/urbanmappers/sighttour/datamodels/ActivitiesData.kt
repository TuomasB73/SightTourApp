package fi.urbanmappers.sighttour.datamodels

data class ActivitiesData(
    val rows: List<Activity>
)

data class Activity(
    val id: String,
    val updated: String,
    val descriptions: DescriptionLanguage,
    val company: Company,
    val open: WeekDay,
    val media: List<Media>,
    val address: Address?,
    val companyAddress: Address?,
    val tags: List<String>,
    val siteUrl: String?,
    val storeUrl: String?,
    val priceEUR: Price,
    val availableMonths: List<String>,
    val meantFor: List<String>,
    val duration: String,
    val durationType: String
)

data class DescriptionLanguage(
    val en: ActivityDescription
)

data class ActivityDescription(
    val name: String,
    val description: String?,
    val siteUrl: String?,
    val storeUrl: String?
)

data class Company(
    val name: String?,
    val businessId: String?,
    val email: String,
    val phone: String
)

data class WeekDay(
    val sunday: OpeningHours
)

data class OpeningHours(
    val open: Boolean,
    val from: String?,
    val to: String?
)

data class Media(
    val id:	String,
    val kind: String,
    val name: String,
    val smallUrl: String,
    val originalUrl: String,
    val largeUrl: String
)

data class Price(
    val from: Int?,
    val to: Int?,
    val pricingType: String
)
