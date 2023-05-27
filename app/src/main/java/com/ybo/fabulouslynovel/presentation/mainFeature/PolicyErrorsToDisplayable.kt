package com.ybo.fabulouslynovel.presentation.mainFeature

import com.ybo.fabdata.DataError
import com.ybo.fabulouslynovel.R

/** policy object able to transform a error from DATA into a displayable object for VIEW*/
class PolicyErrorsToDisplayable
    {
        companion object
        {
            const val NO_ERROR : Int = 0
        }

        /** transform error into string id for display */
        fun toDisplayableError(inError: DataError.Error) : Int
            {
            return when(inError)
                {
                is DataError.NoInternetError -> R.string.error_no_internet
                is DataError.DataModuleNotInitializedError -> R.string.error_internal
                is DataError.OtherError -> R.string.error_general
                is DataError.TimeoutError -> R.string.error_timeout
                is DataError.NoError -> NO_ERROR
                }
            }
    }