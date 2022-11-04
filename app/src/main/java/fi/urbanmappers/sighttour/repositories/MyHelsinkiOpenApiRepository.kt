package fi.urbanmappers.sighttour.repositories

import fi.urbanmappers.sighttour.apis.MyHelsinkiOpenApi
import fi.urbanmappers.sighttour.datamodels.*

class MyHelsinkiOpenApiRepository {
    private val myHelsinkiOpenApi = MyHelsinkiOpenApi.service

    suspend fun getPlaces(tags: String? = null, limit: Int? = null): PlacesData {
        val response = myHelsinkiOpenApi.getPlaces(tags, limit)
        val responseBody = response.body()
       if (response.isSuccessful && responseBody != null) {
            return responseBody
        } else {
           throw Exception("MyHelsinkiOpenApiRepository getPlaces error: ${response.message()}, ${response.code()}")
        }
    }

    suspend fun getPlaceById(placeId: String): Place {
        val response = myHelsinkiOpenApi.getPlaceById(placeId)
        val responseBody = response.body()
        if (response.isSuccessful && responseBody != null) {
            return responseBody
        } else {
            throw Exception("MyHelsinkiOpenApiRepository getPlaceById error: ${response.message()}, ${response.code()}")
        }
    }

    suspend fun getEvents(tags: String? = null, limit: Int? = null): EventsData {
        val response = myHelsinkiOpenApi.getEvents(tags, limit)
        val responseBody = response.body()
        if (response.isSuccessful && responseBody != null) {
            return responseBody
        } else {
            throw Exception("MyHelsinkiOpenApiRepository getEvents error: ${response.message()}, ${response.code()}")
        }
    }

    suspend fun getEventById(eventId: String): Event {
        val response = myHelsinkiOpenApi.getEventById(eventId)
        val responseBody = response.body()
        if (response.isSuccessful && responseBody != null) {
            return responseBody
        } else {
            throw Exception("MyHelsinkiOpenApiRepository getEventById error: ${response.message()}, ${response.code()}")
        }
    }

    suspend fun getActivities(tags: String? = null, limit: Int? = null): ActivitiesData {
        val response = myHelsinkiOpenApi.getActivities(tags, limit)
        val responseBody = response.body()
        if (response.isSuccessful && responseBody != null) {
            return responseBody
        } else {
            throw Exception("MyHelsinkiOpenApiRepository getActivities error: ${response.message()}, ${response.code()}")
        }
    }

    suspend fun getActivityById(activityId: String): Activity {
        val response = myHelsinkiOpenApi.getActivityById(activityId)
        val responseBody = response.body()
        if (response.isSuccessful && responseBody != null) {
            return responseBody
        } else {
            throw Exception("MyHelsinkiOpenApiRepository getActivityById error: ${response.message()}, ${response.code()}")
        }
    }
}
