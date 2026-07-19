package com.example.doorshieldactuator.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doorshieldactuator.model.DoorAction
import com.example.doorshieldactuator.model.DoorHistoryItem
import com.example.doorshieldactuator.ui.components.GlassSurface
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = viewModel()
) {
    val historyItems by viewModel.filteredHistory.collectAsStateWithLifecycle()
    val selectedFilter by viewModel.selectedFilter.collectAsStateWithLifecycle()

    val groupedHistory = historyItems.groupBy {
        getDateSection(it.timestamp)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 20.dp
        ),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "History",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = "View recent door activity",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        item {
            HistoryFilters(
                selectedFilter = selectedFilter,
                onFilterSelected = viewModel::selectFilter
            )
        }

        if (historyItems.isEmpty()) {
            item {
                EmptyHistory()
            }
        } else {
            groupedHistory.forEach { (section, sectionItems) ->
                item(key = section) {
                    Text(
                        text = section.uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(
                            start = 4.dp,
                            top = 8.dp
                        )
                    )
                }

                items(
                    items = sectionItems,
                    key = { it.id }
                ) {
                    HistoryItemCard(it)
                }
            }
        }
    }
}

@Composable
private fun HistoryFilters(
    selectedFilter: HistoryFilter,
    onFilterSelected: (HistoryFilter) -> Unit
) {
    GlassSurface(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 22.dp,
        elevation = 6.dp,
        contentPadding = PaddingValues(6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            HistoryFilter.entries.forEach { filter ->
                val selected = selectedFilter == filter

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(17.dp))
                        .background(
                            if (selected) {
                                MaterialTheme.colorScheme
                                    .primaryContainer
                                    .copy(alpha = 0.80f)
                            } else {
                                Color.Transparent
                            }
                        )
                        .clickable {
                            onFilterSelected(filter)
                        }
                        .padding(vertical = 11.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when (filter) {
                            HistoryFilter.ALL -> "All"
                            HistoryFilter.LOCKED -> "Locked"
                            HistoryFilter.UNLOCKED -> "Unlocked"
                        },
                        style = MaterialTheme.typography.labelLarge,
                        color = if (selected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun HistoryItemCard(
    historyItem: DoorHistoryItem
) {
    val isUnlocked =
        historyItem.action == DoorAction.UNLOCKED

    GlassSurface(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 22.dp,
        elevation = 8.dp,
        contentPadding = PaddingValues(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                color = if (isUnlocked) {
                    MaterialTheme.colorScheme.primaryContainer.copy(
                        alpha = 0.75f
                    )
                } else {
                    MaterialTheme.colorScheme.surfaceVariant.copy(
                        alpha = 0.65f
                    )
                }
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isUnlocked) {
                            Icons.Default.LockOpen
                        } else {
                            Icons.Default.Lock
                        },
                        contentDescription = null,
                        tint = if (isUnlocked) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = if (isUnlocked) {
                        "Door Unlocked"
                    } else {
                        "Door Locked"
                    },
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(Modifier.height(3.dp))

                Text(
                    text = "by ${historyItem.performedBy}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = formatTime(historyItem.timestamp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun EmptyHistory() {
    GlassSurface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        cornerRadius = 28.dp,
        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 48.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(76.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme
                    .primaryContainer
                    .copy(alpha = 0.6f)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = null,
                        modifier = Modifier.size(34.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            Text(
                text = "No activity found",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(5.dp))

            Text(
                text = "Door activity will appear here.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun formatTime(timestamp: Long): String {
    return SimpleDateFormat(
        "hh:mm a",
        Locale.getDefault()
    ).format(Date(timestamp))
}

private fun getDateSection(timestamp: Long): String {
    val eventCalendar = Calendar.getInstance().apply {
        timeInMillis = timestamp
    }

    val today = Calendar.getInstance()

    val yesterday = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, -1)
    }

    return when {
        isSameDay(eventCalendar, today) -> "Today"
        isSameDay(eventCalendar, yesterday) -> "Yesterday"

        else -> SimpleDateFormat(
            "dd MMM yyyy",
            Locale.getDefault()
        ).format(Date(timestamp))
    }
}

private fun isSameDay(
    first: Calendar,
    second: Calendar
): Boolean {
    return first.get(Calendar.YEAR) ==
            second.get(Calendar.YEAR) &&
            first.get(Calendar.DAY_OF_YEAR) ==
            second.get(Calendar.DAY_OF_YEAR)
}