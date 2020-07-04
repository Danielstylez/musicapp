package com.mobile.musicapp.profile.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM User where id = :id")
    suspend fun getUser(id: Int): User

    @Insert
    fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)
}