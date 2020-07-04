package com.mobile.musicapp.profile.data

import com.mobile.musicapp.base.AppDatabase

class ProfileRepository(val appDatabase: AppDatabase) {

    suspend fun getUser(): User {
        return appDatabase.userDao().getUser(0)
    }

    fun updateUser(user: User) {
        appDatabase.userDao().insertUser(user)
    }
}