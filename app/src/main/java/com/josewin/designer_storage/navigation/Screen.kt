package com.josewin.designer_storage.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Phone
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.josewin.designer_storage.R

sealed class Screen(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Camera : Screen(
        route = "camera",
        title = "Camera",
        icon = R.drawable.camera
    )
    
    object Viewer : Screen(
        route = "viewer",
        title = "Viewer",
        icon = R.drawable.photo_frame
    )
    object PhotoPreview : Screen(
        route = "photo_preview/{imageIndex}",
        title = "Photo Preview",
        icon = -1
    ) {
        fun createRoute(imageIndex: Int) = "photo_preview/$imageIndex"
    }
}