package com.example.sylva.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sylva.ui.theme.SylvaSpacing
import com.example.sylva.ui.theme.SylvaShapes

@Composable
fun UploadDropZone(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
            .then(
                if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.tertiary,
                shape = SylvaShapes.card
            )
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = SylvaShapes.card
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(SylvaSpacing.sm),
            modifier = Modifier.padding(SylvaSpacing.md)
        ) {
            Icon(
                imageVector = Icons.Outlined.CloudUpload,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = "Drop image here or tap to browse",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "JPEG, PNG up to 5MB",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


