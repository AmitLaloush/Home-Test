package com.example.countryapp.network


import com.example.countryapp.model.CountryModel
import com.example.countryapp.util.NetworkUtils.Companion.ALL_COUNTRIES_API
import com.example.countryapp.util.NetworkUtils.Companion.NAMED_COUNTRY_API
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET(ALL_COUNTRIES_API)
    fun getCountries(): Call<List<CountryModel>>

    @GET("${NAMED_COUNTRY_API}{name}")
    fun getNamedCountry(@Path("name") name: String): Call<List<CountryModel>>
}