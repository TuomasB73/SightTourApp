package fi.urbanmappers.sighttour.repositories

import fi.urbanmappers.sighttour.apis.MyHelsinkiOpenApi
import fi.urbanmappers.sighttour.datamodels.PlacesData

class MyHelsinkiOpenApiRepository {
    private val myHelsinkiOpenApi = MyHelsinkiOpenApi.service

    suspend fun getPlaces(tags: String? = null, limit: Int? = null): PlacesData {
        return myHelsinkiOpenApi.getPlaces(tags, limit)
    }
}
