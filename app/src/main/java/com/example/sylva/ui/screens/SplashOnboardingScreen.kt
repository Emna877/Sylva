package com.example.sylva.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.sylva.R
import com.example.sylva.ui.theme.Cream
import com.example.sylva.ui.theme.ForestDeep
import com.example.sylva.ui.theme.ForestNight
import com.example.sylva.ui.theme.MossGreen
import com.example.sylva.ui.theme.SylvaSpacing

@Composable
fun SplashOnboardingScreen(
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(600)) + slideInVertically(
            initialOffsetY = { 30 },
            animationSpec = tween(600)
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(ForestNight, ForestDeep, MossGreen)
                    )
                )
                .padding(SylvaSpacing.xl),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(SylvaSpacing.md)) {
                Icon(
                    imageVector = Icons.Outlined.Eco,
                    contentDescription = null,
                    tint = Cream
                )
                Text(
                    text = "Sylva",
                    style = MaterialTheme.typography.displayLarge,
                    color = Cream
                )
                Text(
                    text = "Identify trees, understand ecosystems, and explore urban biodiversity through AI.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Cream
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.leaf),
                    contentDescription = null
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(SylvaSpacing.md)) {
                Text(
                    text = "Your botanical field guide, reimagined for mobile.",
                    style = MaterialTheme.typography.titleMedium,
                    color = Cream,
                    textAlign = TextAlign.Start
                )
                Button(
                    onClick = onContinue,
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Text(text = "Start exploring", color = Cream)
                }
            }
        }
    }
}
