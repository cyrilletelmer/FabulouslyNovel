package com.ybo.fabdata.api

import com.ybo.fabdata.DataError


/** object able to transform an exception happened in the third party networking library into a local [ApiSeat.Error]
 * */
interface ExceptionPolicyForAPI
    {
    fun exceptionToError(inException: Exception): DataError.Error
    }