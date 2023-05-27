package com.ybo.fabulouslynovel.presentation.mainFeature

import com.ybo.fabdata.api.ConnectionStrengthAware

/** image with a backup resource when there is no internet */
data class DisplayableImage
    (
    val url: String?,
    val backupImageResource: Int,
    private val connectionWatcher: ConnectionStrengthAware
    )
    {
    fun canBeDownloaded() : Boolean
        {
        return url != null && connectionWatcher.isInternetAvailable()
        }
    }