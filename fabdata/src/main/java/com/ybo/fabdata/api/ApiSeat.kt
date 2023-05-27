package com.ybo.fabdata.api

import com.ybo.fabdata.DataError
import com.ybo.fabdata.DataModuleHandler


/** an object able to make calls to the Seat API and return objects mirroring the JSONs */
class ApiSeat
    (
    var internetConnectionChecker: ConnectionStrengthAware,
    var apiExceptionPolicy: ExceptionPolicyForAPI,
    var elementaryApiCalls: ElementaryApiCalls
    )
    {


    /** gets a specific event */
    suspend fun getEventById(inId:Int) : ApiResult<ApiModelEvent>
        {
        return checkInternetAndDoTheRequest {
            elementaryApiCalls.eventByIdOrThrow(inId)
        }
        }


    /** gets a list of ApiModelEvents */
    suspend fun getEvents( inPageSize:Int?, inCategory: EventCategory, inSortingRule:SortingRule, inPage:Int = 1)
        : ApiResult<List<ApiModelEvent>>
        {
        return checkInternetAndDoTheRequest {
                elementaryApiCalls.eventsOrThrow(inPageSize,inCategory.asString,sortingRuleToSortingArg(inSortingRule),inPage)
            }
        }

    /** gets a list of events from a entered word in search bar */
    suspend fun getEventBySearchedPattern(inPattern: String, inSortingRule:SortingRule)
        : ApiResult<List<ApiModelEvent>>
    {
        return checkInternetAndDoTheRequest {
            elementaryApiCalls.eventsBySearchedPatternOrThrow(inPattern,"")
        }
    }


    class SortingRule(val sortingAttribute: SortingAttributeForEvents, val ascending :Boolean, val startingValue:String?)
    enum class SortingAttributeForEvents
        {
        SCORE,
        DATE,
        NONE
        }

    enum class EventCategory(val asString:String?)
        {
        ALL(null),
        CONCERT("concert"),
        FESTIVAL("music_festival")
        }


    /** result of an api call, even if failed */
    open class ApiResult<T>(val error:DataError.Error, val result : T? )
        {
        override fun toString() :String { return "<error: $error, res $result >"}
        }




    private suspend fun <T> checkInternetAndDoTheRequest(  inElementaryRequestThatCanThrow : suspend ()-> T) : ApiResult<T>
        {
        DataModuleHandler.logger.log("API_REQUEST", "making a request after having checked internet")
        var outResult = ApiResult<T>(DataError.OtherError(null,null),null)
        if(internetConnectionChecker is DataModuleHandler.StubConnectionStrengthAware || elementaryApiCalls is DataModuleHandler.StubElementaryAPICalls)
            outResult = ApiResult(DataError.DataModuleNotInitializedError(),null)
        else if(!internetConnectionChecker.isInternetAvailable())
            outResult = ApiResult(DataError.NoInternetError(),null)
        else
            {
            // we have made basic checks, now let's do the request.
            try
                {
                outResult = ApiResult(DataError.NoError(),inElementaryRequestThatCanThrow())
                }
            catch (vE:Exception)
                {
                vE.printStackTrace()
                outResult = ApiResult(apiExceptionPolicy.exceptionToError(vE),null)
                }
            }
        return outResult.also { DataModuleHandler.logger.log("API_REQUEST","returning from API $it ") }
        }

    private fun sortingRuleToSortingArg(inSortingRule: SortingRule) : String?
        {
        val vDirection = if(inSortingRule.ascending) "asc" else "desc"
        return when(inSortingRule.sortingAttribute)
            {
            SortingAttributeForEvents.SCORE -> "score.$vDirection"
            SortingAttributeForEvents.DATE -> "datetime_utc.$vDirection"
            SortingAttributeForEvents.NONE -> null
            }.also {
            DataModuleHandler.logger.log("API_REQUEST","returning sorting rule $it ")
            }
        }

    }