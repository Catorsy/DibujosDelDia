package com.example.dibujosdeldia.ui.main.api.net.mars

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarsAPI {
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getPictureOfTheDay(
        @Query("sol") sol : Int,
        @Query("camera") camera : String,
        @Query("api_key") apiKey: String,
    ) : Call<MarsResponseData>
}