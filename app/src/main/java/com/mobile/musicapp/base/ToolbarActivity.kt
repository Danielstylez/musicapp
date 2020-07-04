package com.mobile.musicapp.base

import android.os.Build
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.mobile.musicapp.R
import com.mobile.musicapp.home.color.MenuColor

abstract class ToolbarActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null

    fun setUpToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    fun setToolbarColor(@ColorInt color: Int) {
        toolbar?.setBackgroundColor(color)
    }

    /**
     * Cambia la paleta de colores según el menú que se haya elegido.
     *
     * @throws IllegalArgumentException
     */
    open fun setPaletteColor(tag: String) {
        val dark = MenuColor.getDarkRes(tag)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, dark)
        }
    }
}