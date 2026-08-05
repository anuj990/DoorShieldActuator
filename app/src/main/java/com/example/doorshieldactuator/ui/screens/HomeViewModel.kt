package com.example.doorshieldactuator.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doorshieldactuator.data.repository.DoorRepository
import com.example.doorshieldactuator.model.DeviceConnectionStatus
import com.example.doorshieldactuator.model.DoorHistoryItem
import com.example.doorshieldactuator.model.DoorStatus
import com.example.doorshieldactuator.model.DoorUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val uiState: StateFlow<DoorUiState> =
        DoorRepository.doorState

    val historyItems: StateFlow<List<DoorHistoryItem>> =
        DoorRepository.historyItems

    private val _motionDetected =
        MutableStateFlow(false)

    val motionDetected =
        _motionDetected.asStateFlow()

    private var previousMotion = false

    init {
        startPolling()
    }

    private fun startPolling() {

        viewModelScope.launch {

            while (isActive) {

                try {

                    DoorRepository.checkConnection()

                    val motion =
                        DoorRepository.checkMotion()

                    if (motion && !previousMotion) {

                        _motionDetected.value = true
                    }

                    if (!motion) {

                        _motionDetected.value = false
                    }

                    previousMotion = motion

                } catch (_: Exception) {

                    DoorRepository.setConnection(
                        DeviceConnectionStatus.DISCONNECTED
                    )
                }

                delay(1000)
            }
        }
    }

    fun onDoorActionClick() {

        if (uiState.value.isLoading) return

        viewModelScope.launch {

            DoorRepository.setLoading(true)

            try {

                when (uiState.value.doorStatus) {

                    DoorStatus.LOCKED ->
                        DoorRepository.unlockDoor()

                    DoorStatus.UNLOCKED ->
                        DoorRepository.lockDoor()
                }

            } catch (e: Exception) {

                DoorRepository.setError(
                    e.message ?: "Unknown Error"
                )
            }
        }
    }
}