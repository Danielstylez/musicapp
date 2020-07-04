package com.mobile.musicapp.profile.data

import androidx.lifecycle.LiveData
import com.mobile.musicapp.profile.data.db.AppDatabase
import com.mobile.musicapp.profile.data.db.User

class ProfileRepository(val appDatabase: AppDatabase) {

    suspend fun getUser(): User {
        return appDatabase.userDao().getUser(0)
    }

    fun updateUser(user: User) {
        appDatabase.userDao().insertUser(user)
    }
}