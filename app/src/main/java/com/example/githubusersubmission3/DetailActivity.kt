package com.example.githubusersubmission3

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubusersubmission3.adapter.SectionPagerAdapter
import com.example.githubusersubmission3.api.UserDetailResponse
import com.example.githubusersubmission3.database.Favorites
import com.example.githubusersubmission3.viewmodel.DetailViewModel
import com.example.githubusersubmission3.databinding.ActivityDetailBinding
import com.example.githubusersubmission3.viewmodel.FavoritesViewModel
import com.example.githubusersubmission3.viewmodel.FavoritesViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var login: String
    private lateinit var avatar: String
    private val detailViewModel by viewModels<DetailViewModel>()
    private val followersFragment = FollowersFragment()
    private val followingFragment = FollowingFragment()
    private var favorites: Favorites = Favorites()

    private var isFavorited = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.github_user_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        login = intent.getStringExtra(EXTRA_USER).toString()
        avatar = intent.getStringExtra(EXTRA_AVATAR).toString()

        setTabLayout()

        detailViewModel.showUserDetailDataFromAPI(login)
        detailViewModel.userDetail.observe(this, {
            showUserDetailData(it)
        })

        val favoritesViewModel = obtainViewModel(this@DetailActivity)
        favoritesViewModel.getAllFavoritedUsers().observe(this, { favs ->
            favs.forEach() {
                if (it.username == login) {
                    isFavorited = true
                    showFavorited()
                }
            }
        })

        binding.btnDetailFavorite.setOnClickListener {
            if (isFavorited) {
                isFavorited = false
                showFavorited()

                favorites.let {
                    it.username = login
                }
                Toast.makeText(
                    this,
                    resources.getString(R.string.delete_favorite_success, favorites.username),
                    Toast.LENGTH_SHORT
                ).show()
                favoritesViewModel.delete(favorites)
            } else {
                isFavorited = true
                showFavorited()

                favorites.let {
                    it.username = login
                    it.avatar_url = avatar
                }
                Toast.makeText(
                    this,
                    resources.getString(R.string.add_favorite_success, favorites.username),
                    Toast.LENGTH_SHORT
                ).show()
                favoritesViewModel.insert(favorites)
            }
        }
    }

    private fun showFavorited() {
        if (isFavorited) {
            binding.btnDetailFavorite.setImageResource(R.drawable.ic_baseline_favorite_24_black)
        } else {
            binding.btnDetailFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24_black)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoritesViewModel {
        val factory = FavoritesViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoritesViewModel::class.java)
    }

    private fun setTabLayout() {
        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.setFragment(followersFragment, followingFragment)

        followersFragment.setUsername(login)
        followingFragment.setUsername(login)

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showUserDetailData(data: UserDetailResponse) {

        Glide.with(this)
            .load(data.avatarUrl)
            .into(binding.imgDetailProfilePicture)
        with(binding) {
            tvDetailUsername.text = resources.getString(R.string.username, data.login)
            tvDetailName.text = data.name
            tvDetailRepositoriesNumber.text = data.publicRepositories.toString()
        }

        if (data.company == null) {
            binding.tvDetailCompany.visibility = View.GONE
            binding.tvDetailCompanyName.visibility = View.GONE
        } else {
            binding.tvDetailCompanyName.text = data.company
        }

        if (data.location == null) {
            binding.tvDetailLocation.visibility = View.GONE
            binding.tvDetailLocationName.visibility = View.GONE
        } else {
            binding.tvDetailLocationName.text = data.location
        }


        binding.btnDetailCheckoutGitHubAccount.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.htmlUrl))
            startActivity(intent)
        }

    }

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_AVATAR = "extra_avatar"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}