package com.example.githubusersubmission3

import android.content.Intent
import android.os.Bundle
import android.view.ViewPropertyAnimator
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.githubusersubmission3.databinding.ActivitySplashScreenBinding
import com.example.githubusersubmission3.settings.SettingPreferences
import com.example.githubusersubmission3.settings.SettingsViewModel
import com.example.githubusersubmission3.settings.SettingsViewModelFactory
import com.example.githubusersubmission3.settings.dataStore

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val alphaFirst = 0f
    private val alphaSecond = 1f
    private val splashScreenDuration: Long = 500
    private var propertyAnim: ViewPropertyAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.ssGit.alpha = alphaFirst

        val pref = SettingPreferences.getInstance(dataStore)
        val settingsViewModel = ViewModelProvider(this, SettingsViewModelFactory(pref)).get(
            SettingsViewModel::class.java
        )

        propertyAnim = binding.ssGit.animate().setDuration(splashScreenDuration).alpha(alphaSecond)
            .withEndAction {
                Intent(this, MainActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }

        settingsViewModel.getThemeSettings().observe(this, { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            propertyAnim?.start()
        })
    }

    override fun onDestroy() {
        propertyAnim?.cancel()
        super.onDestroy()
    }
}