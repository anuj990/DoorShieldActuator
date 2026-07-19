package com.example.doorshieldactuator.ui.screens

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doorshieldactuator.model.DoorAction
import com.example.doorshieldactuator.model.DoorHistoryItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = viewModel()
) {

    val historyItems by
    viewModel.filteredHistory.collectAsStateWithLifecycle()

    val selectedFilter by
    viewModel.selectedFilter.collectAsStateWithLifecycle()

    val groupedHistory =
        historyItems.groupBy {
            getDateSection(it.timestamp)
        }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 20.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {

            HistoryHeader()
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

            groupedHistory.forEach { (section, items) ->

                item(
                    key = section
                ) {

                    Text(
                        text = section.uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        color =
                            MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(
                            top = 8.dp
                        )
                    )
                }

                items(
                    items = items,
                    key = {
                        it.id
                    }
                ) { historyItem ->

                    HistoryItemCard(
                        historyItem = historyItem
                    )
                }
            }
        }
    }
}

@Composable
private fun HistoryHeader() {

    Column {

        Text(
            text = "History",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = "View recent door activity",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun HistoryFilters(
    selectedFilter: HistoryFilter,
    onFilterSelected: (HistoryFilter) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        HistoryFilterChip(
            text = "All",
            selected =
                selectedFilter == HistoryFilter.ALL,
            onClick = {
                onFilterSelected(
                    HistoryFilter.ALL
                )
            },
            modifier = Modifier.weight(1f)
        )

        HistoryFilterChip(
            text = "Locked",
            selected =
                selectedFilter == HistoryFilter.LOCKED,
            onClick = {
                onFilterSelected(
                    HistoryFilter.LOCKED
                )
            },
            modifier = Modifier.weight(1f)
        )

        HistoryFilterChip(
            text = "Unlocked",
            selected =
                selectedFilter == HistoryFilter.UNLOCKED,
            onClick = {
                onFilterSelected(
                    HistoryFilter.UNLOCKED
                )
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun HistoryFilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = text
                )
            }
        },
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor =
                MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor =
                MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Composable
private fun HistoryItemCard(
    historyItem: DoorHistoryItem
) {

    val isUnlocked =
        historyItem.action == DoorAction.UNLOCKED

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.surface
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                color = if (isUnlocked) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                }
            ) {

                Box(
                    contentAlignment =
                        Alignment.Center
                ) {

                    Icon(
                        imageVector =
                            if (isUnlocked) {
                                Icons.Default.LockOpen
                            } else {
                                Icons.Default.Lock
                            },
                        contentDescription = null,
                        tint =
                            if (isUnlocked) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme
                                    .onSurfaceVariant
                            },
                        modifier =
                            Modifier.size(24.dp)
                    )
                }
            }

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text =
                        if (isUnlocked) {
                            "Door Unlocked"
                        } else {
                            "Door Locked"
                        },
                    style =
                        MaterialTheme.typography.titleMedium,
                    color =
                        MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text =
                        "by ${historyItem.performedBy}",
                    style =
                        MaterialTheme.typography.bodyMedium,
                    color =
                        MaterialTheme.colorScheme
                            .onSurfaceVariant
                )
            }

            Text(
                text = formatTime(
                    historyItem.timestamp
                ),
                style =
                    MaterialTheme.typography.bodySmall,
                color =
                    MaterialTheme.colorScheme
                        .onSurfaceVariant
            )
        }
    }
}

@Composable
private fun EmptyHistory() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 80.dp
            ),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Surface(
            modifier = Modifier.size(80.dp),
            shape = CircleShape,
            color =
                MaterialTheme.colorScheme.surfaceVariant
        ) {

            Box(
                contentAlignment =
                    Alignment.Center
            ) {

                Icon(
                    imageVector =
                        Icons.Default.History,
                    contentDescription = null,
                    modifier =
                        Modifier.size(36.dp),
                    tint =
                        MaterialTheme.colorScheme
                            .onSurfaceVariant
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "No activity found",
            style =
                MaterialTheme.typography.titleMedium,
            color =
                MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text =
                "Door activity will appear here.",
            style =
                MaterialTheme.typography.bodyMedium,
            color =
                MaterialTheme.colorScheme
                    .onSurfaceVariant
        )
    }
}

private fun formatTime(
    timestamp: Long
): String {

    val formatter =
        SimpleDateFormat(
            "hh:mm a",
            Locale.getDefault()
        )

    return formatter.format(
        Date(timestamp)
    )
}

private fun getDateSection(
    timestamp: Long
): String {

    val eventCalendar =
        Calendar.getInstance().apply {
            timeInMillis = timestamp
        }

    val today =
        Calendar.getInstance()

    val yesterday =
        Calendar.getInstance().apply {
            add(
                Calendar.DAY_OF_YEAR,
                -1
            )
        }

    return when {

        isSameDay(
            eventCalendar,
            today
        ) -> {
            "Today"
        }

        isSameDay(
            eventCalendar,
            yesterday
        ) -> {
            "Yesterday"
        }

        else -> {

            SimpleDateFormat(
                "dd MMM yyyy",
                Locale.getDefault()
            ).format(
                Date(timestamp)
            )
        }
    }
}

private fun isSameDay(
    first: Calendar,
    second: Calendar
): Boolean {

    return first.get(
        Calendar.YEAR
    ) == second.get(
        Calendar.YEAR
    ) &&
            first.get(
                Calendar.DAY_OF_YEAR
            ) == second.get(
        Calendar.DAY_OF_YEAR
    )
}