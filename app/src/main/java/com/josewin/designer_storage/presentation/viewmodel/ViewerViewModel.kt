package com.josewin.designer_storage.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josewin.designer_storage.data.model.ImageItem
import com.josewin.designer_storage.data.repository.ViewerRepository
import com.josewin.designer_storage.presentation.state.ViewerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewerViewModel(
    private val viewerRepository: ViewerRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ViewerUiState())
    val uiState: StateFlow<ViewerUiState> = _uiState.asStateFlow()
    
    init {
        loadImages()
    }
    
    fun loadImages() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        
        viewModelScope.launch {
            try {
                val images = viewerRepository.getAllImages()
                _uiState.value = _uiState.value.copy(
                    images = images,
                    isLoading = false,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to load images"
                )
            }
        }
    }
    
    fun refreshImages() {
        viewModelScope.launch {
            try {
                val images = viewerRepository.refreshImages()
                _uiState.value = _uiState.value.copy(
                    images = images,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message ?: "Failed to refresh images"
                )
            }
        }
    }
    
    fun selectImage(imageItem: ImageItem) {
        val currentSelection = _uiState.value.selectedImages.toMutableList()
        if (currentSelection.contains(imageItem)) {
            currentSelection.remove(imageItem)
        } else {
            currentSelection.add(imageItem)
        }
        
        _uiState.value = _uiState.value.copy(
            selectedImages = currentSelection,
            isSelectionMode = currentSelection.isNotEmpty()
        )
    }
    
    fun clearSelection() {
        _uiState.value = _uiState.value.copy(
            selectedImages = emptyList(),
            isSelectionMode = false
        )
    }
    
    fun deleteSelectedImages() {
        val selectedImages = _uiState.value.selectedImages
        if (selectedImages.isEmpty()) return
        
        viewModelScope.launch {
            try {
                selectedImages.forEach { imageItem ->
                    viewerRepository.deleteImage(imageItem)
                }
                clearSelection()
                loadImages() // Refresh the list
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message ?: "Failed to delete images"
                )
            }
        }
    }

    fun deleteSingleImage(imageItem: ImageItem) {
        viewModelScope.launch {
            try {
                viewerRepository.deleteImage(imageItem)

                // Remove from selection if it was selected
                val currentSelection = _uiState.value.selectedImages.toMutableList()
                if (currentSelection.contains(imageItem)) {
                    currentSelection.remove(imageItem)
                    _uiState.value = _uiState.value.copy(
                        selectedImages = currentSelection,
                        isSelectionMode = currentSelection.isNotEmpty()
                    )
                }

                // Refresh the images list
                loadImages()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message ?: "Failed to delete image"
                )
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

