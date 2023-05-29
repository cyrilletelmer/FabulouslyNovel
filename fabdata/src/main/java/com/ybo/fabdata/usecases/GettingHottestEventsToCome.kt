package com.ybo.fabdata.usecases

import com.ybo.fabdata.DataModuleHandler
import com.ybo.fabdata.api.ApiSeat
import com.ybo.fabdata.model.EntityEvent

/** interactor for getting the best events to come */
class GettingHottestEventsToCome (val eventFactory: EntityEvent.Factory, val api : ApiSeat)
    {

    suspend fun execute() : DataModuleHandler.DataResult<List<EntityEvent>>
        {
        DataModuleHandler.logger.log("GETTING_HOTTEST_EVENTS_USECASE", "usecase asking the hottest events")
        //defering to API for actual request
        //val v2 = api.getEventById()
        val vSortingRule : ApiSeat.SortingRule = ApiSeat.SortingRule(ApiSeat.SortingAttributeForEvents.SCORE,false,null)
        val vResult = api.getEvents(null,ApiSeat.EventCategory.ALL,vSortingRule)
        //we have the result of the Api call, we will turn it into its Entity counterpart (ApiModelEvents to EntityEvents)
        return DataModuleHandler.DataResult(vResult.error, vResult.data?.let { inListOfApiEvents ->
            inListOfApiEvents.map {eventFactory.create(it) }
        }).also {
            DataModuleHandler.logger.log("GETTING_HOTTEST_EVENTS_USECASE","returning hottest events $it")
        }
        }
    }