package com.example.doorshieldactuator.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doorshieldactuator.model.DeviceConnectionStatus
import com.example.doorshieldactuator.model.DoorStatus
import com.example.doorshieldactuator.model.DoorUiState
import com.example.doorshieldactuator.ui.theme.SuccessGreen

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HomeHeader()

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        ConnectionStatus(
            connectionStatus = uiState.connectionStatus
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        DoorStatusControl(
            doorStatus = uiState.doorStatus
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        DoorActionButton(
            uiState = uiState,
            onClick = viewModel::onDoorActionClick
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        LastActivityCard(
            doorStatus = uiState.doorStatus
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )
    }
}


@Composable
private fun HomeHeader() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = "DoorShield",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Home Entrance",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Surface(
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surface
        ) {

            IconButton(
                onClick = {
                    // Settings navigation will be added later.
                }
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

    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 1.dp
    ) {

        Row(
            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 9.dp
            ),
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
private fun DoorStatusControl(
    doorStatus: DoorStatus
) {

    val isLocked =
        doorStatus == DoorStatus.LOCKED

    val outerCircleColor by animateColorAsState(
        targetValue = if (isLocked) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        },
        label = "outerCircleColor"
    )

    val scale by animateFloatAsState(
        targetValue = if (isLocked) {
            1f
        } else {
            1.04f
        },
        label = "lockScale"
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(210.dp)
                .scale(scale)
                .background(
                    color = outerCircleColor,
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                        .copy(alpha = 0.5f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            Surface(
                modifier = Modifier.size(145.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary,
                shadowElevation = 12.dp
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    AnimatedContent(
                        targetState = isLocked,
                        label = "lockIcon"
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
                            modifier = Modifier.size(68.dp),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        AnimatedContent(
            targetState = isLocked,
            label = "statusText"
        ) { locked ->

            Text(
                text = if (locked) {
                    "Door Locked"
                } else {
                    "Door Unlocked"
                },
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = if (isLocked) {
                "Your home entrance is secured."
            } else {
                "Your home entrance is currently unlocked."
            },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
private fun DoorActionButton(
    uiState: DoorUiState,
    onClick: () -> Unit
) {

    val isLocked =
        uiState.doorStatus == DoorStatus.LOCKED

    val isConnected =
        uiState.connectionStatus ==
                DeviceConnectionStatus.CONNECTED

    Button(
        onClick = onClick,
        enabled = isConnected && !uiState.isLoading,
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),
        shape = RoundedCornerShape(16.dp)
    ) {

        if (uiState.isLoading) {

            CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )

        } else {

            Icon(
                imageVector = if (isLocked) {
                    Icons.Default.LockOpen
                } else {
                    Icons.Default.Lock
                },
                contentDescription = null
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
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}


@Composable
private fun LastActivityCard(
    doorStatus: DoorStatus
) {

    val isLocked =
        doorStatus == DoorStatus.LOCKED

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = null,
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
                    text = if (isLocked) {
                        "Door was locked recently"
                    } else {
                        "Door was unlocked recently"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}