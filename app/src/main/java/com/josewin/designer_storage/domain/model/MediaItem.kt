package com.josewin.designer_storage.domain.model

import java.net.URI

data class MediaItem(
    val id : Long,
    val uri : URI,
    val name : String,
    val dateAdded: Long,
    val size: Long,
    val mimeType: String
)