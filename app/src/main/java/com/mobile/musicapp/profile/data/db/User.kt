package com.mobile.musicapp.profile.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
class User(
    @PrimaryKey val id: Int,
    val userName: String,
    val image: String,
    val name: String,
    val lastName: String,
    val description: String
)