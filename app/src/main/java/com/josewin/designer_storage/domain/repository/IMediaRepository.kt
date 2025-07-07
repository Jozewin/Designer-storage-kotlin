package com.josewin.designer_storage.domain.repository

import android.net.Uri
import com.josewin.designer_storage.domain.model.MediaItem
import kotlinx.coroutines.flow.Flow

interface IMediaRepository {
    suspend fun getMediaImages() : Flow<List<MediaItem>>
    suspend fun captureImage(): Uri?
}