package com.example.countryapp.network

import com.example.countryapp.model.CountryFetchingError
import com.example.countryapp.model.CountryModel
import com.example.countryapp.model.bus_events_model.CountryBoarderItemEvent
import com.example.countryapp.model.bus_events_model.CountryErrorEvent
import com.example.countryapp.model.bus_events_model.CountryListEvent
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    companion object {
        const val TAG = "REPOSITORY"
    }

    fun getAllCountries() {
        RestClient.service.getCountries().enqueue(object : Callback<List<CountryModel>> {
            override fun onResponse(call: Call<List<CountryModel>>, response: Response<List<CountryModel>>) {
                EventBus.getDefault().post(CountryListEvent(response.body()))
            }

            override fun onFailure(call: Call<List<CountryModel>>, t: Throwable) {
                EventBus.getDefault()
                    .post(CountryErrorEvent(CountryFetchingError("Something went wrong")))
            }

        })
    }

    fun getCountriesByName(countryName: String) {
        RestClient.service.getNamedCountry(countryName)
            .enqueue(object : Callback<List<CountryModel>> {
                override fun onResponse(call: Call<List<CountryModel>>, response: Response<List<CountryModel>>) {
                    EventBus.getDefault().post(CountryBoarderItemEvent(response.body()))
                }

                override fun onFailure(call: Call<List<CountryModel>>, t: Throwable) {
                    EventBus.getDefault()
                        .post(CountryErrorEvent(CountryFetchingError("Something went wrong")))
                }

            })
    }
}