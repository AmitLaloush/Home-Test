package com.example.countryapp.model.bus_events_model

import com.example.countryapp.model.CountryFetchingError
import com.example.countryapp.model.CountryModel

data class CountryListEvent(val countries: List<CountryModel>?)
data class CountryBoardersEvent(val countries: List<String>?)
data class CountryBoarderItemEvent(val countries: List<CountryModel>?)
data class CountryErrorEvent(val t: CountryFetchingError)