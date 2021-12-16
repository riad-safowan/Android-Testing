package com.riadsafowan.androidtest.repositories

import androidx.lifecycle.LiveData
import com.riadsafowan.androidtest.data.local.ShoppingItem
import com.riadsafowan.androidtest.data.remote.ImageResponse
import com.riadsafowan.androidtest.utils.Resource

interface Repository {
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)
    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>
    fun observeTotalPrice(): LiveData<Float>
    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>

}