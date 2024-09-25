package com.example.limitless.data.ViewModels

import kotlinx.coroutines.flow.MutableStateFlow

class CurrentDayViewModel() {
    val currentScreenState: MutableStateFlow<CurrentDayScreenState> = MutableStateFlow(
        CurrentDayScreenState.Loading
    )
}

sealed class CurrentDayScreenState {
    data object Loading : CurrentDayScreenState()
    data class Content(val steps: Long, val dailyGoal: Long) : CurrentDayScreenState()
    data object Error: CurrentDayScreenState()
}