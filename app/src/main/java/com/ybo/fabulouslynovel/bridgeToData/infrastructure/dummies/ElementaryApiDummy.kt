package com.ybo.fabulouslynovel.bridgeToData.infrastructure.dummies

import android.util.Log
import com.ybo.fabdata.api.ApiModelEvent
import com.ybo.fabdata.api.ElementaryApiCalls

class ElementaryApiDummy : ElementaryApiCalls
{
    override suspend fun eventByIdOrThrow(inId: Int?): ApiModelEvent {
        TODO("Not yet implemented")
    }


    override suspend fun eventsOrThrow(

        inPageSize:Int?,
        inCategory: String?,
        inSortingRule: String?,
        inPage:Int
    ): List<ApiModelEvent>
    {
        return mutableListOf(ApiEventImplemented()).apply {
            add(ApiEventImplemented())
            add(ApiEventImplemented())
        }.also {
            Log.d("test","returning list"+it)
        }
    }

    override suspend fun eventsBySearchedPatternOrThrow(
        inPattern: String,
        inSortingRule: String?
    ): List<ApiModelEvent> {
        TODO("Not yet implemented")
    }


}