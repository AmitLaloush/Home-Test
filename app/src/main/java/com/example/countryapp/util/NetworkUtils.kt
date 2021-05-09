package com.example.countryapp.util

class NetworkUtils {
    companion object {
        const val BASE_URL = "https://restcountries.eu/rest/v2/"
        const val ALL_COUNTRIES_API = "${BASE_URL}all"
        const val NAMED_COUNTRY_API = "${BASE_URL}name/"
    }
}