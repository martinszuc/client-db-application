package com.matos.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import androidx.preference.PreferenceManager
import com.matos.app.ui.MainScreen
import com.matos.app.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityMain : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applyThemeFromPreferences()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppTheme {
                MainScreen()
            }
        }
    }

    private fun applyThemeFromPreferences() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        when (sharedPreferences.getString("theme_preference", "system_default")) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "system_default" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}