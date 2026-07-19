package com.example.doorshieldactuator.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doorshieldactuator.model.DoorStatus
import com.example.doorshieldactuator.model.DoorUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        DoorUiState()
    )

    val uiState: StateFlow<DoorUiState> =
        _uiState.asStateFlow()

    fun onDoorActionClick() {

        if (_uiState.value.isLoading) {
            return
        }

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )


            delay(500)

            val newDoorStatus =
                when (_uiState.value.doorStatus) {

                    DoorStatus.LOCKED -> {
                        DoorStatus.UNLOCKED
                    }

                    DoorStatus.UNLOCKED -> {
                        DoorStatus.LOCKED
                    }
                }

            _uiState.value = _uiState.value.copy(
                doorStatus = newDoorStatus,
                isLoading = false
            )
        }
    }
}