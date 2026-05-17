package com.example.sylva.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BackNavigationButton(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onBackClick, modifier = modifier) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = "Go back",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

