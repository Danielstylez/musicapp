package com.mobile.musicapp.gallery.list.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mobile.musicapp.R
import com.mobile.musicapp.gallery.detail.GalleryDetailActivity
import com.mobile.musicapp.home.view.HomeActivity
import kotlinx.android.synthetic.main.gallery_fragment.*

class GalleryFragment : Fragment() {
    lateinit var fragmentView: View
    lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeActivity = activity as HomeActivity
        homeActivity.setUpToolbar()
        homeActivity.setToolbarTitle(getString(R.string.gallery_title))
        homeActivity.setToolbarColor(ContextCompat.getColor(homeActivity, R.color.galleryPrimary))
        homeActivity.setPaletteColor(GalleryFragment::class.java.simpleName)

        fragmentView = inflater.inflate(R.layout.gallery_fragment, container, false)

        galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        galleryViewModel.loadGallery().observe(this, Observer {
            val metrics = resources.displayMetrics

            val width = metrics.widthPixels / (metrics.densityDpi / 160)
            val gridNumber = if (width >= 600) 6 else 4

            val adapter = GalleryAdapter(((metrics.widthPixels / gridNumber) * 1.2).toInt())
            adapter.onGalleryImageListener = object : OnGalleryImageListener {
                override fun onGalleryImageClicked(url: String) {
                    val intent =
                        Intent(this@GalleryFragment.context, GalleryDetailActivity::class.java)
                    intent.putExtra(GalleryDetailActivity.GALLERY_DETAIL_PARAM, url)
                    startActivity(intent)
                }
            }
            adapter.addAll(it)

            gallery_list.layoutManager =
                StaggeredGridLayoutManager(gridNumber, StaggeredGridLayoutManager.VERTICAL)
            gallery_list.adapter = adapter
        })

        return fragmentView
    }
}