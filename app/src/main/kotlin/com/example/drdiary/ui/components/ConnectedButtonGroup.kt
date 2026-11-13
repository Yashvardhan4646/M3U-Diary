package com.example.drdiary.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.rounded.ViewList
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

import androidx.compose.ui.text.style.TextOverflow

enum class DiaryFilter {
    FAVORITES,
    ALL_ENTRIES
}

/**
 * M3 Expressive Connected Button Group (56dp tall, 3dp gap, 8dp inner corners, 28dp outer corners).
 * Centered near the top of the screen. Responsive and overflow-proof.
 */
@Composable
fun ConnectedButtonGroup(
    selectedFilter: DiaryFilter,
    onFilterSelected: (DiaryFilter) -> Unit,
    favoritesCount: Int = 0,
    allCount: Int = 0,
    modifier: Modifier = Modifier
) {
    val leftShape = RoundedCornerShape(
        topStart = 28.dp,
        bottomStart = 28.dp,
        topEnd = 8.dp,
        bottomEnd = 8.dp
    )
    val rightShape = RoundedCornerShape(
        topStart = 8.dp,
        bottomStart = 8.dp,
        topEnd = 28.dp,
        bottomEnd = 28.dp
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left Button: "Favorites" (Filled when selected)
        val isFavSelected = selectedFilter == DiaryFilter.FAVORITES
        val favBgColor = if (isFavSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.surfaceContainerHigh
        }
        val favTextColor = if (isFavSelected) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .clip(leftShape)
                .background(favBgColor)
                .then(
                    if (!isFavSelected) {
                        Modifier.border(1.dp, MaterialTheme.colorScheme.outlineVariant, leftShape)
                    } else Modifier
                )
                .pressScale(onClick = { onFilterSelected(DiaryFilter.FAVORITES) })
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Favorite,
                    contentDescription = null,
                    tint = favTextColor,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (favoritesCount > 0) "Favorites ($favoritesCount)" else "Favorites",
                    style = MaterialTheme.typography.labelLarge,
                    color = favTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.width(3.dp))

        // Right Button: "All Entries" (Tonal when selected)
        val isAllSelected = selectedFilter == DiaryFilter.ALL_ENTRIES
        val allBgColor = if (isAllSelected) {
            MaterialTheme.colorScheme.secondaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceContainerHigh
        }
        val allTextColor = if (isAllSelected) {
            MaterialTheme.colorScheme.onSecondaryContainer
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .clip(rightShape)
                .background(allBgColor)
                .then(
                    if (!isAllSelected) {
                        Modifier.border(1.dp, MaterialTheme.colorScheme.outlineVariant, rightShape)
                    } else Modifier
                )
                .pressScale(onClick = { onFilterSelected(DiaryFilter.ALL_ENTRIES) })
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ViewList,
                    contentDescription = null,
                    tint = allTextColor,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (allCount > 0) "All Entries ($allCount)" else "All Entries",
                    style = MaterialTheme.typography.labelLarge,
                    color = allTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
