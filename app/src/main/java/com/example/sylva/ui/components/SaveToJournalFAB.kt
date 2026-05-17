package com.example.sylva.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SaveToJournalFAB(
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onSave, modifier = modifier) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(MaterialTheme.colorScheme.tertiary, CircleShape),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.BookmarkBorder,
                contentDescription = "Save to journal",
                tint = MaterialTheme.colorScheme.onTertiary
            )
        }
    }
}

