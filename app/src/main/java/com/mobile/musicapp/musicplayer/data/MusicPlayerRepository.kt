package com.mobile.musicapp.musicplayer.data

import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.mobile.musicapp.MusicAppApplication
import com.mobile.musicapp.R
import com.mobile.musicapp.base.AppDatabase
import java.io.*
import java.util.*

class MusicPlayerRepository(val appDatabase: AppDatabase) {

    companion object {
        val PAUSED = 0
        val PLAYING = 1
        val LOADING = 2
    }

    private var musicStateLiveData: MutableLiveData<Int>? = null
    private var musicDurationChangeLiveData: MutableLiveData<Int>? = null
    private var musicPositionChangedLiveData: MutableLiveData<Int>? = null

    private var currentSongState = PAUSED
    private var currentSong: Song? = null

    private var mediaPlayer: MediaPlayer? = null

    private var timer: Timer? = null

    suspend fun downloadFiles() {
        val int = listOf(
            R.raw.track01,
            R.raw.track02,
            R.raw.track03,
            R.raw.track04,
            R.raw.track05,
            R.raw.track06
        )

        val songList = mutableListOf<Song>()

        int.forEachIndexed { index, media ->
            val path = copyFiletoExternalStorage(media, "track$index.mp3")

            val mmr = MediaMetadataRetriever()
            mmr.setDataSource(path)

            val name = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
            val album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)
            val duration =
                mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLong()
            mmr.release();

            songList.add(
                Song(
                    index,
                    name ?: "",
                    album ?: "",
                    duration ?: 0,
                    "https://vgmdownloads.com/soundtracks/kingdom-hearts-hd-1.5-remix/Cover.jpg",
                    path
                )
            )
        }

        appDatabase.songDao().synchronize(songList)
    }

    private fun copyFiletoExternalStorage(
        resourceId: Int,
        resourceName: String
    ): String {
        val path =
            File("${MusicAppApplication.INSTANCE!!.filesDir}", resourceName)
        try {
            val input: InputStream =
                MusicAppApplication.INSTANCE!!.resources.openRawResource(resourceId)

            var out: FileOutputStream? = null
            out = FileOutputStream(path)
            val buff = ByteArray(1024)
            var read = 0
            try {
                while (input.read(buff).also { read = it } > 0) {
                    out.write(buff, 0, read)
                }
            } finally {
                input.close()
                out.close()
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return path.absolutePath
    }

    suspend fun getSongs(): List<Song> {
        return appDatabase.songDao().getSongs()
    }

    fun playClicked(song: Song) {
        when (currentSongState) {
            PAUSED -> {
                playSong(song)
            }
            PLAYING -> {
                pauseSong()
            }
            LOADING -> {
                if (currentSong?.id ?: -1 == song.id) {
                    pauseSong()
                } else {
                    playSong(song)
                }
            }
        }
        currentSong = song
    }

    private fun playSong(song: Song) {
        musicStateLiveData?.postValue(LOADING)
        if (currentSong?.id ?: -1 != song.id || mediaPlayer == null) {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(MusicAppApplication.INSTANCE!!, Uri.parse(song.path))
            timer = Timer()
            timer?.schedule(object : TimerTask() {
                override fun run() {
                    try {
                        val position = mediaPlayer?.currentPosition
                        musicDurationChangeLiveData?.postValue(position)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            }, 1000, 1000)
            musicPositionChangedLiveData?.postValue(mediaPlayer?.duration)
        }
        mediaPlayer?.start()
        musicStateLiveData?.postValue(PLAYING)
        currentSongState = PLAYING
    }

    private fun pauseSong() {
        mediaPlayer?.pause()
        musicStateLiveData?.postValue(PAUSED)
        currentSongState = PAUSED
    }

    fun nextSong() {
        releaseAudio()
    }

    fun prevSong() {
        releaseAudio()
    }

    fun releaseAudio() {
        mediaPlayer?.release()
        mediaPlayer = null
        musicStateLiveData?.postValue(PAUSED)
        musicPositionChangedLiveData?.postValue(0)
        currentSongState = PAUSED
        timer?.cancel()
        timer = null
    }

    fun setLiveData(
        musicStateLiveData: MutableLiveData<Int>,
        musicDurationChangeLiveData: MutableLiveData<Int>,
        musicPositionChangedLiveData: MutableLiveData<Int>
    ) {
        this.musicStateLiveData = musicStateLiveData
        this.musicDurationChangeLiveData = musicDurationChangeLiveData
        this.musicPositionChangedLiveData = musicPositionChangedLiveData
    }

    fun release() {
        mediaPlayer?.release()
        musicStateLiveData = null
        musicDurationChangeLiveData = null
        musicPositionChangedLiveData = null
        timer?.cancel()
        timer = null

    }

}