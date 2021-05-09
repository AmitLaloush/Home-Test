package com.example.countryapp.view.country_details

import android.util.Log
import com.example.countryapp.adapter.CountryAdapter
import com.example.countryapp.model.CountryModel
import com.example.countryapp.model.bus_events_model.CountryBoarderItemEvent
import com.example.countryapp.model.bus_events_model.CountryErrorEvent
import com.example.countryapp.network.Repository
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class CountryDetailsPresenter(private val boarderList: ArrayList<String>, val view: ICountryDetails.View) :
    ICountryDetails.Presenter {
    private val mCountryAdapter: CountryAdapter = CountryAdapter()
    private val mCountriesList = mutableListOf<CountryModel>()

    init {
        EventBus.getDefault().register(this)
    }

    override fun onViewCreated() {
        view.apply {
            initRecyclerView()
            setAdapter(mCountryAdapter)
            showLoadingUI()
        }
        if (boarderList.isEmpty()) {
            showBoarderErrorMessage(NO_NEIGHBOR_EXIST_ERROR)
        } else {
            boarderList.forEach {
                Repository().getCountriesByName(it)
            }
        }
    }

    private fun showBoarderErrorMessage(message: String) {
        view.apply {
            hideLoadingUI()
            showMessage(message)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCountryDetailsReceived(countryBoarder: CountryBoarderItemEvent) {
        Log.d(TAG, "onCountryDetailsReceived: ${countryBoarder.countries}")
        mCountriesList.addAll(countryBoarder.countries as List)
        view.apply {
            setData(mCountriesList)
            hideLoadingUI()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCountryFetchingError(error: CountryErrorEvent) {
        Log.e(TAG, "onCountryFetchingError: ${error.t.error}")
        showBoarderErrorMessage(error.t.error)
    }

    companion object {
        const val TAG = "CountryDetailsPresenter"

        const val NO_NEIGHBOR_EXIST_ERROR = "No neighbor exists"
    }
}