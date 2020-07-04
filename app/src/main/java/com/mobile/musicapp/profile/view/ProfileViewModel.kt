package com.mobile.musicapp.profile.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.musicapp.profile.data.ProfileRepository
import com.mobile.musicapp.profile.data.db.DatabaseBuilder
import com.mobile.musicapp.profile.data.db.User
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    lateinit var profileRepository: ProfileRepository
    var userLivedata = MutableLiveData<User>()

    init {
        viewModelScope.launch {
            profileRepository = ProfileRepository(DatabaseBuilder.getInstance())
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            profileRepository.updateUser(user)
            userLivedata.postValue(profileRepository.getUser())
        }
    }

    fun getUser() {
        viewModelScope.launch {
            userLivedata.postValue(profileRepository.getUser())
        }
    }

}