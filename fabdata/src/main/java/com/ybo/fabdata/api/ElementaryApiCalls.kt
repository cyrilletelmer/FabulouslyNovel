package com.ybo.fabdata.api

/** most basic abstraction of API seatheek*/
interface ElementaryApiCalls
    {
    /** gets an event by its id or throw*/
    suspend fun eventByIdOrThrow(inId:Int?):ApiModelEvent

    /** gets events  or throw*/
    suspend fun eventsOrThrow
        (
        inPageSize:Int?,
        inCategory: String?,
        inSortingRule: String?,
        inPage:Int =0,
        ) : List<ApiModelEvent>

    /** gets event by searched keywords */
    suspend fun eventsBySearchedPatternOrThrow
        (
        inPattern: String,
        inSortingRule: String?
        ): List<ApiModelEvent>
    }