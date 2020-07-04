package com.mobile.musicapp.home.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    var selectedMenuLiveData = MutableLiveData<String>()

    fun selectMenu(tag: String) {
        selectedMenuLiveData.value = tag
    }
}