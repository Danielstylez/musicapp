package com.mobile.musicapp.musicplayer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.mobile.musicapp.R
import com.mobile.musicapp.home.view.HomeActivity
import com.mobile.musicapp.musicplayer.data.MusicPlayerRepository.Companion.LOADING
import com.mobile.musicapp.musicplayer.data.MusicPlayerRepository.Companion.PAUSED
import com.mobile.musicapp.musicplayer.data.MusicPlayerRepository.Companion.PLAYING
import com.mobile.musicapp.musicplayer.data.Song
import kotlinx.android.synthetic.main.music_player_fragment.*

class MusicPlayerFragment : Fragment(), ViewPager.OnPageChangeListener, View.OnClickListener {
    lateinit var fragmentView: View
    lateinit var musicPlayerViewModel: MusicPlayerViewModel
    lateinit var songList: List<Song>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeActivity = activity as HomeActivity
        homeActivity.setUpToolbar()
        homeActivity.setToolbarTitle(getString(R.string.music_player_title))
        homeActivity.setToolbarColor(
            ContextCompat.getColor(
                homeActivity,
                R.color.musicPlayerPrimary
            )
        )
        homeActivity.setPaletteColor(MusicPlayerFragment::class.java.simpleName)

        fragmentView = inflater.inflate(R.layout.music_player_fragment, container, false)

        musicPlayerViewModel = ViewModelProvider(this).get(MusicPlayerViewModel::class.java)
        musicPlayerViewModel.getSongList().observe(this, Observer {
            if (!it.isEmpty()) {
                songList = it
                val musicViewPagerAdapter = MusicViewPagerAdapter(it)
                musicPager.adapter = musicViewPagerAdapter
                setSongInformation(it[0].name, it[0].album)
            }
        })
        musicPlayerViewModel.musicStateLiveData.observe(this, Observer {
            processSongState(it ?: 0)
        })
        musicPlayerViewModel.musicDurationChangeLiveData.observe(this, Observer {
            musicSeek.progress = it
        })
        musicPlayerViewModel.musicPositionChangedLiveData.observe(this, Observer {
            musicSeek.max = it
        })

        fragmentView.findViewById<ViewPager>(R.id.musicPager)
            .addOnPageChangeListener(this)

        fragmentView.findViewById<ImageView>(R.id.musicPlay)
            .setOnClickListener(this)
        fragmentView.findViewById<ImageView>(R.id.musicNext)
            .setOnClickListener(this)
        fragmentView.findViewById<ImageView>(R.id.musicPrevious)
            .setOnClickListener(this)

        fragmentView.findViewById<SeekBar>(R.id.musicSeek)
            .setOnTouchListener { p0, p1 -> false }

        return fragmentView
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.musicPlay -> musicPlayerViewModel.playClicked(songList[musicPager.currentItem])
            R.id.musicNext -> {
                val next = musicPager.currentItem + 1
                if (next < songList.size) {
                    musicPager.setCurrentItem(next, true)
                    musicPlayerViewModel.nextSong()
                }
            }
            R.id.musicPrevious -> {
                val prev = musicPager.currentItem - 1
                if (prev >= 0) {
                    musicPager.setCurrentItem(prev, true)
                    musicPlayerViewModel.previousSong()
                }
            }
        }
    }

    private fun processSongState(it: Int) {
        when (it) {
            // Pausado
            PAUSED -> {
                musicPlay.visibility = View.VISIBLE
                musicPlay.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity!!,
                        R.drawable.play_icon
                    )
                )
                musicProgressBar.visibility = View.INVISIBLE
            }
            // Reproduciendo
            PLAYING -> {
                musicPlay.visibility = View.VISIBLE
                musicPlay.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity!!,
                        R.drawable.pause_icon
                    )
                )
                musicProgressBar.visibility = View.INVISIBLE
            }
            // Cargando
            LOADING -> {
                musicPlay.visibility = View.INVISIBLE
                musicProgressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun onPageSelected(position: Int) {
        setSongInformation(
            songList[position].name, songList[position].album
        )
        musicPlayerViewModel.nextSong()
    }

    private fun setSongInformation(name: String, album: String) {
        musicTitle.text = name
        musicAlbum.text = album
        musicPlay.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.play_icon))
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
}