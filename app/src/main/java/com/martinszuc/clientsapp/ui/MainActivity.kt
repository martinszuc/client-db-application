package com.martinszuc.clientsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.martinszuc.clientsapp.ui.component.login.LoginScreen
import com.martinszuc.clientsapp.ui.component.settings.SettingsViewModel
import com.martinszuc.clientsapp.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        // Observe the theme preference and apply the theme immediately
        lifecycleScope.launch {
            settingsViewModel.themePreference.collect { themePreference ->
                applyTheme(themePreference)
                setContent {
                    AppTheme(darkTheme = themePreference == "dark" || (themePreference == "system_default" && isSystemInDarkTheme())) {
                        if (firebaseAuth.currentUser != null) {
                            MainScreen()
                        } else {
                            LoginScreen(onLoginSuccess = {
                                setContent {
                                    AppTheme(darkTheme = themePreference == "dark" || (themePreference == "system_default" && isSystemInDarkTheme())) {
                                        MainScreen()
                                    }
                                }
                            })
                        }
                    }
                }
            }
        }
    }

    private fun applyTheme(themePreference: String) {
        when (themePreference) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "system_default" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}