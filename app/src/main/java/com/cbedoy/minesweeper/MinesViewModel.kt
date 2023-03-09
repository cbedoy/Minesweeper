package com.cbedoy.minesweeper

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UiState(
    val mines: List<Mine> = emptyList(),
    val isGameOver : Boolean = false,
    val bombCount: Int = 0
)

class MinesViewModel : ViewModel(){

    private val _state = MutableStateFlow(UiState())
    val state get() = _state.asStateFlow()

    init {
        val mines = (1..66).map { Mine(it % 6 == 0) }
        _state.update {
            it.copy(mines = mines.shuffled())
        }
    }

    fun onTap(mine: Mine) {
        _state.update { state ->
            state.copy(
                isGameOver = state.bombCount >= 3,
                bombCount = if (mine.hasABomb) state.bombCount + 1 else state.bombCount
            )
        }
    }
}