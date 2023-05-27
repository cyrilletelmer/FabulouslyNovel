package com.ybo.fabulouslynovel.bridgeToData.infrastructure.retrofit

import com.ybo.fabdata.DataError
import com.ybo.fabdata.api.ExceptionPolicyForAPI

class RetrofitBasedExceptionPolicy: ExceptionPolicyForAPI {
    override fun exceptionToError(inException: Exception): DataError.Error
        {
        return if(inException.message?.contains("timeout") == true)
            DataError.TimeoutError()
        else
            DataError.OtherError(null,inException.message)
        }
}