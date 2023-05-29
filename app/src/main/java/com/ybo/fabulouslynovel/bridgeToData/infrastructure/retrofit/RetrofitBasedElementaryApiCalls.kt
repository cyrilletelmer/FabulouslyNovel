package com.ybo.fabulouslynovel.bridgeToData.infrastructure.retrofit

import com.google.gson.annotations.SerializedName
import com.ybo.fabdata.DataModuleHandler
import com.ybo.fabdata.api.ApiModelEvent
import com.ybo.fabdata.api.ApiModelLocation
import com.ybo.fabdata.api.ApiModelMultiImage
import com.ybo.fabdata.api.ApiModelPerformer
import com.ybo.fabdata.api.ApiModelStats
import com.ybo.fabdata.api.ApiModelVenue
import com.ybo.fabdata.api.ApiSeat
import com.ybo.fabdata.api.ElementaryApiCalls
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/** implementation of basic api calls using retrofit */
class RetrofitBasedElementaryApiCalls : ElementaryApiCalls
    {
    override suspend fun eventByIdOrThrow(inId: Int?): ApiModelEvent
        {
        throw Exception("pb")
        }

    override suspend fun eventsOrThrow
        (
        inPageSize:Int?,
        inCategory: String?,
        inSortingRule: String?,
        inPage:Int,
        ): List<ApiModelEvent>
        {
        return ApiClientRetrofit.apiService.events(inPage,inCategory,inSortingRule,null, ApiClientRetrofit.CLIENT_ID).let {
            DataModuleHandler.logger.log("APIRETROFIT","requesting events result is ${it.errorBody()?.string()} body ${it.body()}")
            if(it.isSuccessful)
                it.body()!!.events
            else
                throw Exception(it.errorBody()!!.string())
            }
        }

    override suspend fun eventsBySearchedPatternOrThrow
        (
        inPattern: String,
        inSortingRule: String?
        ): List<ApiModelEvent>
        {
        val vResponseFromRetrofit = ApiClientRetrofit.apiService.events(null,null,inSortingRule,inPattern,ApiClientRetrofit.CLIENT_ID)
        if(vResponseFromRetrofit.isSuccessful)
            return vResponseFromRetrofit.body()!!.events
        else
            throw Exception(vResponseFromRetrofit.errorBody()!!.string())
        }

    interface Retrofit
        {
        @GET("/events/{id}")
        suspend fun eventById(@Query("id") inId:Int, @Query("client_id") inClientId:String) : Response<RetrofitModelEvent>

        @GET("/events")
        suspend fun events
            (
            @Query("page")  inPage:Int?,
            @Query("type")  inCategory:String?,
            @Query("sort")  inSortingArg:String?,
            @Query("q") inSearched: String?,
            @Query("client_id") inClientId:String
            ) : Response<EventsContainer>
        }

    data class EventsContainer
        (
        val events : MutableList<RetrofitModelEvent>,
        val meta : MetaData
        )
    data class MetaData(val total:Int, val took:Int, val page:Int, val per_page:Int, val geolocation : String? )

    data class RetrofitModelEvent(
        @SerializedName("id")
        override val id: Int,
        @SerializedName("title")
        override val title: String,
        @SerializedName("url")
        override val url: String,
        @SerializedName("datetime_local")
        override val date: String,
        @SerializedName("stats")
        override  val stats: RetrofitModelStats,
        override val performers: List<RetrofitModelPerformer> = mutableListOf(),
        override val type: String = "",
        override val venue: RetrofitModelVenue?
    ) : ApiModelEvent()

    data class RetrofitModelStats(
        @SerializedName("listing_count")
        override val listingCount: Int?,
        @SerializedName("average_price")
        override val averagePrice: Int?,
        @SerializedName("lower_price")
        override val lowerPrice: Int?,
        @SerializedName("highest_price")
        override val highestPrice: Int?
    ) : ApiModelStats()

    data class RetrofitModelPerformer(
        override val id: Int,
        override val name: String,
        @SerializedName("short_name")
        override val shortName: String,
        override val url: String,
        override val image: String,
        override val images: RetrofitModelMultiImage,
        override val score: Float
    ) : ApiModelPerformer()


    data class RetrofitModelMultiImage(
        @SerializedName("large")
        override val largeImageUrl: String?,
        @SerializedName("small")
        override val smallImageUrl: String?,
        @SerializedName("medium")
        override val mediumImageUrl: String?,
        @SerializedName("huge")
        override val hugeImageUrl: String?
    ) : ApiModelMultiImage()

        data class RModelLocation(override var lat: Float, override var lng: Float) : ApiModelLocation()

    data class RetrofitModelVenue(
        override var id: Int,
        override var city: String?,
        override var name: String?,
        @SerializedName("extended_address")
        override var extendedAddress: String?,
        override var url: String?,
        override var country: String?,
        override var state: String?,
        override var score: Float?,
        override var postalCode: String?,
        override var address: String?
    ) :ApiModelVenue()






}