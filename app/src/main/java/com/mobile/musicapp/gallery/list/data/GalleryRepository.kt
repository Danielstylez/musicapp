package com.mobile.musicapp.gallery.list.data

import android.content.res.Resources

/**
 * TODO: Obtener una lista de imágenes usando retrofit
 */
class GalleryRepository {

    fun loadGallery(): List<String> {
        val list = mutableListOf<String>()

        val displayMetrics = Resources.getSystem().displayMetrics
        val width = displayMetrics.widthPixels / 2

        for (i in 1..50) {
            list.add("https://picsum.photos/${width}/${(width * 1.2).toInt()}?random=$i")
        }

        return list
    }
}