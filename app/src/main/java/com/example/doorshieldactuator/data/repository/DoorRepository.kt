package com.example.doorshieldactuator.data.repository

import com.example.doorshieldactuator.data.repository.network.NetworkModule
import com.example.doorshieldactuator.model.DeviceConnectionStatus
import com.example.doorshieldactuator.model.DoorAction
import com.example.doorshieldactuator.model.DoorHistoryItem
import com.example.doorshieldactuator.model.DoorStatus
import com.example.doorshieldactuator.model.DoorUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object DoorRepository {

    private val api = NetworkModule.doorApi

    private val _doorState = MutableStateFlow(
        DoorUiState()
    )

    val doorState: StateFlow<DoorUiState> =
        _doorState.asStateFlow()

    private val _historyItems =
        MutableStateFlow<List<DoorHistoryItem>>(emptyList())

    val historyItems: StateFlow<List<DoorHistoryItem>> =
        _historyItems.asStateFlow()

    suspend fun checkConnection() {

        try {

            val response = api.getStatus()

            if (response.isSuccessful) {

                _doorState.update {
                    it.copy(
                        connectionStatus = DeviceConnectionStatus.CONNECTED
                    )
                }

                response.body()?.let {

                    updateDoorStatus(
                        if (it.locked)
                            DoorStatus.LOCKED
                        else
                            DoorStatus.UNLOCKED
                    )
                }

            } else {

                setConnection(
                    DeviceConnectionStatus.DISCONNECTED
                )
            }

        } catch (_: Exception) {

            setConnection(
                DeviceConnectionStatus.DISCONNECTED
            )
        }
    }

    suspend fun lockDoor() {

        val response = api.lockDoor()

        if (response.isSuccessful) {

            updateDoorStatus(DoorStatus.LOCKED)

        } else {

            throw Exception("Failed to lock door")
        }
    }

    suspend fun unlockDoor() {

        val response = api.unlockDoor()

        if (response.isSuccessful) {

            updateDoorStatus(DoorStatus.UNLOCKED)

        } else {

            throw Exception("Failed to unlock door")
        }
    }

    private fun updateDoorStatus(
        status: DoorStatus
    ) {

        _doorState.update {
            it.copy(
                doorStatus = status,
                isLoading = false,
                errorMessage = null
            )
        }

        addHistoryItem(status)
    }

    fun setLoading(
        loading: Boolean
    ) {

        _doorState.update {
            it.copy(
                isLoading = loading
            )
        }
    }

    fun setConnection(
        status: DeviceConnectionStatus
    ) {

        _doorState.update {
            it.copy(
                connectionStatus = status
            )
        }
    }

    fun setError(
        message: String?
    ) {

        _doorState.update {
            it.copy(
                isLoading = false,
                errorMessage = message
            )
        }
    }

    fun onDoorLocked() {
        updateDoorStatus(DoorStatus.LOCKED)
    }

    fun onDoorUnlocked() {
        updateDoorStatus(DoorStatus.UNLOCKED)
    }

    private fun addHistoryItem(
        doorStatus: DoorStatus
    ) {

        val history = DoorHistoryItem(
            id = System.nanoTime(),
            action = if (doorStatus == DoorStatus.LOCKED)
                DoorAction.LOCKED
            else
                DoorAction.UNLOCKED,
            performedBy = "You",
            timestamp = System.currentTimeMillis()
        )

        _historyItems.update {
            listOf(history) + it
        }
    }
}