package com.ybo.fabulouslynovel.presentation.base

class UiEvent<T>(private var  value:T?)
    {
    /** whether the event was consumed (read) */
    var consumed: Boolean = false
        private set

    /**Returns the value and prevents it from being read again*/
    fun getContentIfNotHandled(): T? {
        return if (consumed) {
            null
        } else {
            consumed = true
            value
        }
    }


    }