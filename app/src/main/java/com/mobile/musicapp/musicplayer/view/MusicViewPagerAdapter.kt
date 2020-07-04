package com.mobile.musicapp.musicplayer.view

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.mobile.musicapp.R
import com.mobile.musicapp.musicplayer.data.Song

class MusicViewPagerAdapter(var songList: List<Song>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(container.context)
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        imageView.layoutParams = params

        Glide.with(container.context)
            .load(songList[position].image)
            .placeholder(R.drawable.placeholder_image)
            .into(imageView)

        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun getCount(): Int = songList.size

    override fun isViewFromObject(view: View, obj: Any): Boolean = obj == view
}