package com.example.doorshieldactuator.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doorshieldactuator.data.repository.DoorRepository
import com.example.doorshieldactuator.model.DoorHistoryItem
import com.example.doorshieldactuator.model.DoorUiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val uiState: StateFlow<DoorUiState> =
        DoorRepository.doorState

    val historyItems: StateFlow<List<DoorHistoryItem>> =
        DoorRepository.historyItems

    fun onDoorActionClick() {

        if (uiState.value.isLoading) {
            return
        }

        viewModelScope.launch {

            DoorRepository.toggleDoor()
        }
    }
}