package com.josewin.designer_storage.presentation.ui.screens

import androidx.camera.core.ImageCapture
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.josewin.designer_storage.data.repository.CameraRepository
import com.josewin.designer_storage.presentation.ui.components.CameraControls
import com.josewin.designer_storage.presentation.ui.components.CameraPreview
import com.josewin.designer_storage.presentation.viewmodel.CameraViewModel

@Composable
fun CameraScreen(paddingValues : PaddingValues) {
    val context = LocalContext.current
    val cameraRepository = remember { CameraRepository(context) }
    val viewModel: CameraViewModel = viewModel { CameraViewModel(cameraRepository) }

    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }

    // Handle success message
    LaunchedEffect(uiState.showSuccessMessage) {
        if (uiState.showSuccessMessage) {
            snackbarHostState.showSnackbar("Photo saved successfully!")
            viewModel.clearSuccessMessage()
        }
    }

    // Handle error message
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar("Error: $message")
            viewModel.clearErrorMessage()
        }
    }

    Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Camera Preview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                CameraPreview(
                    modifier = Modifier.fillMaxSize(),
                    isBackCamera = uiState.isBackCamera,
                    flashEnabled = uiState.flashEnabled,
                    onImageCaptureReady = { capture ->
                        imageCapture = capture
                    }
                )
            }

            // Camera Controls
            CameraControls(
                flashEnabled = uiState.flashEnabled,
                isCapturing = uiState.isCapturing,
                onCaptureClick = {
                    imageCapture?.let { capture ->
                        viewModel.captureImage(capture)
                    }
                },
                onFlashToggle = { viewModel.toggleFlash() },
                onSwitchCamera = { viewModel.switchCamera() }
            )
        }

        // Snackbar Host
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) { data ->
            Snackbar(
                snackbarData = data,
                containerColor = if (data.visuals.message.contains("Error")) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.primary
                }
            )
        }
    }
}