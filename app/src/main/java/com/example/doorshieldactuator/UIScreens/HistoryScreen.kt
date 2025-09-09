package com.example.doorshieldactuator.UIScreens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val PrimaryColor = Color(0xFF3B82F6)
val PrimaryColorLight = PrimaryColor.copy(alpha = 0.1f)
val Slate50 = Color(0xFFF8FAFC)
val Slate100 = Color(0xFFF1F5F9)
val Slate200 = Color(0xFFE2E8F0)
val Slate400 = Color(0xFF94A3B8)
val Slate500 = Color(0xFF64748B)
val Slate600 = Color(0xFF475569)
val Slate700 = Color(0xFF334155)
val Slate800 = Color(0xFF1E293B)
val Slate900 = Color(0xFF0F172A)

/**
 * Composable for the filter buttons.
 *
 * This is an extension function of [RowScope] to correctly use the weight modifier.
 */
@Composable
fun RowScope.FilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) PrimaryColor else Slate200
    val textColor = if (isSelected) Color.White else Slate600

    Button(
        onClick = onClick,
        modifier = modifier
            .height(36.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Text(text = text, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

/**
 * Composable for a single history event item.
 */
@Composable
fun HistoryItem(
    icon: @Composable () -> Unit,
    title: String,
    subtitle: String,
    time: String,
    isLastItem: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Slate100),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(text = title, fontWeight = FontWeight.Medium, color = Slate800, fontSize = 16.sp)
            Text(text = subtitle, color = Slate500, fontSize = 14.sp)
        }
        Text(text = time, color = Slate400, fontSize = 14.sp)
    }
    if (!isLastItem) {
        Divider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Slate100,
            thickness = 1.dp
        )
    }
}

/**
 * Main composable for the History screen.
 */
@Composable
fun HistoryScreen() {
    val context = LocalContext.current
    var selectedFilter by remember { mutableStateOf("All") }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Slate50)
            ) {
                // Header Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        Toast.makeText(
                            context,
                            "Back clicked",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Slate700)
                    }
                    Text(
                        text = "History",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Spacer(modifier = Modifier.width(48.dp)) // Placeholder to center the title
                }
                // Filter Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // These calls now correctly use the weight modifier.
                    FilterButton(
                        text = "All",
                        isSelected = selectedFilter == "All",
                        onClick = { selectedFilter = "All" },
                        modifier = Modifier.weight(1f)
                    )
                    FilterButton(
                        text = "Opened",
                        isSelected = selectedFilter == "Opened",
                        onClick = { selectedFilter = "Opened" },
                        modifier = Modifier.weight(1f)
                    )
                    FilterButton(
                        text = "Closed",
                        isSelected = selectedFilter == "Closed",
                        onClick = { selectedFilter = "Closed" },
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = {
                            Toast.makeText(context, "Calendar clicked", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Slate200)
                    ) {
                        Icon(
                            Icons.Default.CalendarMonth,
                            contentDescription = "Calendar",
                            tint = Slate600
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Slate50)
                .padding(16.dp)
        ) {
            // Today's section
            Text(
                text = "TODAY",
                color = Slate500,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
            ) {
                HistoryItem(
                    icon = {
                        Icon(
                            Icons.Default.LockOpen,
                            contentDescription = "Opened",
                            tint = PrimaryColor
                        )
                    },
                    title = "Door Opened",
                    subtitle = "by John Doe",
                    time = "10:30 AM",
                    isLastItem = false
                )
                HistoryItem(
                    icon = {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = "Closed",
                            tint = Slate500
                        )
                    },
                    title = "Door Closed",
                    subtitle = "by Jane Smith",
                    time = "10:31 AM",
                    isLastItem = false
                )
                HistoryItem(
                    icon = {
                        Icon(
                            Icons.Default.LockOpen,
                            contentDescription = "Opened",
                            tint = PrimaryColor
                        )
                    },
                    title = "Door Opened",
                    subtitle = "by John Doe",
                    time = "10:32 AM",
                    isLastItem = true
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "YESTERDAY",
                color = Slate500,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
            ) {
                HistoryItem(
                    icon = {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = "Closed",
                            tint = Slate500
                        )
                    },
                    title = "Door Closed",
                    subtitle = "by Maintenance",
                    time = "8:15 PM",
                    isLastItem = false
                )
                HistoryItem(
                    icon = {
                        Icon(
                            Icons.Default.LockOpen,
                            contentDescription = "Opened",
                            tint = PrimaryColor
                        )
                    },
                    title = "Door Opened",
                    subtitle = "by Jane Smith",
                    time = "5:45 PM",
                    isLastItem = true
                )
            }
        }
    }
}

/**
 * Reusable composable for the bottom navigation items.
 */
@Composable
fun RowScope.NavigationItem(icon: @Composable () -> Unit, label: String, isSelected: Boolean) {
    val textColor = if (isSelected) PrimaryColor else Slate500
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .weight(1f)
            .clickable { /* Handle navigation */ }
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(PrimaryColorLight)
                    .padding(8.dp)
            ) {
                icon()
            }
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                modifier = Modifier.padding(top = 4.dp)
            )
        } else {
            icon()
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                color = textColor,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HistoryScreenPreview() {
    MaterialTheme {
        HistoryScreen()
    }
}