package com.example.doorshieldactuator.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doorshieldactuator.model.DeviceConnectionStatus
import com.example.doorshieldactuator.model.DoorAction
import com.example.doorshieldactuator.model.DoorHistoryItem
import com.example.doorshieldactuator.model.DoorStatus
import com.example.doorshieldactuator.model.DoorUiState
import com.example.doorshieldactuator.ui.components.GlassSurface
import com.example.doorshieldactuator.ui.theme.SuccessGreen

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val historyItems by viewModel.historyItems.collectAsStateWithLifecycle()

    HomeContent(
        uiState = uiState,
        lastHistoryItem = historyItems.firstOrNull(),
        onDoorActionClick = viewModel::onDoorActionClick
    )
}

@Composable
private fun HomeContent(
    uiState: DoorUiState,
    lastHistoryItem: DoorHistoryItem?,
    onDoorActionClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 20.dp,
                vertical = 20.dp
            )
    ) {
        HomeHeader()

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        ConnectionStatus(
            connectionStatus = uiState.connectionStatus
        )

        Spacer(
            modifier = Modifier.weight(0.6f)
        )

        DoorControlSection(
            uiState = uiState,
            onDoorActionClick = onDoorActionClick
        )

        Spacer(
            modifier = Modifier.weight(0.8f)
        )

        LastActivityCard(
            historyItem = lastHistoryItem
        )
    }
}

@Composable
private fun HomeHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Welcome home",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            Text(
                text = "DoorShield",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        GlassSurface(
            modifier = Modifier.size(48.dp),
            cornerRadius = 24.dp,
            elevation = 6.dp,
            contentPadding = PaddingValues(0.dp)
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun ConnectionStatus(
    connectionStatus: DeviceConnectionStatus
) {
    val isConnected =
        connectionStatus == DeviceConnectionStatus.CONNECTED

    GlassSurface(
        cornerRadius = 50.dp,
        elevation = 5.dp,
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 10.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(9.dp)
                    .background(
                        color = if (isConnected) {
                            SuccessGreen
                        } else {
                            MaterialTheme.colorScheme.error
                        },
                        shape = CircleShape
                    )
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Text(
                text = if (isConnected) {
                    "Device connected"
                } else {
                    "Device offline"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun DoorControlSection(
    uiState: DoorUiState,
    onDoorActionClick: () -> Unit
) {
    val isLocked =
        uiState.doorStatus == DoorStatus.LOCKED

    val controlScale by animateFloatAsState(
        targetValue = if (uiState.isLoading) {
            0.96f
        } else {
            1f
        },
        animationSpec = tween(300),
        label = "doorControlScale"
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(224.dp)
                .scale(controlScale),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(224.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(
                                    alpha = if (isLocked) 0.18f else 0.12f
                                ),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )

            GlassSurface(
                modifier = Modifier.size(188.dp),
                cornerRadius = 94.dp,
                elevation = 18.dp,
                contentPadding = PaddingValues(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme
                                        .primaryContainer
                                        .copy(alpha = 0.78f),
                                    MaterialTheme.colorScheme
                                        .primaryContainer
                                        .copy(alpha = 0.40f)
                                )
                            ),
                            shape = CircleShape
                        )
                        .border(
                            width = 1.dp,
                            color = Color.White.copy(alpha = 0.20f),
                            shape = CircleShape
                        )
                        .clickable(
                            enabled =
                                !uiState.isLoading &&
                                        uiState.connectionStatus ==
                                        DeviceConnectionStatus.CONNECTED,
                            onClick = onDoorActionClick
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(54.dp),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 4.dp
                        )
                    } else {
                        Crossfade(
                            targetState = isLocked,
                            animationSpec = tween(350),
                            label = "doorIcon"
                        ) { locked ->
                            Icon(
                                imageVector = if (locked) {
                                    Icons.Default.Lock
                                } else {
                                    Icons.Default.LockOpen
                                },
                                contentDescription = if (locked) {
                                    "Door locked"
                                } else {
                                    "Door unlocked"
                                },
                                modifier = Modifier.size(70.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )
        Text(
            text = when (uiState.doorStatus) {
                DoorStatus.LOCKED -> "Door Locked"
                DoorStatus.UNLOCKED -> "Door Unlocked"
                else -> "Door Status Unknown"
            },
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = when {
                uiState.isLoading ->
                    "Sending command to your door..."

                uiState.connectionStatus !=
                        DeviceConnectionStatus.CONNECTED ->
                    "DoorShield is currently offline"

                isLocked ->
                    "Your home is secure"

                else ->
                    "Your door is currently unlocked"
            },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        DoorActionButton(
            isLocked = isLocked,
            isLoading = uiState.isLoading,
            isConnected =
                uiState.connectionStatus ==
                        DeviceConnectionStatus.CONNECTED,
            onClick = onDoorActionClick
        )

        uiState.errorMessage?.let { error ->
            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun DoorActionButton(
    isLocked: Boolean,
    isLoading: Boolean,
    isConnected: Boolean,
    onClick: () -> Unit
) {
    val buttonColor by animateColorAsState(
        targetValue = if (isLocked) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        },
        animationSpec = tween(300),
        label = "buttonColor"
    )

    val contentColor by animateColorAsState(
        targetValue = if (isLocked) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onSurface
        },
        animationSpec = tween(300),
        label = "buttonContentColor"
    )

    GlassSurface(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 22.dp,
        elevation = 10.dp,
        contentPadding = PaddingValues(5.dp)
    ) {
        Button(
            onClick = onClick,
            enabled = !isLoading && isConnected,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor.copy(
                    alpha = if (isLocked) 0.92f else 0.72f
                ),
                contentColor = contentColor,
                disabledContainerColor =
                    MaterialTheme.colorScheme
                        .surfaceVariant
                        .copy(alpha = 0.55f),
                disabledContentColor =
                    MaterialTheme.colorScheme
                        .onSurfaceVariant
                        .copy(alpha = 0.6f)
            )
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(21.dp),
                    color = contentColor,
                    strokeWidth = 2.5.dp
                )

                Spacer(
                    modifier = Modifier.width(10.dp)
                )

                Text(
                    text = "Please wait",
                    style = MaterialTheme.typography.titleMedium
                )
            } else {
                Icon(
                    imageVector = if (isLocked) {
                        Icons.Default.LockOpen
                    } else {
                        Icons.Default.Lock
                    },
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )

                Spacer(
                    modifier = Modifier.width(10.dp)
                )

                Text(
                    text = if (isLocked) {
                        "Unlock Door"
                    } else {
                        "Lock Door"
                    },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun LastActivityCard(
    historyItem: DoorHistoryItem?
) {
    GlassSurface(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 22.dp,
        elevation = 8.dp,
        contentPadding = PaddingValues(18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme
                    .primaryContainer
                    .copy(alpha = 0.65f)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = null,
                        modifier = Modifier.size(23.dp),
                        tint = MaterialTheme.colorScheme.primary
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
                    text = "Last activity",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = when {
                        historyItem == null ->
                            "No activity yet"

                        historyItem.action == DoorAction.LOCKED ->
                            "Door was locked by ${historyItem.performedBy}"

                        else ->
                            "Door was unlocked by ${historyItem.performedBy}"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (historyItem != null) {
                Surface(
                    modifier = Modifier.size(10.dp),
                    shape = CircleShape,
                    color = if (
                        historyItem.action ==
                        DoorAction.UNLOCKED
                    ) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        SuccessGreen
                    }
                ) {}
            }
        }
    }
}