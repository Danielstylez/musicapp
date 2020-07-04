package com.mobile.musicapp.gallery.list.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.musicapp.gallery.list.data.GalleryRepository

class GalleryViewModel : ViewModel() {
    val galleryRepository = GalleryRepository()
    var galleryListLiveData: MutableLiveData<List<String>>? = null

    fun loadGallery(): LiveData<List<String>> {
        if (galleryListLiveData == null) {
            galleryListLiveData = MutableLiveData()
            galleryListLiveData?.value = galleryRepository.loadGallery()
        }

        return galleryListLiveData as MutableLiveData<List<String>>
    }
}