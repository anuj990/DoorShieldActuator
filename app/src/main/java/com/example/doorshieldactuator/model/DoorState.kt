package com.example.doorshieldactuator.model

import com.example.doorshieldactuator.data.repository.network.DeviceManager

enum class DoorStatus {
    LOCKED,
    UNLOCKED
}

enum class DeviceConnectionStatus {
    CONNECTING,
    CONNECTED,
    DISCONNECTED
}

data class DoorUiState(
    val doorStatus: DoorStatus = DoorStatus.LOCKED,
    val connectionStatus: DeviceConnectionStatus =
        DeviceConnectionStatus.DISCONNECTED,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val cameraStreamUrl: String =
        DeviceManager.streamUrl
)