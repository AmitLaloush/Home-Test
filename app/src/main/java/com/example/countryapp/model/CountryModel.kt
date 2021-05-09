package com.example.countryapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class CountryModel(
    @SerializedName("nativeName") val nativeName: String,
    @SerializedName("name") val name: String,
    @SerializedName("area") val area: Double,
    @SerializedName("borders") val borders: List<String>,
    @SerializedName("cioc") val countryShortcutName: String
) : Comparable<CountryModel>, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.createStringArrayList() ?: mutableListOf(),
        parcel.readString() ?: ""
    )

    override fun compareTo(other: CountryModel): Int {
        if (nativeName == other.nativeName && name == other.name && area == other.area)
            return 0
        return -1
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nativeName)
        parcel.writeString(name)
        parcel.writeDouble(area)
        parcel.writeStringList(borders)
        parcel.writeString(countryShortcutName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CountryModel> {
        override fun createFromParcel(parcel: Parcel): CountryModel {
            return CountryModel(parcel)
        }

        override fun newArray(size: Int): Array<CountryModel?> {
            return arrayOfNulls(size)
        }
    }

}