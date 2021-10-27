package com.example.githubusersubmission3.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubusersubmission3.FollowersFragment
import com.example.githubusersubmission3.FollowingFragment


class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    private var followersFragment: FollowersFragment? = null
    private var followingFragment: FollowingFragment? = null


    override fun getItemCount(): Int = 2

    fun setFragment(followersFragment: FollowersFragment, followingFragment: FollowingFragment) {
        this.followersFragment = followersFragment
        this.followingFragment = followingFragment
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = followersFragment
            1 -> fragment = followingFragment
        }

        return fragment as Fragment
    }

}