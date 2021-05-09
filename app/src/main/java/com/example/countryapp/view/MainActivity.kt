package com.example.countryapp.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.countryapp.R
import com.example.countryapp.network.Repository
import com.example.countryapp.view.country_details.CountryDetailsFragment
import com.example.countryapp.view.country_list.CountriesListFragment

class MainActivity : AppCompatActivity(),
    CountriesListFragment.EventListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Repository().getAllCountries()
        replaceFragment(CountriesListFragment())
    }

    private fun replaceFragmentAndAddToBackStack(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.apply {
            addToBackStack(null)
            replace(R.id.main_frame, fragment)
            commit()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.apply {
            replace(R.id.main_frame, fragment)
            commit()
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCountrySelected(boarders: List<String>) {
        replaceFragmentAndAddToBackStack(CountryDetailsFragment.newInstance(boarders))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

}