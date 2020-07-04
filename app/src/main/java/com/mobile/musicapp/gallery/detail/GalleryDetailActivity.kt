package com.mobile.musicapp.gallery.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.mobile.musicapp.R
import com.mobile.musicapp.base.ToolbarActivity
import com.mobile.musicapp.gallery.list.view.GalleryFragment
import kotlinx.android.synthetic.main.gallery_detail_activity.*

/**
 * TODO: Agregar la funcionalidad de descargar la imagen
 */
class GalleryDetailActivity : ToolbarActivity() {

    companion object {
        val GALLERY_DETAIL_PARAM = "GALLERY_DETAIL_PARAM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.gallery_detail_activity)

        setUpToolbar()
        setToolbarTitle(getString(R.string.gallery_title))
        setToolbarColor(ContextCompat.getColor(this, R.color.galleryPrimary))
        setPaletteColor(GalleryFragment::class.java.simpleName)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        Glide.with(this)
            .load(intent.getStringExtra(GALLERY_DETAIL_PARAM))
            .placeholder(R.drawable.placeholder_image)
            .into(galleryDetailImage)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}