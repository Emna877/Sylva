package com.example.sylva.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sylva.R
import com.example.sylva.ui.model.TreeProfile
import com.example.sylva.ui.navigation.SylvaDestination
import com.example.sylva.ui.screens.CameraScreen
import com.example.sylva.ui.screens.GalleryUploadScreen
import com.example.sylva.ui.screens.HistoryScreen
import com.example.sylva.ui.screens.HomeScreen
import com.example.sylva.ui.screens.LoadingAnalysisScreen
import com.example.sylva.ui.screens.SplashOnboardingScreen
import com.example.sylva.ui.screens.TreeDetailsScreen
import com.example.sylva.viewmodel.TreeAnalysisUiState
import com.example.sylva.viewmodel.TreeAnalysisViewModel
import java.io.ByteArrayOutputStream

@Composable
fun SylvaApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    analysisViewModel: TreeAnalysisViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by analysisViewModel.uiState.collectAsState()

    var currentTree by remember { mutableStateOf<TreeProfile?>(null) }
    var currentErrorMessage by remember { mutableStateOf<String?>(null) }
    val savedTrees = remember { mutableStateListOf<TreeProfile>() }
    var selectedImageBytes by remember { mutableStateOf<ByteArray?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        val imageBytes = bitmap?.toJpegBytes()
        if (imageBytes != null) {
            selectedImageBytes = imageBytes
            analysisViewModel.analyzeImage(imageBytes)
            navController.navigate(SylvaDestination.Loading.route)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            context.contentResolver.openInputStream(uri)?.use { stream ->
                selectedImageBytes = stream.readBytes()
            }
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is TreeAnalysisUiState.Success) {
            currentTree = (uiState as TreeAnalysisUiState.Success).profile
            currentErrorMessage = null
        } else if (uiState is TreeAnalysisUiState.Error) {
            currentErrorMessage = (uiState as TreeAnalysisUiState.Error).message
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = SylvaDestination.Splash.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(SylvaDestination.Splash.route) {
                SplashOnboardingScreen(
                    onContinue = {
                        navController.navigate(SylvaDestination.Home.route) {
                            popUpTo(SylvaDestination.Splash.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(SylvaDestination.Home.route) {
                HomeScreen(
                    latestTree = currentTree,
                    onScanClick = { navController.navigate(SylvaDestination.Camera.route) },
                    onUploadClick = { navController.navigate(SylvaDestination.Gallery.route) },
                    onHistoryClick = { navController.navigate(SylvaDestination.History.route) },
                    onRetry = {
                        analysisViewModel.analyzeImage(loadLeafBytes(context))
                        navController.navigate(SylvaDestination.Loading.route)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(SylvaDestination.Camera.route) {
                CameraScreen(
                    onCapture = { cameraLauncher.launch(null) },
                    onBack = { navController.popBackStack() },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(SylvaDestination.Gallery.route) {
                GalleryUploadScreen(
                    hasImage = selectedImageBytes != null,
                    imageBytes = selectedImageBytes,
                    onPickImage = { galleryLauncher.launch("image/*") },
                    onAnalyze = {
                        val imageBytes = selectedImageBytes ?: loadLeafBytes(context)
                        analysisViewModel.analyzeImage(imageBytes)
                        navController.navigate(SylvaDestination.Loading.route)
                    },
                    onBack = { navController.popBackStack() },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(SylvaDestination.Loading.route) {
                LaunchedEffect(uiState) {
                    when (uiState) {
                        is TreeAnalysisUiState.Success,
                        is TreeAnalysisUiState.Error -> {
                            navController.navigate(SylvaDestination.TreeDetails.route) {
                                popUpTo(SylvaDestination.Loading.route) { inclusive = true }
                            }
                        }
                        else -> Unit
                    }
                }

                LoadingAnalysisScreen(
                    onBack = { navController.popBackStack() },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(SylvaDestination.TreeDetails.route) {
                TreeDetailsScreen(
                    profile = currentTree,
                    imageBytes = selectedImageBytes,
                    isError = uiState is TreeAnalysisUiState.Error,
                    errorMessage = currentErrorMessage,
                    onRetry = {
                        analysisViewModel.retryLastAnalysis()
                        navController.navigate(SylvaDestination.Loading.route)
                    },
                    onBack = { navController.popBackStack() },
                    onSave = {
                        if (currentTree != null && !savedTrees.contains(currentTree)) {
                            savedTrees.add(0, currentTree!!)
                        }
                    },
                    onShare = { },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(SylvaDestination.History.route) {
                HistoryScreen(
                    trees = savedTrees,
                    onExploreClick = { navController.navigate(SylvaDestination.Home.route) },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

private fun loadLeafBytes(context: Context): ByteArray {
    val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.leaf)
        ?: error("Leaf fallback image could not be loaded")
    return bitmap.toJpegBytes()
}

private fun Bitmap.toJpegBytes(): ByteArray {
    val out = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 90, out)
    return out.toByteArray()
}
