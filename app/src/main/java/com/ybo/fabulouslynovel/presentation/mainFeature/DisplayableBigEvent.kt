package com.ybo.fabulouslynovel.presentation.mainFeature

import com.ybo.fabdata.model.EntityEvent

/** displayable form of a [EntityEvent], aimed at being displayed in a large setting */
data class DisplayableBigEvent
    (
    val icon:DisplayableImage,
    val title:String,
    val id:Int,
    val pricing : String,
    val performersShortList :String,
    val performersLongList :String,
    val date : String,
    val venueAddress : String
    )