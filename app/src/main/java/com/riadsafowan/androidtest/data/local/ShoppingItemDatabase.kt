package com.riadsafowan.androidtest.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShoppingItem::class], version = 1)
abstract class ShoppingItemDatabase : RoomDatabase() {

    abstract fun shoppingDao(): ShoppingDao

    companion object{
        private const val DB_NAME = "Database.db"

        // Get reference of the LanguageDatabase and assign it null value
        @Volatile
        private var instance : ShoppingItemDatabase? = null
        private val LOCK = Any()

        // create an operator fun which has context as a parameter
        // assign value to the instance variable
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also{
                instance = it
            }
        }
        // create a buildDatabase function assign the required values
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ShoppingItemDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()
    }
}