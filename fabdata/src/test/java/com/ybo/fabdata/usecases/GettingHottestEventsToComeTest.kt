package com.ybo.fabdata.usecases

import com.ybo.fabdata.DataError
import com.ybo.fabdata.DataModuleHandler
import com.ybo.fabdata.api.ApiModelEvent
import com.ybo.fabdata.api.ApiSeat
import com.ybo.fabdata.api.ConnectionStrengthAware
import com.ybo.fabdata.api.ElementaryApiCalls
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GettingHottestEventsToComeTest
{
    @Test
    fun `test getting hottest events usecase with no internet`() = runBlocking{
        val vElementaryAPI = ElementaryAPIForTest { _: Int?, _: String?, _: String?, _: Int ->
            assert(false)// should not be reached because the elementary api will not be called
            mutableListOf()
        }

        val vApiSeat = ApiSeat(noConnection(),DataModuleHandler.exceptionPolicy,vElementaryAPI)
        val vInteractor = GettingHottestEventsToCome(DataModuleHandler.eventFactory,vApiSeat)
        val vResult = vInteractor.execute()
        assert(!vResult.isSuccessful())
        assert(vResult.error is DataError.NoInternetError)

        }



    private fun noConnection() : ConnectionStrengthAware = object : ConnectionStrengthAware
        {
        override fun isInternetAvailable(): Boolean =false
        }


    private class ElementaryAPIForTest
        (
        private val functionOfInterest : (inPageSize: Int?,inCategory: String?, inSortingRule: String?,inPage: Int) -> List<ApiModelEvent>) : ElementaryApiCalls
        {
        override suspend fun eventByIdOrThrow(inId: Int?): ApiModelEvent {
            TODO("Not yet implemented")
        }

        override suspend fun eventsOrThrow(
            inPageSize: Int?,
            inCategory: String?,
            inSortingRule: String?,
            inPage: Int
        ): List<ApiModelEvent> = functionOfInterest.invoke(inPageSize,inCategory,inSortingRule,inPage)

        override suspend fun eventsBySearchedPatternOrThrow(
            inPattern: String,
            inSortingRule: String?
        ): List<ApiModelEvent> {
            TODO("Not yet implemented")
        }

    }

}