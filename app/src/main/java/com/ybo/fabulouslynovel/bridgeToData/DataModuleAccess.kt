package com.ybo.fabulouslynovel.bridgeToData

import com.ybo.fabdata.DataModuleHandler
import com.ybo.fabdata.api.ConnectionStrengthAware
import com.ybo.fabdata.usecases.GettingHottestEventsToCome
import com.ybo.fabdata.usecases.GettingSearchedEvents
import com.ybo.fabulouslynovel.bridgeToData.infrastructure.ConnectionWatcherForAndroid
import com.ybo.fabulouslynovel.bridgeToData.infrastructure.LogStrategyForAndroid
import com.ybo.fabulouslynovel.bridgeToData.infrastructure.retrofit.RetrofitBasedElementaryApiCalls

/** class making the bridge to DATA module. UX part is separated from the DATA by this facade object*/
object DataModuleAccess
    {

    private val dataModuleHandler: DataModuleHandler = DataModuleHandler()
    init {
        //dataModuleHandler.initialize(null, ConnectionWatcherDummy(), ElementaryApiDummy())
        dataModuleHandler.initialize(null, getConnectionWatcher(), RetrofitBasedElementaryApiCalls())
        DataModuleHandler.logger = LogStrategyForAndroid()
        }


    fun getUsecaseForGettingHottestEvents(): GettingHottestEventsToCome
        {
        return dataModuleHandler.getMostPopularEvents()
        }

    fun getUsecaseForEventsBySearchedPattern(): GettingSearchedEvents
        {
        return dataModuleHandler.getEventsFromSearchedPattern()
        }

    fun getConnectionWatcher(): ConnectionStrengthAware
        {
        return ConnectionWatcherForAndroid()
        }

    }