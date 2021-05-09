package com.example.countryapp.view.country_list

import android.view.MenuItem
import com.example.countryapp.adapter.CountryAdapter
import com.example.countryapp.model.CountryModel
import com.example.countryapp.view.IBaseView

interface ICountryList {
    interface View : IBaseView {
        fun initRecyclerView()
        fun setAdapter(adapter: CountryAdapter)
        fun setData(newData: List<CountryModel>)
        fun onItemSelected(boarders: List<String>)
    }

    interface Presenter {
        fun onViewCreated()
        fun onResume()
        fun onStop()
        fun onMenuOptionSelected(id: MenuItem)
    }
}