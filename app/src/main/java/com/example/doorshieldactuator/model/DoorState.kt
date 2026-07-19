package com.example.doorshieldactuator.model

enum class DoorStatus {
    LOCKED,
    UNLOCKED
}

enum class DeviceConnectionStatus {
    CONNECTED,
    DISCONNECTED
}

data class DoorUiState(
    val doorStatus: DoorStatus = DoorStatus.LOCKED,
    val connectionStatus: DeviceConnectionStatus =
        DeviceConnectionStatus.CONNECTED,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)