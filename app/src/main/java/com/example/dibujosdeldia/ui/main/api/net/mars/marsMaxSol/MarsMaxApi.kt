package com.example.dibujosdeldia.ui.main.api.net.mars.marsMaxSol

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//можно получать все о жизни марсохода
interface MarsMaxApi {
    @GET("mars-photos/api/v1/manifests/Curiosity/")
    fun getMaxSol(
        @Query("api_key") apiKey: String,
    ) : Call <MarsMaxApiResponseData>
}