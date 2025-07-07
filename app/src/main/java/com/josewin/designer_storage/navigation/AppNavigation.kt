package com.josewin.designer_storage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.josewin.designer_storage.presentation.ui.screens.CameraScreen
import com.josewin.designer_storage.presentation.ui.screens.ViewerScreen

@Composable
fun AppNavigation(navController: NavHostController, paddingValues: PaddingValues) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding->


    NavHost(
        navController = navController,
        startDestination = Screen.Camera.route
    ) {
        composable(Screen.Camera.route) {
            CameraScreen(paddingValues = paddingValues)
        }
        composable(Screen.Viewer.route) {
            ViewerScreen()
        }
    }}
}