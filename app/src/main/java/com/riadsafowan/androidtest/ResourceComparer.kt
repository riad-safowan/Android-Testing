package com.riadsafowan.androidtest

import android.content.Context

class ResourceComparer {
    fun isEqual(context: Context, resId: Int, text: String): Boolean {
        return context.getString(resId) == text
    }
}