package com.example.countryapp.view.country_list

import android.util.Log
import android.view.MenuItem
import com.example.countryapp.R
import com.example.countryapp.adapter.CountryAdapter
import com.example.countryapp.model.CountryModel
import com.example.countryapp.model.bus_events_model.CountryBoardersEvent
import com.example.countryapp.model.bus_events_model.CountryErrorEvent
import com.example.countryapp.model.bus_events_model.CountryListEvent
import com.example.countryapp.network.Repository
import com.example.countryapp.view.country_details.CountryDetailsPresenter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class CountryListPresenter(val view: ICountryList.View) : ICountryList.Presenter {
    private val mCountryAdapter: CountryAdapter = CountryAdapter()
    private val mCountriesList = mutableListOf<CountryModel>()

    companion object {
        const val TAG = "CountryListPresenter"
    }

    init {
        Repository().getAllCountries()
    }

    override fun onViewCreated() {
        view.apply {
            initRecyclerView()
            setAdapter(mCountryAdapter)
            showLoadingUI()
        }
    }

    override fun onResume() {
        EventBus.getDefault().register(this)
        if (mCountriesList.isNotEmpty()) {
            view.hideLoadingUI()
        }
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
    }

    override fun onMenuOptionSelected(item: MenuItem) {
        when (item.itemId) {
            R.id.sort_area_ascending ->
                mCountriesList.sortBy { it.area }
            R.id.sort_area_descending ->
                mCountriesList.sortByDescending { it.area }
            R.id.sort_name_ascending ->
                mCountriesList.sortBy { it.name }
            R.id.sort_name_descending ->
                mCountriesList.sortByDescending { it.name }
        }
        view.setData(mCountriesList)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getListCountries(newData: CountryListEvent) {
        Log.d(TAG, "getListCountries: $newData")
        newData.countries?.let {
            mCountriesList.clear()
            mCountriesList.addAll(it)
            view.apply {
                setData(mCountriesList)
                hideLoadingUI()
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCountryClicked(countryBoarder: CountryBoardersEvent) {
        view.onItemSelected(countryBoarder.countries ?: mutableListOf())
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCountryFetchingError(error: CountryErrorEvent) {
        Log.e(CountryDetailsPresenter.TAG, "onCountryFetchingError: ${error.t.error}")
        view.apply {
            showMessage(error.t.error)
            hideLoadingUI()
        }
    }
}