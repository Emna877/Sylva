package com.example.sylva.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.sylva.ui.model.SampleTreeData
import com.example.sylva.ui.theme.SylvaTheme

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SplashPreview() {
    SylvaTheme {
        SplashOnboardingScreen(onContinue = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    SylvaTheme {
        HomeScreen(
            latestTree = SampleTreeData.oakProfile,
            onScanClick = {},
            onUploadClick = {},
            onHistoryClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CameraPreview() {
    SylvaTheme {
        CameraScreen(onCapture = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun GalleryPreview() {
    SylvaTheme {
        GalleryUploadScreen(hasImage = true, onPickImage = {}, onAnalyze = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingPreview() {
    SylvaTheme {
        LoadingAnalysisScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailsPreview() {
    SylvaTheme {
        TreeDetailsScreen(profile = SampleTreeData.oakProfile, isError = false, onRetry = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryPreview() {
    SylvaTheme {
        HistoryScreen(trees = listOf(SampleTreeData.oakProfile), onExploreClick = {})
    }
}

