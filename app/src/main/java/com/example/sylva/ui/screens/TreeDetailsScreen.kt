package com.example.sylva.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.sylva.ui.components.BackNavigationButton
import com.example.sylva.ui.components.ConfidenceBadge
import com.example.sylva.ui.components.ErrorStateSection
import com.example.sylva.ui.components.InsightCard
import com.example.sylva.ui.components.SaveToJournalFAB
import com.example.sylva.ui.components.ShareButton
import com.example.sylva.ui.components.SpeciesChip
import com.example.sylva.ui.model.TreeProfile
import com.example.sylva.ui.theme.ForestDeep
import com.example.sylva.ui.theme.ForestNight
import com.example.sylva.ui.theme.SylvaShapes
import com.example.sylva.ui.theme.SylvaSpacing
import kotlinx.coroutines.delay

@Composable
fun TreeDetailsScreen(
    profile: TreeProfile?,
    isError: Boolean,
    errorMessage: String? = null,
    onRetry: () -> Unit,
    onBack: () -> Unit = {},
    onSave: () -> Unit = {},
    onShare: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    if (isError) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(SylvaSpacing.md),
            verticalArrangement = Arrangement.Center
        ) {
            ErrorStateSection(
                title = "Tree analysis failed",
                message = errorMessage
                    ?: "We could not retrieve Plant.id or Gemini insights. Check network and try again.",
                onRetry = onRetry
            )
        }
        return
    }

    val safeProfile = profile ?: return
    var revealCount by remember { mutableStateOf(0) }

    LaunchedEffect(safeProfile.scientificName) {
        revealCount = 0
        repeat(safeProfile.insights.size) { index ->
            delay(80)
            revealCount = index + 1
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = SylvaSpacing.md),
            verticalArrangement = Arrangement.spacedBy(SylvaSpacing.md)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = SylvaSpacing.md),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BackNavigationButton(onBackClick = onBack)
                    ShareButton(onShare = onShare)
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(
                            brush = Brush.linearGradient(
                                listOf(ForestDeep, ForestNight)
                            ),
                            shape = SylvaShapes.image
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.alpha(0.6f)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Image,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Tree photo will appear here",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(start = SylvaSpacing.xs)
                        )
                    }
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(SylvaSpacing.sm)) {
                    ConfidenceBadge(confidence = safeProfile.confidence)
                    Text(
                        text = safeProfile.scientificName,
                        style = MaterialTheme.typography.displayLarge,
                        fontStyle = FontStyle.Italic
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(SylvaSpacing.xs),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        safeProfile.commonNames.forEach { name ->
                            SpeciesChip(label = name)
                        }
                    }
                }
            }
            item {
                InsightCard(
                    insight = com.example.sylva.ui.model.InsightItem(
                        title = "Native region",
                        body = safeProfile.nativeRegion,
                        iconLabel = "region"
                    )
                )
            }
            item {
                InsightCard(
                    insight = com.example.sylva.ui.model.InsightItem(
                        title = "Ecological role",
                        body = safeProfile.ecologicalRole,
                        iconLabel = "role"
                    )
                )
            }
            item {
                InsightCard(
                    insight = com.example.sylva.ui.model.InsightItem(
                        title = "Biodiversity importance",
                        body = safeProfile.biodiversityImportance,
                        iconLabel = "biodiversity"
                    )
                )
            }
            item {
                InsightCard(
                    insight = com.example.sylva.ui.model.InsightItem(
                        title = "Urban pollution usefulness",
                        body = safeProfile.urbanUsefulness,
                        iconLabel = "urban"
                    )
                )
            }
            item {
                InsightCard(
                    insight = com.example.sylva.ui.model.InsightItem(
                        title = "Fun fact",
                        body = safeProfile.funFact,
                        iconLabel = "fact"
                    )
                )
            }

            itemsIndexed(safeProfile.insights) { index, insight ->
                AnimatedVisibility(
                    visible = revealCount > index,
                    enter = fadeIn() + slideInVertically(initialOffsetY = { it / 3 })
                ) {
                    InsightCard(insight = insight)
                }
            }
            item {
                Box(modifier = Modifier.height(100.dp))
            }
        }

        SaveToJournalFAB(
            onSave = onSave,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
    }
}
