package com.ybo.fabulouslynovel.presentation.mainFeature

import com.ybo.fabdata.api.ConnectionStrengthAware
import com.ybo.fabdata.model.EntityEvent
import com.ybo.fabulouslynovel.R
import java.time.format.DateTimeFormatter

/** class specialized in transforming an EntityEvent into a its displayable version*/
class PolicyEventToDisplayable(private val connectionWatcher:ConnectionStrengthAware)
    {



    /** turning a list of events from DATA into events in UI context (small displayable),
     * with a "SEE MORE..." pseudo event at the end, representing other events not displayed */
    fun toListOfDisplayableSmall(inEvents: List<EntityEvent>):List<DisplayableEventSmall>
        {
        return inEvents.map {toDisplayableSmall(it) }.toMutableList().apply { add( fakeEventAtTheEndOfTheList()) }
        }

    /** turning an event from DATA into a displayable meant to be rendered in a big picture */
    fun toDisplayableBig(inEvent: EntityEvent) : DisplayableBigEvent
        {
        val vMostRelevantPrice = inEvent.getMinimalPrice() ?: inEvent.getAveragePrice() ?: 0
        val vPricing = if (vMostRelevantPrice!= 0)
                "<strong>"+"%.2f".format(vMostRelevantPrice.toFloat())+"</strong><font size=4><sup>€</sup></font>"
            else
                "Free"
        var vAddress = inEvent.getAddress()
        return DisplayableBigEvent(
            DisplayableImage(inEvent.getImageURL(EntityEvent.ImageType.BIG),R.drawable.broken_image, connectionWatcher),
            inEvent.getTitle(),
            inEvent.getId(),
            vPricing,
            inEvent.getPerformers().take (3).reduce { inAcc, inNewPerf -> "$inAcc, $inNewPerf" }+"...",
            inEvent.getPerformers().reduce { inAcc, inNewPerf -> "$inAcc,<br>$inNewPerf" },
            inEvent.getStartingDate()?.format(DateTimeFormatter.ISO_DATE)?:"TDB",
            vAddress ?: ""
            )
        }

    //==============================================================================================
    //private toolbox

    /** turning an event from DATA into a event in UI context (small displayable) */
    private fun toDisplayableSmall(inEvent:EntityEvent):DisplayableEventSmall
        {
        return DisplayableEventSmall(
            DisplayableImage(inEvent.getImageURL(EntityEvent.ImageType.SMALL), R.drawable.broken_image, connectionWatcher),
            inEvent.getTitle(),
            inEvent.getId(),
            DisplayableEventSmall.PopularityScore.FOUR_STARS
            )
        }

    /** a pseudo-event representing all the other event that are not displayed, at the end of a list*/
    private fun fakeEventAtTheEndOfTheList() :DisplayableEventSmall
        {
        return DisplayableEventSmall(
            DisplayableImage(null,R.drawable.more_image, connectionWatcher),
            "More...",
            -1,
            DisplayableEventSmall.PopularityScore.FOUR_STARS,
            false
        )
        }

    }