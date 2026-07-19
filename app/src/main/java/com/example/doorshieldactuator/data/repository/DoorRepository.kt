package com.example.doorshieldactuator.data.repository

import com.example.doorshieldactuator.model.DeviceConnectionStatus
import com.example.doorshieldactuator.model.DoorAction
import com.example.doorshieldactuator.model.DoorHistoryItem
import com.example.doorshieldactuator.model.DoorStatus
import com.example.doorshieldactuator.model.DoorUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object DoorRepository {

    private val _doorState =
        MutableStateFlow(
            DoorUiState(
                doorStatus = DoorStatus.LOCKED,
                connectionStatus = DeviceConnectionStatus.CONNECTED
            )
        )

    val doorState: StateFlow<DoorUiState> =
        _doorState.asStateFlow()

    private val _historyItems =
        MutableStateFlow<List<DoorHistoryItem>>(
            emptyList()
        )

    val historyItems: StateFlow<List<DoorHistoryItem>> =
        _historyItems.asStateFlow()

    suspend fun toggleDoor() {

        if (_doorState.value.isLoading) {
            return
        }

        _doorState.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }

        delay(1200)

        val newStatus =
            when (_doorState.value.doorStatus) {

                DoorStatus.LOCKED ->
                    DoorStatus.UNLOCKED

                DoorStatus.UNLOCKED ->
                    DoorStatus.LOCKED
            }

        _doorState.update {
            it.copy(
                doorStatus = newStatus,
                isLoading = false
            )
        }

        addHistoryItem(
            doorStatus = newStatus
        )
    }

    private fun addHistoryItem(
        doorStatus: DoorStatus
    ) {

        val action =
            when (doorStatus) {

                DoorStatus.LOCKED ->
                    DoorAction.LOCKED

                DoorStatus.UNLOCKED ->
                    DoorAction.UNLOCKED
            }

        val historyItem =
            DoorHistoryItem(
                id = System.nanoTime(),
                action = action,
                performedBy = "You",
                timestamp = System.currentTimeMillis()
            )

        _historyItems.update { currentItems ->

            listOf(historyItem) + currentItems
        }
    }
}