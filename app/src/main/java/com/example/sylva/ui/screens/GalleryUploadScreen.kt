package com.example.sylva.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.sylva.ui.components.BackNavigationButton
import com.example.sylva.ui.components.UploadDropZone
import com.example.sylva.ui.components.UploadTipsRow
import com.example.sylva.ui.components.RoundedImagePreview
import com.example.sylva.ui.theme.SylvaSpacing

@Composable
fun GalleryUploadScreen(
    hasImage: Boolean,
    onPickImage: () -> Unit,
    onAnalyze: () -> Unit,
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        BackNavigationButton(
            onBackClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = SylvaSpacing.md, start = SylvaSpacing.md)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SylvaSpacing.md),
            verticalArrangement = Arrangement.spacedBy(SylvaSpacing.lg)
        ) {
            Text(text = "Upload tree image", style = MaterialTheme.typography.headlineLarge)
            Text(
                text = "Use a clear image with leaves or bark in focus for best identification.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (!hasImage) {
                UploadDropZone(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                OutlinedButton(
                    onClick = onPickImage,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Select from gallery")
                }
            } else {
                RoundedImagePreview(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Button(
                    onClick = onAnalyze,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Analyze with AI")
                }
                OutlinedButton(
                    onClick = onPickImage,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Choose another image")
                }
            }

            UploadTipsRow()
        }
    }
}
