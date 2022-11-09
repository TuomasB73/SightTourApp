package fi.urbanmappers.sighttour.apis

import fi.urbanmappers.sighttour.datamodels.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object MyHelsinkiOpenApi {
    private const val URL = "https://open-api.myhelsinki.fi/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    interface Service {

        @GET("v2/places/")
        suspend fun getPlaces(
            @Query("tags_search") tags: String?,
            @Query("limit") limit: Int?
        ): Response<PlacesData>

        @GET("v2/place/{id}")
        suspend fun getPlaceById(
            @Path("id") placeId: String
        ): Response<Place>

        @GET("v1/events/")
        suspend fun getEvents(
            @Query("tags_search") tags: String?,
            @Query("limit") limit: Int?
        ): Response<EventsData>

        @GET("v1/event/{id}")
        suspend fun getEventById(
            @Path("id") eventId: String
        ): Response<Event>

        @GET("v2/activities")
        suspend fun getActivities(
            @Query("tags_search") tags: String?,
            @Query("limit") limit: Int?
        ): Response<ActivitiesData>

        @GET("v2/activity/{id}")
        suspend fun getActivityById(
            @Path("id") activityId: String
        ): Response<Activity>
    }

    val service: Service = retrofit.create(Service::class.java)
}
