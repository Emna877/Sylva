package com.example.sylva.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.sylva.ui.components.BackNavigationButton
import com.example.sylva.ui.components.FloatingCaptureButton
import com.example.sylva.ui.theme.SylvaSpacing

@Composable
fun CameraScreen(
    onCapture: () -> Unit,
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val borderColor = MaterialTheme.colorScheme.tertiary

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
            verticalArrangement = Arrangement.spacedBy(SylvaSpacing.lg, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp)
                    .drawBehind {
                        drawRoundRect(
                            color = borderColor,
                            cornerRadius = CornerRadius(28.dp.toPx()),
                            style = Stroke(
                                width = 2.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(18f, 14f), 0f)
                            )
                        )
                    }
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(28.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Live viewfinder",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Align tree trunk and leaves inside frame",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text(
                text = "Tap capture button to scan tree",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.tertiary
            )

            FloatingCaptureButton(onCapture = onCapture)
        }
    }
}
