package com.example.sylva.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ShareButton(
    onShare: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onShare, modifier = modifier) {
        Icon(
            imageVector = Icons.Outlined.Share,
            contentDescription = "Share this tree",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

