package com.example.countryapp.network

import com.example.countryapp.util.NetworkUtils.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RestClient {

    val service: ApiServices by lazy {
        val retrofit = createRetrofitClient()
        retrofit.create(ApiServices::class.java)
    }

    private fun createRetrofitClient() = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

}