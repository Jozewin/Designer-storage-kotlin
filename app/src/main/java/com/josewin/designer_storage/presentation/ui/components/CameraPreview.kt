package com.josewin.designer_storage.presentation.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    isBackCamera: Boolean = true,
    flashEnabled: Boolean = false,
    onImageCaptureReady: (ImageCapture) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val imageCapture = remember {
        ImageCapture.Builder()
            .setFlashMode(if (flashEnabled) ImageCapture.FLASH_MODE_ON else ImageCapture.FLASH_MODE_OFF)
            .build()
    }

    LaunchedEffect(flashEnabled) {
        imageCapture.flashMode = if (flashEnabled) ImageCapture.FLASH_MODE_ON else ImageCapture.FLASH_MODE_OFF
    }

    LaunchedEffect(imageCapture) {
        onImageCaptureReady(imageCapture)
    }

    val previewView = remember {
        PreviewView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            scaleType = PreviewView.ScaleType.FILL_CENTER
        }
    }

    // Focus indicator state
    var focusPoint by remember { mutableStateOf<Offset?>(null) }

    LaunchedEffect(isBackCamera) {
        val cameraProvider = context.getCameraProvider()

        val cameraSelector = if (isBackCamera) {
            CameraSelector.DEFAULT_BACK_CAMERA
        } else {
            CameraSelector.DEFAULT_FRONT_CAMERA
        }

        val preview = Preview.Builder().build()
        preview.setSurfaceProvider(previewView.surfaceProvider)

        try {
            val camera = cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )

            setupTapToFocus(previewView, camera.cameraControl, camera.cameraInfo) { offset ->
                focusPoint = offset
            }

        } catch (exc: Exception) {
            // Handle binding error
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            val cameraProvider = ProcessCameraProvider.getInstance(context)
            cameraProvider.get().unbindAll()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )

        focusPoint?.let { point ->
            FocusIndicator(
                position = point,
                onAnimationEnd = { focusPoint = null }
            )
        }
    }
}

@SuppressLint("ClickableViewAccessibility")
private fun setupTapToFocus(
    previewView: PreviewView,
    cameraControl: CameraControl,
    cameraInfo: CameraInfo,
    onFocusTap: (Offset) -> Unit
) {
    previewView.setOnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            val factory = previewView.meteringPointFactory
            val point = factory.createPoint(event.x, event.y)

            val action = FocusMeteringAction.Builder(
                point,
                FocusMeteringAction.FLAG_AF or FocusMeteringAction.FLAG_AE
            ).setAutoCancelDuration(3, java.util.concurrent.TimeUnit.SECONDS)
                .build()

            cameraControl.startFocusAndMetering(action)

            // Notify Compose UI to show focus ring
            onFocusTap(Offset(event.x, event.y))
        }
        true
    }
}

@Composable
fun FocusIndicator(
    position: Offset,
    onAnimationEnd: () -> Unit,
    color: Color = Color.White,
    size: Dp = 80.dp
) {
    val scaleAnim = remember { Animatable(1f) }
    val alphaAnim = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        scaleAnim.animateTo(
            1.5f,
            animationSpec = tween(durationMillis = 400)
        )
        alphaAnim.animateTo(
            0f,
            animationSpec = tween(durationMillis = 400)
        )
        onAnimationEnd()
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        drawCircle(
            color = color.copy(alpha = alphaAnim.value),
            radius = (size.toPx() / 2) * scaleAnim.value,
            center = position
        )
    }
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }
