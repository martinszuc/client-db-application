package com.martinszuc.clientsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.martinszuc.clientsapp.ui.component.login.LoginScreen
import com.martinszuc.clientsapp.ui.component.settings.SettingsViewModel
import com.martinszuc.clientsapp.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivityMain : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        // Apply the theme from DataStore
        lifecycleScope.launch {
            val themePreference = settingsViewModel.themePreference.first()
            setContent {
                AppTheme(darkTheme = themePreference == "dark" || (themePreference == "system_default" && isSystemInDarkTheme())) {
                    if (firebaseAuth.currentUser != null) {
                        MainScreen(initialThemePreference = themePreference)
                    } else {
                        LoginScreen(onLoginSuccess = {
                            setContent {
                                AppTheme(darkTheme = themePreference == "dark" || (themePreference == "system_default" && isSystemInDarkTheme())) {
                                    MainScreen(initialThemePreference = themePreference)
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}