package com.example.countryapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.countryapp.R
import com.example.countryapp.model.CountryModel
import com.example.countryapp.model.bus_events_model.CountryBoardersEvent
import org.greenrobot.eventbus.EventBus


private class CountryDiffUtilCallback() : DiffUtil.ItemCallback<CountryModel>() {

    override fun areItemsTheSame(
        oldItem: CountryModel,
        newItem: CountryModel
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(
        oldItem: CountryModel,
        newItem: CountryModel
    ): Boolean {
        return oldItem.compareTo(newItem) == 0
    }
}

class CountryAdapter : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    private val asyncListDiffer = AsyncListDiffer(
        this,
        CountryDiffUtilCallback()
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_country_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val countryItem = asyncListDiffer.currentList[position]
        holder.bind(countryItem)
    }

    fun setData(newItems: List<CountryModel>) {
        asyncListDiffer.submitList(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        val view: View
    ) : RecyclerView.ViewHolder(view) {

        private var mCountryName = view.findViewById<TextView>(R.id.country_name)

        fun bind(country: CountryModel) {
            mCountryName.apply {
                text = "Native:${country.nativeName}\tName:${country.name}"
                setOnClickListener {
                    EventBus.getDefault().post(CountryBoardersEvent(country.borders))
                }
            }
        }
    }
}

