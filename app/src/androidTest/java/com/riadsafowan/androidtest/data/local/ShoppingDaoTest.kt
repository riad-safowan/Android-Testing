package com.riadsafowan.androidtest.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.riadsafowan.androidtest.getOrAwaitValue
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShoppingDaoTest {

    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.shoppingDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertShoppingItem() = runBlockingTest {
        val item1 =  ShoppingItem(1, "mango", 1, 10f, "osjkafoiaf")
        val item2 =  ShoppingItem(2, "mango", 1, 10f, "osjkafoiaf")
        val item3 =  ShoppingItem(3, "mango", 1, 10f, "osjkafoiaf")

        dao.insertShoppingItem(item1)
        dao.insertShoppingItem(item2)
        dao.insertShoppingItem(item3)

        val allItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allItems).contains(item3)

    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val item1 =  ShoppingItem(1, "mango", 1, 10f, "osjkafoiaf")
        val item2 =  ShoppingItem(2, "mango", 1, 10f, "osjkafoiaf")

        dao.insertShoppingItem(item1)
        dao.insertShoppingItem(item2)
        dao.deleteShoppingItem(item1)

        val allItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allItems).doesNotContain(item1)
    }

    @Test
    fun observePriceShoppingItem() = runBlockingTest {
        val item1 =  ShoppingItem(1, "mango", 1, 10f, "osjkafoiaf")
        val item2 =  ShoppingItem(2, "mango", 2, 5f, "osjkafoiaf")
        val item3 =  ShoppingItem(3, "mango", 3, 10f, "osjkafoiaf")

        dao.insertShoppingItem(item1)
        dao.insertShoppingItem(item2)
        dao.insertShoppingItem(item3)

        val allItems = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(allItems).isEqualTo(50)
    }

}

