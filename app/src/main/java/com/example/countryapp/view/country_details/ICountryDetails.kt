package com.example.countryapp.view.country_details

import com.example.countryapp.adapter.CountryAdapter
import com.example.countryapp.model.CountryModel
import com.example.countryapp.view.IBaseView

interface ICountryDetails {
    interface View : IBaseView {
        fun initRecyclerView()
        fun setAdapter(adapter: CountryAdapter)
        fun setData(newData: List<CountryModel>)
    }

    interface Presenter {
        fun onViewCreated()
    }
}