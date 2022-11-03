package fi.urbanmappers.sighttour.apis

import fi.urbanmappers.sighttour.datamodels.PlacesData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
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
        ): PlacesData
    }

    val service: Service = retrofit.create(Service::class.java)
}
