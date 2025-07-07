package com.josewin.designer_storage.presentation.viewmodel

import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josewin.designer_storage.data.repository.CameraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CameraViewModel(
    private val cameraRepository: CameraRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CameraUiState())
    val uiState: StateFlow<CameraUiState> = _uiState.asStateFlow()

    fun captureImage(imageCapture: ImageCapture) {
        if (_uiState.value.isCapturing) return

        _uiState.value = _uiState.value.copy(isCapturing = true)

        viewModelScope.launch {
            cameraRepository.captureImage(imageCapture)
                .onSuccess { uri ->
                    _uiState.value = _uiState.value.copy(
                        isCapturing = false,
                        lastCapturedImage = uri,
                        showSuccessMessage = true
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isCapturing = false,
                        errorMessage = exception.message
                    )
                }
        }
    }

    fun clearSuccessMessage() {
        _uiState.value = _uiState.value.copy(showSuccessMessage = false)
    }

    fun clearErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun toggleFlash() {
        _uiState.value = _uiState.value.copy(
            flashEnabled = !_uiState.value.flashEnabled
        )
    }

    fun switchCamera() {
        _uiState.value = _uiState.value.copy(
            isBackCamera = !_uiState.value.isBackCamera
        )
    }
}

data class CameraUiState(
    val isCapturing: Boolean = false,
    val lastCapturedImage: Uri? = null,
    val showSuccessMessage: Boolean = false,
    val errorMessage: String? = null,
    val flashEnabled: Boolean = false,
    val isBackCamera: Boolean = true
)