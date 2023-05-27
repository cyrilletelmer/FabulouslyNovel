package com.ybo.fabdata.model

import com.ybo.fabdata.api.ApiModelEvent
import java.time.LocalDate

/** represents an Event*/
interface EntityEvent
    {
    interface Factory
        {
        fun create(inEventFromAPI : ApiModelEvent) : EntityEvent
        }

    fun getId(): Int

    fun getMinimalPrice() : Int?

    fun getAveragePrice() : Int?


    fun getTitle(): String

    fun getType():EventType

    enum class EventType
        {
        NULL,
        CONCERT,
        FESTIVAL,
        OTHER
        }

    fun getStartingDate() : LocalDate?

    fun getImageURL(inImageType: ImageType) : String

    enum class ImageType {SMALL,MEDIUM,BIG,HUGE}

    fun getPerformers() : List<String>

    fun getAddress() : String?
    }