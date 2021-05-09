package com.example.countryapp.view.country_details

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countryapp.R
import com.example.countryapp.adapter.CountryAdapter
import com.example.countryapp.model.CountryModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class CountryDetailsFragment : Fragment(), ICountryDetails.View {


    private lateinit var mNeighborRecyclerView: RecyclerView
    private lateinit var mPresenter: CountryDetailsPresenter
    private lateinit var mAdapter: CountryAdapter
    private lateinit var mProgressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mPresenter = CountryDetailsPresenter(
                it.getStringArrayList(ARGUMENT_BOARDER_COUNTRIES) as ArrayList<String>,
                this
            )
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNewCountryReceived(country: CountryModel) {
        Log.d(TAG, "onNewCountryReceived: $country")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initUI(view)
        mPresenter.onViewCreated()
    }

    private fun initUI(view: View) {
        mNeighborRecyclerView = view.findViewById(R.id.rv_neighbor_list)
        mProgressBar = view.findViewById(R.id.progress_bar)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    companion object {
        const val TAG = "CountryDetailsFragment"
        const val ARGUMENT_BOARDER_COUNTRIES = "boarder_countries"

        @JvmStatic
        fun newInstance(boardersNames: List<String>) =
            CountryDetailsFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(
                        ARGUMENT_BOARDER_COUNTRIES,
                        boardersNames as ArrayList<String>
                    )
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun initRecyclerView() {
        mNeighborRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun setAdapter(adapter: CountryAdapter) {
        mAdapter = adapter
        mNeighborRecyclerView.adapter = mAdapter
    }

    override fun setData(newData: List<CountryModel>) {
        mAdapter.setData(newData)
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
}