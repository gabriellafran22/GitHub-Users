package com.example.githubusersubmission3.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubusersubmission3.database.Favorites
import com.example.githubusersubmission3.repository.FavoritesRepository

class FavoritesViewModel(application: Application) : ViewModel() {

    private val mFavoritesRepository: FavoritesRepository = FavoritesRepository(application)

    fun getAllFavoritedUsers(): LiveData<List<Favorites>> =
        mFavoritesRepository.getAllFavoritedUsers()

    fun insert(favorites: Favorites) {
        mFavoritesRepository.insert(favorites)
    }

    fun delete(favorites: Favorites) {
        mFavoritesRepository.delete(favorites)
    }
}

