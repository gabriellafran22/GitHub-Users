package com.example.githubusersubmission3.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorites: Favorites)

    @Query("DELETE FROM favorites WHERE username = :username")
    open fun deleteByUsername(username: String?): Int

    @Query("SELECT * from favorites ORDER BY id ASC")
    fun getAllFavoritedUsers(): LiveData<List<Favorites>>

}