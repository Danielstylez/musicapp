package com.mobile.musicapp.musicplayer.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.musicapp.base.DatabaseBuilder
import com.mobile.musicapp.musicplayer.data.MusicPlayerRepository
import com.mobile.musicapp.musicplayer.data.Song
import kotlinx.coroutines.launch

class MusicPlayerViewModel : ViewModel() {

    private lateinit var musicPlayerRepository: MusicPlayerRepository

    private var songListLiveData = MutableLiveData<List<Song>>()
    var musicStateLiveData = MutableLiveData<Int>()
    var musicDurationChangeLiveData = MutableLiveData<Int>()
    var musicPositionChangedLiveData = MutableLiveData<Int>()

    init {
        viewModelScope.launch {
            musicPlayerRepository = MusicPlayerRepository(DatabaseBuilder.getInstance())
            musicPlayerRepository.setLiveData(
                musicStateLiveData,
                musicDurationChangeLiveData,
                musicPositionChangedLiveData
            )
            musicPlayerRepository.downloadFiles()
        }
    }

    fun getSongList(): LiveData<List<Song>> {
        viewModelScope.launch {
            songListLiveData.postValue(musicPlayerRepository.getSongs())
        }
        return songListLiveData
    }

    fun playClicked(song: Song) {
        viewModelScope.launch { musicPlayerRepository.playClicked(song) }
    }

    fun nextSong() {
        viewModelScope.launch { musicPlayerRepository.nextSong() }
    }

    fun previousSong() {
        viewModelScope.launch { musicPlayerRepository.prevSong() }
    }

    override fun onCleared() {
        musicPlayerRepository.release()

        super.onCleared()
    }

}