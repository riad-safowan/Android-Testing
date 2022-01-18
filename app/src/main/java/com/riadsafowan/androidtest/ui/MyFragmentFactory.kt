package com.riadsafowan.androidtest.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.riadsafowan.androidtest.adapters.ImageAdapter
import javax.inject.Inject

class MyFragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ImagePickFragment::class.java.name -> ImagePickFragment(imageAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}