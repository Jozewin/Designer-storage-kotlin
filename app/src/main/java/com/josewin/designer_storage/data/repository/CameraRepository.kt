package com.josewin.designer_storage.data.repository

import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import com.josewin.designer_storage.util.FileUtils
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import kotlin.coroutines.resume

class CameraRepository(private val context: Context) {

    suspend fun captureImage(imageCapture: ImageCapture): Result<Uri> {
        return suspendCancellableCoroutine { continuation ->
            val outputFile = FileUtils.createImageFile(context)
            val outputOptions = ImageCapture.OutputFileOptions.Builder(outputFile).build()

            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                        continuation.resume(Result.success(Uri.fromFile(outputFile)))
                    }

                    override fun onError(exception: ImageCaptureException) {
                        continuation.resume(Result.failure(exception))
                    }
                }
            )
        }
    }

    fun getAllImages(): List<File> {
        return FileUtils.getAllImagesFromBlouseFolder(context)
    }
}