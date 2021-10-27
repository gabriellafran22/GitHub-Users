package com.example.githubusersubmission3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersubmission3.adapter.ListUserAdapter
import com.example.githubusersubmission3.api.UserResponse
import com.example.githubusersubmission3.database.Favorites
import com.example.githubusersubmission3.databinding.ActivityFavoritesBinding
import com.example.githubusersubmission3.viewmodel.FavoritesViewModel
import com.example.githubusersubmission3.viewmodel.FavoritesViewModelFactory

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.favorite_users)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavoriteUsers.addItemDecoration(itemDecoration)

        binding.rvFavoriteUsers.setHasFixedSize(true)

        val favoritesViewModel = obtainViewModel(this@FavoritesActivity)
        favoritesViewModel.getAllFavoritedUsers().observe(this, { favUsers ->
            if (favUsers.isNotEmpty()) {
                setCallbackAndUserData(favUsers)
            } else {
                Toast.makeText(this, getString(R.string.no_favorite_users), Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }

    private fun setCallbackAndUserData(favUsers: List<Favorites>) {
        var data = Utils.DatabaseToAPI(favUsers)
        val adapter = ListUserAdapter(data)
        binding.rvFavoriteUsers.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponse) {
                val intent = Intent(this@FavoritesActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USER, data.login)
                intent.putExtra(DetailActivity.EXTRA_AVATAR, data.avatarUrl)
                startActivity(intent)
            }

        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoritesViewModel {
        val factory = FavoritesViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoritesViewModel::class.java)
    }
}