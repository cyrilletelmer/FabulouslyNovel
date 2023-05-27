package com.ybo.fabulouslynovel.bridgeToData.infrastructure

import android.util.Log
import com.ybo.fabdata.DataModuleHandler

class LogStrategyForAndroid : DataModuleHandler.LogStrategy()
    {
    override fun log(inTag:String, inComment:String)
        {
        Log.d("<Fabulous>$inTag", inComment)
        }
    }