package com.josewin.designer_storage.util

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FileUtils {

    const val BLOUSE_FOLDER = "Blouse"

    fun getBlouseDirectory(context: Context): File {
        val picturesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val blouseDir = File(picturesDir, BLOUSE_FOLDER)

        if (!blouseDir.exists()) {
            blouseDir.mkdirs()
        }

        return blouseDir
    }

    fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "BLOUSE_${timeStamp}.jpg"
        val blouseDir = getBlouseDirectory(context)

        return File(blouseDir, imageFileName)
    }

    fun getAllImagesFromBlouseFolder(context: Context): List<File> {
        val blouseDir = getBlouseDirectory(context)

        return blouseDir.listFiles { file ->
            file.isFile && file.extension.lowercase() in listOf("jpg", "jpeg", "png")
        }?.sortedByDescending { it.lastModified() } ?: emptyList()
    }

    fun getImageCount(context: Context): Int {
        return getAllImagesFromBlouseFolder(context).size
    }
}