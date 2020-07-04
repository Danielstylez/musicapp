package com.mobile.musicapp.home.view

import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mobile.musicapp.R
import com.mobile.musicapp.base.ToolbarActivity
import com.mobile.musicapp.gallery.list.view.GalleryFragment
import com.mobile.musicapp.home.color.MenuColor
import com.mobile.musicapp.musicplayer.view.MusicPlayerFragment
import com.mobile.musicapp.profile.view.ProfileFragment
import kotlinx.android.synthetic.main.home_activity.*

/**
 * Actividad que muestra el menú principal y los correspondientes menús: Galería, Perfil del usuario
 * y Reproductor a través de fragmentos.
 */
class HomeActivity : ToolbarActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.home_activity)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.selectedMenuLiveData.observe(this, Observer {
            if (it != null) {
                showFragment(it)
            }
        })

        homeViewModel.selectMenu(MusicPlayerFragment::class.java.simpleName)
    }

    override fun onResume() {
        super.onResume()

        homeNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onPause() {
        homeNavigationView.setOnNavigationItemSelectedListener(null)

        super.onPause()
    }

    /**
     * Método que se ejecuta cuando el usuario selectiona un elemento del menú.
     *
     * @param item elemento
     *
     * @return true si se manejó el evento, false en caso contrario.
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.music_player_menu -> {
                homeViewModel.selectMenu(MusicPlayerFragment::class.java.simpleName)
            }
            R.id.gallery_menu -> {
                homeViewModel.selectMenu(GalleryFragment::class.java.simpleName)
            }
            R.id.profile_menu -> {
                homeViewModel.selectMenu(ProfileFragment::class.java.simpleName)
            }
            else -> return false
        }
        return true
    }

    /**
     * Muestra el fragmento dado si este no se encuentra registrado en el fragment manager. Si el
     * fragmento dado no existe, se lanza una excepción.
     *
     * @throws IllegalArgumentException
     */
    private fun showFragment(tag: String) {
        val fragment: Fragment? = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            val newFragment = when (tag) {
                MusicPlayerFragment::class.java.simpleName -> MusicPlayerFragment()
                GalleryFragment::class.java.simpleName -> GalleryFragment()
                ProfileFragment::class.java.simpleName -> ProfileFragment()
                else -> throw IllegalArgumentException("No existe el fragmento dado")
            }

            supportFragmentManager.beginTransaction().replace(R.id.homeContainer, newFragment, tag)
                .commitAllowingStateLoss()
        }
    }

    override fun setPaletteColor(tag: String) {
        super.setPaletteColor(tag)

        val color = ContextCompat.getColor(this, MenuColor.getPrimaryRes(tag))

        homeNavigationView.setBackgroundColor(color)
    }
}
