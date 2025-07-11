package com.josewin.designer_storage.data.repository

import android.content.Context
import com.josewin.designer_storage.data.model.ImageItem
import com.josewin.designer_storage.util.FileUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ViewerRepository(private val context: Context) {
    
    suspend fun getAllImages(): List<ImageItem> {
        return withContext(Dispatchers.IO) {
            FileUtils.getAllImagesFromBlouseFolder(context)
        }
    }
    
    suspend fun refreshImages(): List<ImageItem> {
        return getAllImages()
    }
    
    suspend fun deleteImage(imageItem: ImageItem): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                imageItem.file.delete()
            } catch (e: Exception) {
                false
            }
        }
    }
}