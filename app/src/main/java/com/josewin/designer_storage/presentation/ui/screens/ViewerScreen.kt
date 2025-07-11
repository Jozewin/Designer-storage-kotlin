    package com.josewin.designer_storage.presentation.ui.screens

    import android.content.Context
    import android.content.Intent
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.PaddingValues
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.lazy.grid.GridCells
    import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
    import androidx.compose.foundation.lazy.grid.itemsIndexed
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Delete
    import androidx.compose.material.icons.filled.Share
    import androidx.compose.material3.ExperimentalMaterial3Api
    import androidx.compose.material3.FloatingActionButton
    import androidx.compose.material3.Icon
    import androidx.compose.material3.IconButton
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.SnackbarHost
    import androidx.compose.material3.SnackbarHostState
    import androidx.compose.material3.Text
    import androidx.compose.material3.TopAppBar
    import androidx.compose.material3.TopAppBarDefaults
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.remember
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.unit.dp
    import androidx.lifecycle.viewmodel.compose.viewModel
    import com.josewin.designer_storage.data.model.ImageItem
    import com.josewin.designer_storage.data.repository.ViewerRepository
    import com.josewin.designer_storage.presentation.ui.components.ImageGridItem
    import com.josewin.designer_storage.presentation.viewmodel.ViewerViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ViewerScreen(
        onImageClick: (Int) -> Unit,
        paddingValues: PaddingValues
    ) {
        val context = LocalContext.current
        val viewerRepository = remember { ViewerRepository(context) }
        val viewModel: ViewerViewModel = viewModel { ViewerViewModel(viewerRepository) }

        val uiState by viewModel.uiState.collectAsState()
        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(true) {
            viewModel.loadImages()
        }
        // Handle error messages
        LaunchedEffect(uiState.errorMessage) {
            uiState.errorMessage?.let { message ->
                snackbarHostState.showSnackbar(message)
                viewModel.clearError()
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = if (uiState.isSelectionMode) {
                                "${uiState.selectedImages.size} selected"
                            } else {
                                "Tailor Storage"
                            },
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        if (uiState.isSelectionMode) {
                            IconButton(
                                onClick = { shareImages(context, uiState.selectedImages) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Share"
                                )
                            }
                            IconButton(
                                onClick = { viewModel.deleteSelectedImages() }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete"
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            floatingActionButton = {
                if (uiState.isSelectionMode) {
                    FloatingActionButton(
                        onClick = { viewModel.clearSelection() }
                    ) {
                        Text("Cancel")
                    }
                }
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { paddingValue ->

            if (uiState.images.isEmpty() && !uiState.isLoading) {
                // Empty state
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValue.calculateBottomPadding(), bottom = paddingValues.calculateBottomPadding()),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "No photos yet",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Use the camera to take your first photo",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            } else {
                // Image grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(top = paddingValue.calculateTopPadding(), bottom = paddingValues.calculateBottomPadding()),

                ) {
                    itemsIndexed(uiState.images) { index, imageItem ->
                        ImageGridItem(
                            imageItem = imageItem,
                            isSelected = uiState.selectedImages.contains(imageItem),
                            isSelectionMode = uiState.isSelectionMode,
                            onClick = {
                                if (uiState.isSelectionMode) {
                                    viewModel.selectImage(imageItem)
                                } else {
                                    onImageClick(index)
                                }
                            },
                            onLongClick = {
                                viewModel.selectImage(imageItem)
                            }
                        )
                    }
                }
            }
        }
    }

    private fun shareImages(context: Context, images: List<ImageItem>) {
        if (images.isEmpty()) return

        if (images.size == 1) {
            // Share single image
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, images.first().uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(intent, "Share Image"))
        } else {
            // Share multiple images
            val intent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
                type = "image/*"
                putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(images.map { it.uri }))
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(intent, "Share Images"))
        }
    }