package com.example.doorshieldactuator.model


enum class DoorAction {
    LOCKED,
    UNLOCKED
}

data class DoorHistoryItem(
    val id: Long,
    val action: DoorAction,
    val performedBy: String,
    val timestamp: Long
)