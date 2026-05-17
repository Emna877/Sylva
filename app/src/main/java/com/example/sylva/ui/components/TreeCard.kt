package com.example.sylva.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.sylva.ui.model.TreeProfile
import com.example.sylva.ui.theme.ForestDeep
import com.example.sylva.ui.theme.MossGreen
import com.example.sylva.ui.theme.SylvaShapes
import com.example.sylva.ui.theme.SylvaSpacing

@Composable
fun TreeCard(
    profile: TreeProfile,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = SylvaShapes.card,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            RoundedImagePreview(modifier = Modifier.height(176.dp))
            Column(
                modifier = Modifier.padding(SylvaSpacing.md),
                verticalArrangement = Arrangement.spacedBy(SylvaSpacing.xs)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(SylvaSpacing.xs)) {
                    ConfidenceBadge(confidence = profile.confidence)
                    SpeciesChip(label = profile.commonNames.first())
                }
                Text(
                    text = profile.scientificName,
                    style = MaterialTheme.typography.titleLarge,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = profile.ecologicalRole,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun RoundedImagePreview(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    listOf(ForestDeep, MossGreen)
                ),
                shape = SylvaShapes.image
            )
    )
}

