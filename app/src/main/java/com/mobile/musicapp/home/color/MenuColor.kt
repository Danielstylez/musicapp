package com.mobile.musicapp.home.color

import com.mobile.musicapp.R
import com.mobile.musicapp.gallery.list.view.GalleryFragment
import com.mobile.musicapp.musicplayer.view.MusicPlayerFragment
import com.mobile.musicapp.profile.view.ProfileFragment

class MenuColor {
    companion object {
        fun getDarkRes(tag: String): Int = when (tag) {
            MusicPlayerFragment::class.java.simpleName -> R.color.musicPlayerDark
            GalleryFragment::class.java.simpleName -> R.color.galleryDark
            ProfileFragment::class.java.simpleName -> R.color.profileDark
            else -> throw IllegalArgumentException("No existe el fragmento dado")
        }

        fun getPrimaryRes(tag: String): Int = when (tag) {
            MusicPlayerFragment::class.java.simpleName -> R.color.musicPlayerPrimary
            GalleryFragment::class.java.simpleName -> R.color.galleryPrimary
            ProfileFragment::class.java.simpleName -> R.color.profilePrimary
            else -> throw IllegalArgumentException("No existe el fragmento dado")
        }
    }
}