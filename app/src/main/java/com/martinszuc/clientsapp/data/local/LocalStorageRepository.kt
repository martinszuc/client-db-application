package com.martinszuc.clientsapp.data.local

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * Project: database application
 *
 * Author: Bc. Martin Szuc (matoszuc@gmail.com)
 * GitHub: https://github.com/martinszuc
 *
 *
 * License:
 * This code is licensed under MIT License. You may not use this file except
 * in compliance with the License.
 */

class LocalStorageRepository(private val context: Context) {

    // Save the photo to internal storage under the specified directory
    fun savePhotoToInternalStorage(uri: Uri, fileName: String, directory: String): File? {
        return try {
            // Create or get the directory
            val storageDir = File(context.filesDir, directory)
            if (!storageDir.exists()) {
                storageDir.mkdirs()  // Create directory if it doesn't exist
            }

            // Create the file in the specified directory
            val photoFile = File(storageDir, fileName)

            // Copy the file from the URI to internal storage
            val inputStream = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(photoFile)

            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()

            photoFile
        } catch (e: Exception) {
            e.printStackTrace()
            null  // Return null if there was an error
        }
    }

    // Function to get a file from internal storage
    fun getFileFromInternalStorage(fileName: String): File? {
        val file = File(context.filesDir, fileName)
        return if (file.exists()) file else null
    }
}
