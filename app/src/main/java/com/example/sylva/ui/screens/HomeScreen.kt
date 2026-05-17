package com.example.sylva.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sylva.R
import com.example.sylva.ui.components.EmptyStateSection
import com.example.sylva.ui.components.PulseCameraButton
import com.example.sylva.ui.components.TreeCard
import com.example.sylva.ui.model.TreeProfile
import com.example.sylva.ui.theme.SylvaSpacing

@Composable
fun HomeScreen(
    latestTree: TreeProfile?,
    onScanClick: () -> Unit,
    onUploadClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SylvaSpacing.md)
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(SylvaSpacing.lg)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = SylvaSpacing.sm),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(SylvaSpacing.xs)
                ) {
                    Text(text = "Welcome back", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "What tree will you\ndiscover today?",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }

                Text(
                    text = "Sylva",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = SylvaSpacing.sm)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SylvaSpacing.sm)
            ) {
                PulseCameraButton(
                    onCapture = onScanClick,
                    modifier = Modifier.weight(1f)
                )
                OutlinedButton(
                    onClick = onUploadClick,
                    modifier = Modifier.weight(1f)
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Outlined.PhotoLibrary,
                        contentDescription = null
                    )
                    Text(text = " Upload")
                }
            }

            if (latestTree == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 180.dp)
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.leaf),
                        contentDescription = null,
                        modifier = Modifier.padding(4.dp)
                    )
                }
                EmptyStateSection(
                    title = "No discoveries yet",
                    message = "Start with camera scan or gallery upload to build your field journal.",
                    actionLabel = "Try demo tree",
                    onActionClick = onRetry
                )
            } else {
                TreeCard(profile = latestTree)
            }
        }

        OutlinedButton(
            onClick = onHistoryClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(SylvaSpacing.md)
        ) {
            Text(text = "📖 Saved trees")
        }
    }
}
