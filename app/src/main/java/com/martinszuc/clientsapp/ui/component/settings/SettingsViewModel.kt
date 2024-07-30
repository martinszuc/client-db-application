package com.martinszuc.clientsapp.ui.component.settings

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.martinszuc.clientsapp.util.AppConstants
import com.martinszuc.clientsapp.util.AppConstants.THEME_DARK
import com.martinszuc.clientsapp.util.AppConstants.THEME_LIGHT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    application: Application // Use Application instead of Context
) : AndroidViewModel(application) {

    private val THEMEKEY = stringPreferencesKey(AppConstants.THEME_PREFERENCE)

    val themePreference: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[THEMEKEY] ?: AppConstants.THEME_SYSTEM_DEFAULT
        }

    fun changeTheme(theme: String) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[THEMEKEY] = theme
            }
            Log.d("SettingsViewModel", "Theme changed to: $theme")
            applyTheme(theme)
        }
    }

    // In your ViewModel
    private val _backupStatus = MutableStateFlow<String?>(null)
    val backupStatus: StateFlow<String?> get() = _backupStatus

    fun backupDatabase() {
        val dbName = "kozmetika_database"
        val currentDBPath = getApplication<Application>().getDatabasePath(dbName).absolutePath
        val backupDir = getApplication<Application>().filesDir // Internal storage
        val backupFile = File(backupDir, "$dbName-backup.db")

        try {
            FileInputStream(currentDBPath).use { src ->
                FileOutputStream(backupFile).use { dst ->
                    src.channel.transferTo(0, src.channel.size(), dst.channel)
                }
            }
            Log.d("SettingsViewModel", "Database backed up successfully to ${backupFile.absolutePath}")
            Toast.makeText(getApplication(), "Databáza zálohovaná úspešne", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            Log.e("SettingsViewModel", "Error backing up database", e)
            Toast.makeText(getApplication(), "Chyba pri zálohovaní", Toast.LENGTH_LONG).show()
        }
    }

    private fun applyTheme(theme: String) {
        when (theme) {
            THEME_LIGHT -> {
                Log.d("SettingsViewModel", "Applying Light Theme")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            THEME_DARK -> {
                Log.d("SettingsViewModel", "Applying Dark Theme")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            AppConstants.THEME_SYSTEM_DEFAULT -> {
                Log.d("SettingsViewModel", "Applying System Default Theme")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }
}
