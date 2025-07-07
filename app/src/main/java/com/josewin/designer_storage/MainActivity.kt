package com.josewin.designer_storage

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.josewin.designer_storage.navigation.AppNavigation
import com.josewin.designer_storage.presentation.ui.components.BottomNavigationBar
import com.josewin.designer_storage.presentation.ui.components.PermissionHandler
import com.josewin.designer_storage.presentation.ui.theme.DesignerStorageTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DesignerStorageTheme {
                var hasPermissions by remember { mutableStateOf(false) }

                PermissionHandler(
                    onPermissionsGranted = { hasPermissions = true }
                ) {
                    if (hasPermissions) {
                        val navController = rememberNavController()

                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            bottomBar = { BottomNavigationBar(navController) }
                        ) { paddingValues ->
                            AppNavigation(
                                navController = navController,
                                paddingValues = paddingValues
                            )
                        }

                     //   CameraScreen()

                    }
                }
            }
        }
    }
}