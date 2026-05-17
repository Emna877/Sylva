package com.example.sylva.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.sylva.ui.theme.LowConfidence
import com.example.sylva.ui.theme.MediumConfidence
import com.example.sylva.ui.theme.SuccessConfidence
import com.example.sylva.ui.theme.SylvaShapes
import com.example.sylva.ui.theme.SylvaSpacing

@Composable
fun ConfidenceBadge(
    confidence: Float,
    modifier: Modifier = Modifier
) {
    val normalized = confidence.coerceIn(0f, 1f)
    val badgeColor = when {
        normalized >= 0.85f -> SuccessConfidence
        normalized >= 0.65f -> MediumConfidence
        else -> LowConfidence
    }

    Text(
        text = "${(normalized * 100).toInt()}% confidence",
        modifier = modifier
            .background(color = badgeColor, shape = SylvaShapes.chip)
            .padding(horizontal = SylvaSpacing.sm, vertical = SylvaSpacing.xs),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onTertiary,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun SpeciesChip(
    label: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = label,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = SylvaShapes.chip
            )
            .padding(horizontal = SylvaSpacing.sm, vertical = SylvaSpacing.xs),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

