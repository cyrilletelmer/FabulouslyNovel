package com.ybo.fabulouslynovel.presentation.mainFeature

import com.ybo.fabdata.model.EntityEvent

/** displayable version of [EntityEvent], in small settings*/
data class DisplayableEventSmall
    (
    val icon:DisplayableImage,
    val title:String,
    val id:Int,
    val popularityScoreOutOf4 : PopularityScore,
    val isRealEvent : Boolean = true
    )
    {
    enum class PopularityScore(asInt:Int){ONE_STAR(1),TWO_STARS(2),THREE_STARS(3),FOUR_STARS(4) }
    }
