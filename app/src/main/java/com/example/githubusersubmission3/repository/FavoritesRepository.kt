package com.example.githubusersubmission3.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubusersubmission3.database.Favorites
import com.example.githubusersubmission3.database.FavoritesDao
import com.example.githubusersubmission3.database.FavoritesRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoritesRepository(application: Application) {

    private val mFavoritesDao: FavoritesDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoritesRoomDatabase.getDatabase(application)
        mFavoritesDao = db.favoritesDao()
    }

    fun getAllFavoritedUsers(): LiveData<List<Favorites>> = mFavoritesDao.getAllFavoritedUsers()

    fun insert(favorites: Favorites) {
        executorService.execute { mFavoritesDao.insert(favorites) }
    }

    fun delete(favorites: Favorites) {
        executorService.execute { mFavoritesDao.deleteByUsername(favorites.username) }
    }

}