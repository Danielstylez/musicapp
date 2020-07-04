package com.mobile.musicapp.musicplayer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Songs")
data class Song(
    @PrimaryKey val id: Int,
    val name: String,
    val album: String,
    val duration: Long,
    val image: String,
    val path: String
)