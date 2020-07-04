package com.mobile.musicapp.profile.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mobile.musicapp.profile.data.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User where id = :id")
    suspend fun getUser(id: Int): User

    @Insert
    fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)
}