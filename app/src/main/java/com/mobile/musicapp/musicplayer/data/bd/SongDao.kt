package com.mobile.musicapp.musicplayer.data.bd

import androidx.room.*
import com.mobile.musicapp.musicplayer.data.Song

@Dao
interface SongDao {

    @Transaction
    suspend fun synchronize(list: List<Song>) {
        deleteAll()
        insertAll(list)
    }

    @Query("SELECT * FROM Songs")
    suspend fun getSongs(): List<Song>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Song>)

    @Delete
    suspend fun delete(song: Song)

    @Query("DELETE FROM Songs")
    suspend fun deleteAll()
}