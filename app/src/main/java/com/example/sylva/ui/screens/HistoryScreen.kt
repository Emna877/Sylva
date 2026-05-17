package com.example.sylva.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sylva.ui.components.EmptyStateSection
import com.example.sylva.ui.components.TreeCard
import com.example.sylva.ui.model.TreeProfile
import com.example.sylva.ui.theme.SylvaSpacing

@Composable
fun HistoryScreen(
    trees: List<TreeProfile>,
    onExploreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (trees.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(SylvaSpacing.md),
            verticalArrangement = Arrangement.Center
        ) {
            EmptyStateSection(
                title = "No saved trees yet",
                message = "Save your tree discoveries to build a personal biodiversity journal.",
                actionLabel = "Discover trees",
                onActionClick = onExploreClick
            )
        }
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(SylvaSpacing.md),
        verticalArrangement = Arrangement.spacedBy(SylvaSpacing.md)
    ) {
        item {
            Text(text = "Saved Trees", style = MaterialTheme.typography.headlineLarge)
        }
        items(trees) { tree ->
            TreeCard(profile = tree)
        }
    }
}

