package com.example.countryapp.model

data class CountryFetchingError(val error:String): Throwable(error)