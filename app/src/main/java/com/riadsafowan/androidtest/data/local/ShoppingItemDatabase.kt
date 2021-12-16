package com.riadsafowan.androidtest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dagger.hilt.DefineComponent
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent

@Database(entities = [ShoppingItem::class], version = 1)
abstract class ShoppingItemDatabase : RoomDatabase() {

    abstract fun shoppingDao(): ShoppingDao

}