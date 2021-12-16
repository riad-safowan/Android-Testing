package com.riadsafowan.androidtest.di

import android.content.Context
import androidx.room.Room
import com.riadsafowan.androidtest.data.local.ShoppingDao
import com.riadsafowan.androidtest.data.local.ShoppingItemDatabase
import com.riadsafowan.androidtest.data.remote.ApiClient
import com.riadsafowan.androidtest.repositories.Repository
import com.riadsafowan.androidtest.repositories.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ShoppingItemDatabase::class.java, "shopping_db").build()

    @Singleton
    @Provides
    fun provideShoppingDao(database: ShoppingItemDatabase) = database.shoppingDao()

    @Singleton
    @Provides
    fun provideShoppingRepository(
        dao: ShoppingDao,
        apiClient: ApiClient
    ) = ShoppingRepository(dao, apiClient) as Repository

    @Singleton
    @Provides
    fun provideApiClient(): ApiClient {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://pixabay.com/api/")
            .build()
            .create(ApiClient::class.java)
    }

}