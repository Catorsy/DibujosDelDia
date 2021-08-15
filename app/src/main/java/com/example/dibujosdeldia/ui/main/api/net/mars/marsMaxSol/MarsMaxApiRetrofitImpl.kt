package com.example.dibujosdeldia.ui.main.api.net.mars.marsMaxSol

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import kotlin.jvm.Throws

class MarsMaxApiRetrofitImpl {
    private val baseUrl = "https://api.nasa.gov/"

    fun getRetrofitImpl() : MarsMaxApi {
        val podRetrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(createOkHttpClient(MarsInterceptor()))
            .build()
        return podRetrofit.create(MarsMaxApi::class.java)
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor (HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return  httpClient.build()
    }

    inner class MarsInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            return  chain.proceed(chain.request())
        }
    }
}