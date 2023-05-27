package com.ybo.fabdata

class DataError {
    // possible errors in this api
    sealed class Error(var errorCode:Int?, var errorMessage: String?)

    class NoInternetError : Error(null,null)
    class TimeoutError: Error(null,null)
    class OtherError(var error:Int?, var message: String?) : Error(error,message)
    class NoError : Error(null,null)
    class DataModuleNotInitializedError : Error(null, "data module not initialized. Call DataModuleHandler.initialize()")
}