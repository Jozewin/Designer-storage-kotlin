package com.josewin.designer_storage.presentation.state

import android.net.Uri
import java.io.File

data class CameraUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val captureSuccess: Boolean = false,
    val lastCapturedImageUri: Uri? = null,
    val lastCapturedImageFile: File? = null
)