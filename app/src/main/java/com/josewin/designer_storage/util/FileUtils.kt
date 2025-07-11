package com.josewin.designer_storage.util

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import com.josewin.designer_storage.data.model.ImageItem
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FileUtils {

    const val BLOUSE_FOLDER = "Tailor"

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
        val imageFileName = "Tailor_${timeStamp}.jpg"
        val blouseDir = getBlouseDirectory(context)

        return File(blouseDir, imageFileName)
    }

    fun getAllImagesFromBlouseFolder(context: Context): List<ImageItem> {
        val blouseDir = getBlouseDirectory(context)

        return blouseDir.listFiles { file ->
            file.isFile && file.extension.lowercase() in listOf("jpg", "jpeg", "png")
        }?.map { file ->
            ImageItem(
                file = file,
                uri = getUriForFile(context, file),
                name = file.name,
                dateModified = file.lastModified(),
                size = file.length()
            )
        }?.sortedByDescending { it.dateModified } ?: emptyList()
    }

    fun getImageCount(context: Context): Int {
        return getAllImagesFromBlouseFolder(context).size
    }

    private fun getUriForFile(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
    }
}