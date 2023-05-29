package com.ybo.fabdata.usecases

import com.ybo.fabdata.DataModuleHandler
import com.ybo.fabdata.api.ApiSeat
import com.ybo.fabdata.model.EntityEvent

/** usecase for getting event from a searched pattern */
class GettingSearchedEvents (private val eventFactory: EntityEvent.Factory, val api : ApiSeat)
    {

    suspend fun execute(inSearchedPattern: String)  : DataModuleHandler.DataResult<List<EntityEvent>>
        {
        DataModuleHandler.logger.log("GETTING_SEARCHED_EVENTS", "usecase asking for events")
        //defering to API for actual request
        val vSortingRule : ApiSeat.SortingRule = ApiSeat.SortingRule(ApiSeat.SortingAttributeForEvents.NONE,false,null)
        val vResult = api.getEventBySearchedPattern(inSearchedPattern,vSortingRule)
        //we have the result of the Api call, we will turn it into its Entity counterpart (ApiModelEvents to EntityEvents)
        return DataModuleHandler.DataResult(vResult.error, vResult.data?.let { inListOfApiEvents ->
            inListOfApiEvents.map {eventFactory.create(it) }
        }).also {
            DataModuleHandler.logger.log("GETTING_SEARCHED_EVENTS","returning searched events $it")
        }
        }
    }