package com.ybo.fabdata.api

interface ElementaryApiCalls
    {
    suspend fun eventByIdOrThrow(inId:Int?):ApiModelEvent

    suspend fun eventsOrThrow
        (

        inPageSize:Int?,
        inCategory: String?,
        inSortingRule: String?,
        inPage:Int =0,
        ) : List<ApiModelEvent>

    suspend fun eventsBySearchedPatternOrThrow
        (
        inPattern: String,
        inSortingRule: String?
        ): List<ApiModelEvent>
    }