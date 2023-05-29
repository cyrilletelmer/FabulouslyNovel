package com.ybo.fabulouslynovel

import android.app.Application
import android.content.Context

class App : Application()
    {
    companion object
        {
        lateinit var context : Context //it's ok
        }

    override fun onCreate()
        {
        super.onCreate()
        context = applicationContext
        }
    }