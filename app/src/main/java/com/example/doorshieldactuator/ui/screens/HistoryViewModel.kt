package com.example.doorshieldactuator.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doorshieldactuator.model.DoorAction
import com.example.doorshieldactuator.model.DoorHistoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

enum class HistoryFilter {
    ALL,
    LOCKED,
    UNLOCKED
}

class HistoryViewModel : ViewModel() {

    private val _historyItems = MutableStateFlow(
        listOf(
            DoorHistoryItem(
                id = 1,
                action = DoorAction.UNLOCKED,
                performedBy = "You",
                timestamp = System.currentTimeMillis()
            ),
            DoorHistoryItem(
                id = 2,
                action = DoorAction.LOCKED,
                performedBy = "You",
                timestamp = System.currentTimeMillis() - 3_600_000
            ),
            DoorHistoryItem(
                id = 3,
                action = DoorAction.UNLOCKED,
                performedBy = "Family Member",
                timestamp = System.currentTimeMillis() - 86_400_000
            ),
            DoorHistoryItem(
                id = 4,
                action = DoorAction.LOCKED,
                performedBy = "Family Member",
                timestamp = System.currentTimeMillis() - 90_000_000
            )
        )
    )

    private val _selectedFilter =
        MutableStateFlow(HistoryFilter.ALL)

    val selectedFilter: StateFlow<HistoryFilter> =
        _selectedFilter.asStateFlow()

    val filteredHistory: StateFlow<List<DoorHistoryItem>> =
        combine(
            _historyItems,
            _selectedFilter
        ) { items, filter ->

            when (filter) {
                HistoryFilter.ALL -> items

                HistoryFilter.LOCKED ->
                    items.filter {
                        it.action == DoorAction.LOCKED
                    }

                HistoryFilter.UNLOCKED ->
                    items.filter {
                        it.action == DoorAction.UNLOCKED
                    }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = _historyItems.value
        )

    fun selectFilter(filter: HistoryFilter) {
        _selectedFilter.value = filter
    }
}