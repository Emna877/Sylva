package com.example.sylva.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LeakAdd
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.sylva.ui.theme.SylvaSpacing

@Composable
fun UploadTipsRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = SylvaSpacing.md),
        horizontalArrangement = Arrangement.spacedBy(SylvaSpacing.md)
    ) {
        TipItem(
            icon = Icons.Outlined.DarkMode,
            title = "Use natural light",
            modifier = Modifier.weight(1f)
        )
        TipItem(
            icon = Icons.Outlined.LeakAdd,
            title = "Include leaves",
            modifier = Modifier.weight(1f)
        )
        TipItem(
            icon = Icons.Outlined.RemoveRedEye,
            title = "Avoid blur",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun TipItem(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SylvaSpacing.xs)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.tertiary
        )
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

