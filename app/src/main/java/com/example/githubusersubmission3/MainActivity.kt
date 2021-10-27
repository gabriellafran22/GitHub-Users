package com.example.githubusersubmission3

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersubmission3.adapter.ListUserAdapter
import com.example.githubusersubmission3.api.UserResponse
import com.example.githubusersubmission3.databinding.ActivityMainBinding
import com.example.githubusersubmission3.settings.*
import com.example.githubusersubmission3.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private var userNotFound: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.github_users)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
        binding.rvUsers.setHasFixedSize(true)


        mainViewModel.users.observe(this, { userResponse ->
            showListUsers(userResponse)
        })

        mainViewModel.isLoading.observe(this, {
            showLoading(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(search: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(search: String?): Boolean {
                mainViewModel.showSearchUsers(search)
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorites -> {
                val intent = Intent(this@MainActivity, FavoritesActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.menu_settings -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }

            else -> return true
        }
    }

    private fun showListUsers(userResponse: List<UserResponse>) {

        if (userResponse.isEmpty() && userNotFound == false) {
            Toast.makeText(this, R.string.user_not_found, Toast.LENGTH_SHORT).show()
            userNotFound = true
        } else {
            userNotFound = false
            val listUsers = ArrayList<UserResponse>()
            for (list in userResponse) {
                val temp = UserResponse(
                    list.login,
                    list.avatarUrl
                )
                listUsers.add(temp)
            }

            val adapter = ListUserAdapter(listUsers)
            binding.rvUsers.adapter = adapter

            adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: UserResponse) {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USER, data.login)
                    intent.putExtra(DetailActivity.EXTRA_AVATAR, data.avatarUrl)
                    startActivity(intent)
                }

            })
        }
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}