package com.martinszuc.clientsapp.data.remote

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
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

    // Upload multiple photos for the authenticated user
    fun uploadMultiplePhotos(
        uris: List<Uri>,
        onAllUploadsSuccess: (List<String>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val uid = getCurrentUserUid()
        if (uid == null) {
            onFailure(Exception("User not authenticated"))
            return
        }

        val downloadUrls = mutableListOf<String>()
        var completedUploads = 0

        uris.forEach { uri ->
            val fileRef = storageRef.child("$uid/service_photos/${System.currentTimeMillis()}_${uri.lastPathSegment}")
            val uploadTask = fileRef.putFile(uri)

            uploadTask.addOnSuccessListener {
                fileRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    downloadUrls.add(downloadUri.toString())
                    completedUploads++

                    if (completedUploads == uris.size) {
                        onAllUploadsSuccess(downloadUrls)
                    }
                }
            }.addOnFailureListener { exception ->
                onFailure(exception)
            }
        }
    }

    // Reference to Firebase Storage's root path
    private val storageRef: StorageReference = firebaseStorage.reference
}
