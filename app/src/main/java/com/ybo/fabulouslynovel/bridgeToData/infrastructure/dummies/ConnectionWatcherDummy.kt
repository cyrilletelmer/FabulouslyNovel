package com.ybo.fabulouslynovel.bridgeToData.infrastructure.dummies

import com.ybo.fabdata.api.ConnectionStrengthAware

class ConnectionWatcherDummy: ConnectionStrengthAware {
    override fun isInternetAvailable(): Boolean {
        return true;
    }
}