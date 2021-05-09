package com.example.countryapp.view.country_list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countryapp.R
import com.example.countryapp.adapter.CountryAdapter
import com.example.countryapp.model.CountryModel


class CountriesListFragment : Fragment(), ICountryList.View {

    private lateinit var mCountriesRecyclerView: RecyclerView
    private lateinit var mPresenter: CountryListPresenter
    private lateinit var mAdapter: CountryAdapter
    private lateinit var mListener: EventListener
    private lateinit var mProgressBar: ProgressBar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EventListener) {
            mListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = CountryListPresenter(this)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_countries_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)

        mPresenter.onViewCreated()
    }

    private fun initView(view: View) {
        mCountriesRecyclerView = view.findViewById(R.id.rv_countries)
        mProgressBar = view.findViewById(R.id.progress_bar)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.onResume()
    }

    companion object {
        const val TAG = "CountriesListFragment"
    }

    override fun onStop() {
        Log.d(TAG, "onStop: ")
        super.onStop()
        mPresenter.onStop()
    }


    override fun initRecyclerView() {
        mCountriesRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun setAdapter(adapter: CountryAdapter) {
        mAdapter = adapter
        mCountriesRecyclerView.adapter = mAdapter
    }

    override fun setData(newData: List<CountryModel>) {
        mAdapter.setData(newData)
    }

    override fun onItemSelected(boarders: List<String>) {
        mListener.onCountrySelected(boarders)
    }

    override fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoadingUI() {
        mProgressBar.visibility = View.VISIBLE
    }

    override fun hideLoadingUI() {
        mProgressBar.visibility = View.GONE
    }

    interface EventListener {
        fun onCountrySelected(boarders: List<String>)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        mPresenter.onMenuOptionSelected(item)
        return true
    }
}
