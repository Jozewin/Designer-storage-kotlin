package com.josewin.designer_storage.presentation.state

import com.josewin.designer_storage.data.model.ImageItem

data class ViewerUiState(
    val images: List<ImageItem> = emptyList(),
    val selectedImages: List<ImageItem> = emptyList(),
    val isLoading: Boolean = false,
    val isSelectionMode: Boolean = false,
    val errorMessage: String? = null
)