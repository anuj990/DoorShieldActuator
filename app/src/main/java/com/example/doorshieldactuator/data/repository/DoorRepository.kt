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

            if (response.isSuccessful && response.body() != null) {

                val body = response.body()!!

                _doorState.update {
                    it.copy(
                        connectionStatus =
                            if (body.connected)
                                DeviceConnectionStatus.CONNECTED
                            else
                                DeviceConnectionStatus.DISCONNECTED,

                        doorStatus =
                            if (body.door.equals(
                                    "locked",
                                    true
                                )
                            )
                                DoorStatus.LOCKED
                            else
                                DoorStatus.UNLOCKED,

                        isLoading = false,
                        errorMessage = null
                    )
                }

            } else {

                setConnection(
                    DeviceConnectionStatus.DISCONNECTED
                )
            }

        } catch (e: Exception) {

            setConnection(
                DeviceConnectionStatus.DISCONNECTED
            )
        }
    }

    suspend fun lockDoor() {

        val response = api.lockDoor()

        if (!response.isSuccessful) {

            throw Exception("Unable to lock door")
        }

        updateDoorStatus(
            DoorStatus.LOCKED
        )
    }

    suspend fun unlockDoor() {

        val response = api.unlockDoor()

        if (!response.isSuccessful) {

            throw Exception("Unable to unlock door")
        }

        updateDoorStatus(
            DoorStatus.UNLOCKED
        )
    }

    suspend fun checkMotion(): Boolean {

        return try {

            val response = api.getMotion()

            if (
                response.isSuccessful &&
                response.body() != null
            ) {

                response.body()!!.motion

            } else {

                false
            }

        } catch (e: Exception) {

            false
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
                errorMessage = message,
                isLoading = false
            )
        }
    }

    private fun addHistoryItem(
        doorStatus: DoorStatus
    ) {

        val item = DoorHistoryItem(

            id = System.nanoTime(),

            action =
                if (doorStatus == DoorStatus.LOCKED)
                    DoorAction.LOCKED
                else
                    DoorAction.UNLOCKED,

            performedBy = "You",

            timestamp = System.currentTimeMillis()
        )

        _historyItems.update {

            listOf(item) + it
        }
    }
}