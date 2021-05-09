package com.example.countryapp.view

interface IBaseView {
    fun showMessage(message: String)
    fun showLoadingUI()
    fun hideLoadingUI()
}