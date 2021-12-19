package com.riadsafowan.androidtest.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.riadsafowan.androidtest.getOrAwaitValue
import com.riadsafowan.androidtest.launchFragmentInHiltContainer
import com.riadsafowan.androidtest.ui.ShoppingFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        hiltRule.inject()

        dao = database.shoppingDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testLaunchContainerInHilt() {
        launchFragmentInHiltContainer<ShoppingFragment> {

        }
    }

    @Test
    fun insertShoppingItem() = runBlockingTest {
        val item1 = ShoppingItem(1, "mango", 1, 10f, "osjkafoiaf")
        val item2 = ShoppingItem(2, "mango", 1, 10f, "osjkafoiaf")
        val item3 = ShoppingItem(3, "mango", 1, 10f, "osjkafoiaf")

        dao.insertShoppingItem(item1)
        dao.insertShoppingItem(item2)
        dao.insertShoppingItem(item3)

        val allItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allItems).contains(item3)

    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val item1 = ShoppingItem(1, "mango", 1, 10f, "osjkafoiaf")
        val item2 = ShoppingItem(2, "mango", 1, 10f, "osjkafoiaf")

        dao.insertShoppingItem(item1)
        dao.insertShoppingItem(item2)
        dao.deleteShoppingItem(item1)

        val allItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allItems).doesNotContain(item1)
    }

    @Test
    fun observePriceShoppingItem() = runBlockingTest {
        val item1 = ShoppingItem(1, "mango", 1, 10f, "osjkafoiaf")
        val item2 = ShoppingItem(2, "mango", 2, 5f, "osjkafoiaf")
        val item3 = ShoppingItem(3, "mango", 3, 10f, "osjkafoiaf")

        dao.insertShoppingItem(item1)
        dao.insertShoppingItem(item2)
        dao.insertShoppingItem(item3)

        val allItems = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(allItems).isEqualTo(50)
    }

}

