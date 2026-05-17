package com.example.sylva.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sylva.ui.model.SampleTreeData
import com.example.sylva.ui.model.TreeProfile
import com.example.sylva.ui.navigation.SylvaDestination
import com.example.sylva.ui.screens.CameraScreen
import com.example.sylva.ui.screens.GalleryUploadScreen
import com.example.sylva.ui.screens.HistoryScreen
import com.example.sylva.ui.screens.HomeScreen
import com.example.sylva.ui.screens.LoadingAnalysisScreen
import com.example.sylva.ui.screens.SplashOnboardingScreen
import com.example.sylva.ui.screens.TreeDetailsScreen
import kotlinx.coroutines.delay

@Composable
fun SylvaApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    var currentTree by remember { mutableStateOf<TreeProfile?>(null) }
    val savedTrees = remember { mutableStateListOf<TreeProfile>() }
    var hasGalleryImage by remember { mutableStateOf(false) }
    var analysisError by remember { mutableStateOf(false) }

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
                        currentTree = SampleTreeData.oakProfile
                        if (!savedTrees.contains(SampleTreeData.oakProfile)) {
                            savedTrees.add(0, SampleTreeData.oakProfile)
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            composable(SylvaDestination.Camera.route) {
                CameraScreen(
                    onCapture = {
                        analysisError = false
                        navController.navigate(SylvaDestination.Loading.route)
                    },
                    onBack = { navController.popBackStack() },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(SylvaDestination.Gallery.route) {
                GalleryUploadScreen(
                    hasImage = hasGalleryImage,
                    onPickImage = { hasGalleryImage = true },
                    onAnalyze = {
                        analysisError = false
                        navController.navigate(SylvaDestination.Loading.route)
                    },
                    onBack = { navController.popBackStack() },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(SylvaDestination.Loading.route) {
                LaunchedEffect(Unit) {
                    delay(1600)
                    currentTree = SampleTreeData.oakProfile
                    if (!savedTrees.contains(SampleTreeData.oakProfile)) {
                        savedTrees.add(0, SampleTreeData.oakProfile)
                    }
                    navController.navigate(SylvaDestination.TreeDetails.route) {
                        popUpTo(SylvaDestination.Loading.route) { inclusive = true }
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
                    isError = analysisError,
                    onRetry = {
                        analysisError = false
                        navController.navigate(SylvaDestination.Loading.route)
                    },
                    onBack = { navController.popBackStack() },
                    onSave = {
                        if (currentTree != null && !savedTrees.contains(currentTree)) {
                            savedTrees.add(0, currentTree!!)
                        }
                    },
                    onShare = { /* Share functionality */ },
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
