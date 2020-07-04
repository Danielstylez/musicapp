package com.mobile.musicapp.profile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mobile.musicapp.R
import com.mobile.musicapp.home.view.HomeActivity
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment() {
    lateinit var fragmentView: View
    lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeActivity = activity as HomeActivity
        homeActivity.setUpToolbar()
        homeActivity.setToolbarTitle(getString(R.string.profile_title))
        homeActivity.setToolbarColor(ContextCompat.getColor(homeActivity, R.color.profilePrimary))
        homeActivity.setPaletteColor(ProfileFragment::class.java.simpleName)

        fragmentView = inflater.inflate(R.layout.profile_fragment, container, false)

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        profileViewModel.userLivedata.observe(this, Observer {
            Glide.with(this)
                .load(it.image)
                .placeholder(R.drawable.profile_placeholder)
                .into(profilePic)
            profileName.text = "${it.name} ${it.lastName}"
            profileUserName.text = it.userName
            profileDescription.text = getString(R.string.description, it.description)
        })
        profileViewModel.getUser()

        return fragmentView
    }
}