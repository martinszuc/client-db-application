package com.martinszuc.clientsapp.data.remote

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject

class FirebaseStorageRepository @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
    private val firebaseAuth: FirebaseAuth // Inject FirebaseAuth to get current user
) {
    // Get the current user's UID
    private fun getCurrentUserUid(): String? {
        return firebaseAuth.currentUser?.uid
    }

    suspend fun uploadPhotoWithDirectory(uri: Uri, fileName: String, directory: String): String? {
        val uid = firebaseAuth.currentUser?.uid ?: return null  // Ensure user is authenticated

        return try {
            // Create the directory path in Firebase Storage
            val fileRef = firebaseStorage.reference.child("$uid/$directory/$fileName")

            // Upload the file and get the download URL
            fileRef.putFile(uri).await()
            fileRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null  // Return null if upload failed
        }
    }
    // New method to upload database backup
    suspend fun uploadDatabaseBackup(databaseFile: File): String? {
        val uid = getCurrentUserUid() ?: return null
        val timestamp = System.currentTimeMillis()
        val fileName = "backup_$timestamp.db"  // Add timestamp to filename for unique backups
        val fileUri = Uri.fromFile(databaseFile)

        return try {
            val fileRef = firebaseStorage.reference.child("$uid/database_backups/$fileName")
            fileRef.putFile(fileUri).await()
            fileRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null  // Return null if the backup upload fails
        }
    }

    // Reference to Firebase Storage's root path
    private val storageRef: StorageReference = firebaseStorage.reference
}
