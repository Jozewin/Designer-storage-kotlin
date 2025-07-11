package com.josewin.designer_storage.navigation

import android.content.Intent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.josewin.designer_storage.data.repository.ViewerRepository
import com.josewin.designer_storage.presentation.ui.screens.CameraScreen
import com.josewin.designer_storage.presentation.ui.screens.PhotoPreviewScreen
import com.josewin.designer_storage.presentation.ui.screens.ViewerScreen
import com.josewin.designer_storage.presentation.viewmodel.ViewerViewModel

@Composable
fun AppNavigation(navController: NavHostController, paddingValues: PaddingValues) {


    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding->

    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = Screen.Camera.route
    ) {
        composable(Screen.Camera.route) {
            CameraScreen(paddingValues = paddingValues)
        }
        composable(Screen.Viewer.route) {
            ViewerScreen(
                onImageClick = { imageIndex ->
                    navController.navigate(Screen.PhotoPreview.createRoute(imageIndex))
                },
                paddingValues = paddingValues
            )
        }

        composable(
            route = Screen.PhotoPreview.route,
            arguments = listOf(
                navArgument("imageIndex") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val imageIndex = backStackEntry.arguments?.getInt("imageIndex") ?: 0

            val viewerRepository = remember { ViewerRepository(context) }
            val viewerViewModel: ViewerViewModel = viewModel { ViewerViewModel(viewerRepository) }
            val uiState by viewerViewModel.uiState.collectAsState()

            if (imageIndex < uiState.images.size) {
                val imageItem = uiState.images[imageIndex]

                PhotoPreviewScreen(
                    imageItem = imageItem,
                    onBackClick = { navController.popBackStack() },
                    onShareClick = { image ->
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "image/*"
                            putExtra(Intent.EXTRA_STREAM, image.uri)
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        context.startActivity(Intent.createChooser(intent, "Share Image"))
                    }
                )
            }
    }}
}}