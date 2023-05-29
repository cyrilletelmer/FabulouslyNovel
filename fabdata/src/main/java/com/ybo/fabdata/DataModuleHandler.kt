package com.ybo.fabdata

import com.ybo.fabdata.api.ApiModelEvent
import com.ybo.fabdata.api.ApiSeat
import com.ybo.fabdata.api.ConnectionStrengthAware
import com.ybo.fabdata.api.ElementaryApiCalls
import com.ybo.fabdata.api.ExceptionPolicyForAPI
import com.ybo.fabdata.model.EntityEvent
import com.ybo.fabdata.model.EntityEventFactory
import com.ybo.fabdata.usecases.GettingHottestEventsToCome
import com.ybo.fabdata.usecases.GettingSearchedEvents

/** Entry point for the DATA part of the app. Client of this module use this object to obtain the data they are interested in*/
class DataModuleHandler
    {

    companion object
        {
        /** this object deals with transforming network exceptions into a [DataError.Error] objects*/
        var exceptionPolicy : ExceptionPolicyForAPI = DefaultExceptionPolicyForAPI()
        /** this object deals with checking internet before making network calls */
        var connectionStrengthAware : ConnectionStrengthAware = StubConnectionStrengthAware()
        /** this object is the abstraction of the SeatGeek API */
        var elementaryApiCalls: ElementaryApiCalls = StubElementaryAPICalls()
        /** what will build the event entities*/
        var eventFactory :EntityEvent.Factory = EntityEventFactory()
        /** object that will be used to write logs in this module */
        var logger : LogStrategy = LogStrategy()
        }

    /** initializes this DATA library with android-aware concretions. */
    fun initialize(inExceptionPolicy: ExceptionPolicyForAPI?, inConnectionWatcher:ConnectionStrengthAware, inElementaryAPI: ElementaryApiCalls)
        {
        if(inExceptionPolicy != null)
            exceptionPolicy = inExceptionPolicy
        connectionStrengthAware = inConnectionWatcher
        elementaryApiCalls = inElementaryAPI
        }

    /** object (usecase) able to request most popular events */
    fun getMostPopularEvents():GettingHottestEventsToCome
        {
        return GettingHottestEventsToCome (
            eventFactory,
            ApiSeat(connectionStrengthAware,exceptionPolicy, elementaryApiCalls)
            )
        }

    /** object (usecase) able to request events from a searched pattern */
    fun getEventsFromSearchedPattern( ):GettingSearchedEvents
        {
        return GettingSearchedEvents (
            eventFactory,
            ApiSeat(connectionStrengthAware,exceptionPolicy, elementaryApiCalls)
            )
        }


    /** result of an api call, even if failed */
    open class DataResult<T>(val error:DataError.Error, val data : T? )
        {
        fun isSuccessful() : Boolean
            {
            return error is DataError.NoError
            }
        }



    //==============================================================================================
    // STUBS:

    internal class DefaultExceptionPolicyForAPI: ExceptionPolicyForAPI
        {
        override fun exceptionToError(inException: Exception): DataError.Error {
            return DataError.OtherError(0,inException.message)
        }
        }

    internal class StubConnectionStrengthAware : ConnectionStrengthAware
        {
        override fun isInternetAvailable(): Boolean {
            TODO("provide a ConnectionStrengthAware object")
        }
        }

    internal class StubElementaryAPICalls : ElementaryApiCalls
        {
            override suspend fun eventByIdOrThrow(inId: Int?): ApiModelEvent {
                TODO("Not yet implemented")
            }

            override suspend fun eventsOrThrow(
                inPageSize: Int?,
                inCategory: String?,
                inSortingRule: String?,
                inPage: Int
            ): List<ApiModelEvent> {
                TODO("Not yet implemented")
            }

            override suspend fun eventsBySearchedPatternOrThrow(
                inPattern: String,
                inSortingRule: String?
            ): List<ApiModelEvent> {
                TODO("Not yet implemented")
            }


        }

    /** logger to be used in this lib. Can be overriden to use android logging */
    open class LogStrategy
        {
        open fun log(inTag:String, inComment:String){}
        }
    }