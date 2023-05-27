package com.ybo.fabulouslynovel.bridgeToData.infrastructure

import android.content.Context
import android.net.ConnectivityManager
import com.ybo.fabdata.api.ConnectionStrengthAware
import com.ybo.fabulouslynovel.App

/** concretion of DATA object aimed at checking connection availability on android */
class ConnectionWatcherForAndroid: ConnectionStrengthAware
    {
    override fun isInternetAvailable(): Boolean
        {
        val vConnectionManager = App.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return vConnectionManager.activeNetworkInfo?.isConnected ?:false
        }
    }