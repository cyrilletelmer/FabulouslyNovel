package com.ybo.fabulouslynovel.presentation.mainFeature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ybo.fabdata.model.EntityEvent
import com.ybo.fabulouslynovel.bridgeToData.DataModuleAccess
import com.ybo.fabulouslynovel.presentation.base.UiEvent
import kotlinx.coroutines.launch
/** viewmodel driving the MAIN feature */
class MainFeatureVM : ViewModel()
    {

    //below are OUTPUT ends of the publishing channel. The view registers to this to be informed of this VM Displayables

    //channel for list of small events
    var listOfSmallEventsChannel : LiveData<List<DisplayableEventSmall>> = MutableLiveData()

    // channel for the big event
    var bigEventChannel : LiveData<DisplayableBigEvent> = MutableLiveData()

    //channel for the waiting animation signal
    var waiterDisplayChannel : LiveData<Boolean> = MutableLiveData()

    var mPoppingErrorSignalChannel : LiveData<UiEvent<Int>> = MutableLiveData()



    //policy objects
    private var mPolicyToDisplayEvents : PolicyEventToDisplayable = PolicyEventToDisplayable(DataModuleAccess.getConnectionWatcher())
    private var mPolicyToDisplayErrors : PolicyErrorsToDisplayable = PolicyErrorsToDisplayable()

    //context
    private var  mHottestEvents: List<EntityEvent>? = null


    /** called when screen is getting visible */
    fun onVisible()
        {
        //screen getting visible. by default launch hottest event requesting
        getWaiterDisplayChannelAsMutable().value= true//display waiting animation
        viewModelScope.launch {
            //in background, let's fetch the hottest events in order to display them
            val vResult = DataModuleAccess.getMostPopularEvents().execute()
            getWaiterDisplayChannelAsMutable().value= false //hide waiting animation
            if(vResult.isSuccessful())
                {
                // we successfully fetched events, we will display them in a small setting, and the most important in a big one
                mHottestEvents = vResult.data?.also {
                    // post the list of events in disp. form
                    getListOfSmallDisplayableChannelAsMutable().value =  mPolicyToDisplayEvents.toListOfDisplayableSmall(it)
                    if (it.isNotEmpty()) //post the most important of them in big screen
                        getBigDisplayableChannelAsMutable().value =  mPolicyToDisplayEvents.toDisplayableBig(it[0])
                    }
                }
            else
                getPoppingErrorSignalChannelAsMutable().value = UiEvent(mPolicyToDisplayErrors.toDisplayableError(vResult.error))
            }
        }

    /** user clicked on event in the small list*/
    fun onClickOnSmallEvent(inPositionInList:Int)
        {
        // display in big setting the event clicked
        mHottestEvents?.let {
            if(inPositionInList < mHottestEvents!!.size)
                {
                //click on actual event: display it in the detailed setting
                getBigDisplayableChannelAsMutable().value = mPolicyToDisplayEvents.toDisplayableBig(it[inPositionInList])
                }
            else
                {
                // click on "more" pseudo event
                //goto exhaustive list
                }

            }
        }

    // Below are INPUT ends of the publishing channel. We use these methods to send Displayables to the View.

    /** mutable channel for list of small displayable events that view will display on top*/
    private fun getListOfSmallDisplayableChannelAsMutable() : MutableLiveData<List<DisplayableEventSmall>>
        {
        return listOfSmallEventsChannel as MutableLiveData<List<DisplayableEventSmall>>
        }

    /** mutable channel for big displayable event that View must highlight*/
    private fun getBigDisplayableChannelAsMutable() : MutableLiveData<DisplayableBigEvent>
        {
        return bigEventChannel as MutableLiveData<DisplayableBigEvent>
        }

    private fun getWaiterDisplayChannelAsMutable() : MutableLiveData<Boolean>
        {
        return waiterDisplayChannel as MutableLiveData<Boolean>
        }

    private fun getPoppingErrorSignalChannelAsMutable(): MutableLiveData<UiEvent<Int>>
        {
        return mPoppingErrorSignalChannel as MutableLiveData<UiEvent<Int>>
        }
    }