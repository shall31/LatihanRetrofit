package com.dicoding.picodiploma.latihanretrofit.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username: String = ""

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowersFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowersFragment.ARG_POSITION, position+1)
            putString(FollowersFragment.ARG_USERNAME, username)                                     //lalu mengisi variabel ARG_USERNAME dengan variabel username (yang berisi person dari DetailActivity)

        }

        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }

}