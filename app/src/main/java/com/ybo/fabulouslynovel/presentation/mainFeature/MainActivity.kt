package com.ybo.fabulouslynovel.presentation.mainFeature


import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ybo.fabulouslynovel.R
import com.ybo.fabulouslynovel.presentation.base.UiEvent
import com.ybo.fabulouslynovel.presentation.base.withClickListening
import com.ybo.fabulouslynovel.presentation.base.popToast

/** VIEW part of the Main feature, driven by a [MainFeatureVM]  */
class MainActivity : AppCompatActivity() {

    lateinit var  mHottestEventsRV :RecyclerView

    lateinit var  mHottestEventsAdapter: EventAdapter

    lateinit var  mViewModelOfThisFeature : MainFeatureVM

    companion object
        {
        lateinit var ourActivity : MainActivity
        }

    override fun onCreate(savedInstanceState: Bundle?)
        {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ourActivity = this
        mHottestEventsAdapter = EventAdapter(mutableListOf(), this::onClickOnSmallEvent)
        mHottestEventsRV = findViewById(R.id.hottestEvents)
        mHottestEventsRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mHottestEventsRV.adapter = mHottestEventsAdapter
        mViewModelOfThisFeature =ViewModelProvider(this)[MainFeatureVM::class.java]
        //register to VM events
        mViewModelOfThisFeature.waiterDisplayChannel.observe(this, this::onDisplayWaitingAnimation)
        mViewModelOfThisFeature.listOfSmallEventsChannel.observe(this, this::onDisplayableListOfSmallEventsIncoming)
        mViewModelOfThisFeature.bigEventChannel.observe(this, this::onDisplayableHighlightedEventIncoming)
        mViewModelOfThisFeature.mPoppingErrorSignalChannel.observe(this,this::onErrorIncoming)
        }

    /** called when user clicked on a small icon for events */
    private fun onClickOnSmallEvent(inView :View, inPosition: Int, inType: Int)
        {
        mViewModelOfThisFeature.onClickOnSmallEvent(inPosition)
        }

    override fun onResume()
        {
        super.onResume()
        mViewModelOfThisFeature.onVisible()
        }

    //==============================================================================================
    // methods responding to VM events:

    private fun onDisplayWaitingAnimation(inDisplay:Boolean)
        {
        findViewById<View>(R.id.waitingAnimationLayout).visibility = if (inDisplay) View.VISIBLE else View.GONE
        }

    private fun onDisplayableListOfSmallEventsIncoming( inList : List<DisplayableEventSmall>)
        {
        mHottestEventsAdapter.mEvents = inList
        mHottestEventsAdapter.notifyDataSetChanged()
        }

    private fun onDisplayableHighlightedEventIncoming(inEvent: DisplayableBigEvent)
        {
        findViewById<TextView>(R.id.bigEventTitle).text = inEvent.title
        findViewById<TextView>(R.id.bigEventSubTitle).text = inEvent.performersShortList
        findViewById<TextView>(R.id.eventPriceRange).text = Html.fromHtml(inEvent.pricing)
        findViewById<TextView>(R.id.eventDate).text = inEvent.date
        findViewById<TextView>(R.id.eventAddress).text = inEvent.venueAddress
        findViewById<View>(R.id.pricingZone).visibility = View.VISIBLE
        Glide.with(this)
            .load(inEvent.icon.url)
            .error(inEvent.icon.backupImageResource)
            .into(findViewById(R.id.bigImageEvent))
        }

    private fun onErrorIncoming(inErrorEvent:UiEvent<Int>)
        {
        val vStringId = inErrorEvent.getContentIfNotHandled()
        if(vStringId != PolicyErrorsToDisplayable.NO_ERROR)
            popToast(getString(vStringId ?:R.string.error_internal))
        }



    //============================================================================
    private fun tabSetup()
        {

        }


    class OurVP(v:View) : RecyclerView.ViewHolder(v)
        {
        var icon: ImageView=v.findViewById(R.id.icon)
        var subtitle :TextView =v.findViewById(R.id.subtitle)
        }

    class EventAdapter(
        var mEvents : List<DisplayableEventSmall>,
        private val mItemClickListener: (View, Int, Int) -> Unit
        ): RecyclerView.Adapter<OurVP>()
        {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OurVP
            {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_event_small, parent, false)
            return OurVP(view).withClickListening(mItemClickListener)
            }

        override fun getItemCount(): Int { return mEvents.size }

        override fun onBindViewHolder(inHolder: OurVP, inPosition: Int)
            {
            val vItem = mEvents[inPosition]
            inHolder.subtitle.text = vItem.title
            Glide.with(ourActivity).load(vItem.icon.url).error(vItem.icon.backupImageResource).into(inHolder.icon)
            }

        }

}


