package com.riadsafowan.androidtest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riadsafowan.androidtest.data.local.ShoppingItem
import com.riadsafowan.androidtest.data.remote.ImageResponse
import com.riadsafowan.androidtest.repositories.Repository
import com.riadsafowan.androidtest.utils.Constants
import com.riadsafowan.androidtest.utils.Event
import com.riadsafowan.androidtest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    val shoppingItems = repository.observeAllShoppingItems()
    val totalPrice = repository.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> = _images

    private val _curImagesUrl = MutableLiveData<String>()
    val curImagesUrl: LiveData<String> = _curImagesUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> =
        _insertShoppingItemStatus

    fun setCurImagesUrl(url: String) {
        _curImagesUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name: String, amount: String, price: String) {
        if (name.isEmpty() || amount.isEmpty() || price.isEmpty()) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.error(
                        "The fields must not be empty",
                        null
                    )
                )
            )
            return
        }
        if (name.length > Constants.MAX_NAME_LENGTH) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.error(
                        "The name must not be longer than ${Constants.MAX_NAME_LENGTH} character",
                        null
                    )
                )
            )
            return
        }
        val amountInt = try {
            amount.toInt()
        } catch (e: Exception) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.error(
                        "The amount must be between 0 and ${Int.MAX_VALUE}",
                        null
                    )
                )
            )
            return
        }

        val priceF = price.toFloat()
        if (priceF < 0 || priceF > 999999999) {
            _insertShoppingItemStatus.postValue(
                Event(
                    Resource.error(
                        "The amount must be between 0 and 999999999",
                        null
                    )
                )
            )
            return
        }

        val shoppingItem =
            ShoppingItem(1, name, amountInt, priceF, _curImagesUrl.value ?: "")
        insertShoppingItemIntoDb(shoppingItem)
        setCurImagesUrl("")
        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))

    }

    fun searchForImage(imageQuery: String) {
        if (imageQuery.isEmpty()) return

        _images.value = Event(Resource.loading(null))

        viewModelScope.launch {
            val response = repository.searchForImage(imageQuery)
            _images.value = Event(response)
        }
    }


}