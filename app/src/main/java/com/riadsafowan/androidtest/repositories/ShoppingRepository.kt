package com.riadsafowan.androidtest.repositories

import androidx.lifecycle.LiveData
import com.riadsafowan.androidtest.data.local.ShoppingDao
import com.riadsafowan.androidtest.data.local.ShoppingItem
import com.riadsafowan.androidtest.data.remote.ApiClient
import com.riadsafowan.androidtest.data.remote.ImageResponse
import com.riadsafowan.androidtest.utils.Resource
import java.lang.Exception
import javax.inject.Inject

class ShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val apiClient: ApiClient
) : Repository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        //we can use safe api call instead of this
        return try {
            val response = apiClient.searchForImage(imageQuery)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Something went wrong", null)
            } else {
                return Resource.error("Something went wrong", null)
            }

        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. check your internet connection", null)
        }
    }
}