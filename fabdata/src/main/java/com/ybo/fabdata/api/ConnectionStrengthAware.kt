package com.ybo.fabdata.api

/** an object having knowledge of the internet connection quality.*/
interface ConnectionStrengthAware
{
    fun isInternetAvailable() : Boolean
}