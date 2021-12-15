package com.riadsafowan.androidtest

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before

import org.junit.Test

class ResourceComparerTest {

    private lateinit var resourceComparer: ResourceComparer

    @Before
    fun setup(){
        resourceComparer = ResourceComparer()
    }

    @After
    fun tearDown(){
        // close if needs be
    }

    @Test
    fun stringResourceSameAsString_returnsTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name, "Android Test")

        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceDifferentAsString_returnsFalse() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name, "Android Testing")

        assertThat(result).isFalse()
    }
}