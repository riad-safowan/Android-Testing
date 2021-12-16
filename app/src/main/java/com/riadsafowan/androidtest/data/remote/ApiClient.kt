package com.riadsafowan.androidtest.data.remote

import com.riadsafowan.androidtest.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {
    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY,
    ): Response<ImageResponse>
}