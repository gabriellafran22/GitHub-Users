package com.example.githubusersubmission3

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersubmission3.adapter.ListUserAdapter
import com.example.githubusersubmission3.api.UserResponse
import com.example.githubusersubmission3.databinding.FragmentFollowingBinding
import com.example.githubusersubmission3.viewmodel.FollowViewModel


class FollowingFragment : Fragment() {

    private var _fragmentFollowingBinding: FragmentFollowingBinding? = null
    private val binding get() = _fragmentFollowingBinding

    private lateinit var username: String
    private val followViewModel by viewModels<FollowViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentFollowingBinding = FragmentFollowingBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        binding?.rvUserFollowing?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding?.rvUserFollowing?.addItemDecoration(itemDecoration)

        followViewModel.getUserDetailFollowFromAPI(username, 1)
        followViewModel.following.observe(viewLifecycleOwner, {
            showListUserFollowing(it)
        })

        followViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

    }

    fun setUsername(username: String) {
        this.username = username
    }

    private fun showListUserFollowing(userResponse: List<UserResponse>) {
        val listUserFollowers = ArrayList<UserResponse>()
        for (list in userResponse) {
            val temp = UserResponse(
                list.login,
                list.avatarUrl
            )
            listUserFollowers.add(temp)
        }

        val adapter = ListUserAdapter(listUserFollowers)
        binding?.rvUserFollowing?.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponse) {

                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USER, data.login)
                intent.putExtra(DetailActivity.EXTRA_AVATAR, data.avatarUrl)
                startActivity(intent)

            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar3?.visibility = View.VISIBLE
        } else {
            binding?.progressBar3?.visibility = View.GONE
        }
    }

}