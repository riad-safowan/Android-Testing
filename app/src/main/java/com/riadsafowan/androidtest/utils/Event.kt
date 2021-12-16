package com.riadsafowan.androidtest.utils

open class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    fun getContentIfNOtHandled(): T? {
        return if (hasBeenHandled) null
        else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}