package com.example.dibujosdeldia.ui.main.api.net.earth

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EarthAPI {
    @GET("planetary/earth/assets")
    fun getPictureOfTheDay(
        @Query("lon") lon: Float,
        @Query("lat") lat: Float,
        @Query("date") date: String,
        @Query("api_key") apiKey: String,
    ): Call<EarthResponseData>
}