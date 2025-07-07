package com.josewin.designer_storage.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.josewin.designer_storage.R

@Composable
fun CameraControls(
    modifier: Modifier = Modifier,
    flashEnabled: Boolean = false,
    isCapturing: Boolean = false,
    onCaptureClick: () -> Unit,
    onFlashToggle: () -> Unit,
    onSwitchCamera: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(NavigationBarDefaults.containerColor)
            .padding(24.dp)
    ) {
        // Flash Toggle on left
        IconButton(
            onClick = onFlashToggle,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.5f))
        ) {
            Icon(
                imageVector = if (flashEnabled) ImageVector.vectorResource(R.drawable.flash_on)
                else ImageVector.vectorResource(R.drawable.flash_off),
                contentDescription = if (flashEnabled) "Flash On" else "Flash Off",
                tint = if (flashEnabled) Color.Yellow else Color.White
            )
        }

        // Capture Button in center
        FloatingActionButton(
            onClick = onCaptureClick,
            modifier = Modifier
                .align(Alignment.Center)
                .size(72.dp),
            containerColor = if (isCapturing) Color.Gray else Color.White,
            contentColor = Color.Black
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.add_a_photo),
                contentDescription = "Capture",
                modifier = Modifier.size(32.dp)
            )
        }


    }
}
