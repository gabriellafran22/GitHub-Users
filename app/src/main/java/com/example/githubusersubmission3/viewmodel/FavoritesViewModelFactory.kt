package com.example.githubusersubmission3.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class FavoritesViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }


    companion object {
        @Volatile
        private var INSTANCE: FavoritesViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavoritesViewModelFactory {
            if (INSTANCE == null) {
                synchronized(FavoritesViewModelFactory::class.java) {
                    INSTANCE = FavoritesViewModelFactory(application)
                }
            }
            return INSTANCE as FavoritesViewModelFactory
        }
    }

}