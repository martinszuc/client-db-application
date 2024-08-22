package com.martinszuc.clientsapp.data.remote

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import javax.inject.Inject

class FirebaseStorageRepository @Inject constructor(
    private val firebaseStorage: FirebaseStorage  // Injected via Hilt
) {
    private val storageRef: StorageReference = firebaseStorage.reference.child("service_photos")

    fun uploadPhoto(uri: Uri, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        val fileRef = storageRef.child("${System.currentTimeMillis()}.jpg")
        val uploadTask: UploadTask = fileRef.putFile(uri)

        uploadTask.addOnSuccessListener {
            fileRef.downloadUrl.addOnSuccessListener { downloadUri ->
                onSuccess(downloadUri.toString())  // Get the download URL
            }
        }.addOnFailureListener { exception ->
            onFailure(exception)
        }
    }

    fun uploadMultiplePhotos(
        uris: List<Uri>,
        onAllUploadsSuccess: (List<String>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val downloadUrls = mutableListOf<String>()
        var completedUploads = 0

        uris.forEach { uri ->
            val fileRef = storageRef.child("${System.currentTimeMillis()}_${uri.lastPathSegment}")
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
}
