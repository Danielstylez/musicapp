package com.mobile.musicapp.base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobile.musicapp.musicplayer.data.Song
import com.mobile.musicapp.musicplayer.data.bd.SongDao
import com.mobile.musicapp.profile.data.User
import com.mobile.musicapp.profile.data.db.UserDao

@Database(entities = [User::class, Song::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun songDao(): SongDao
}