package com.cbedoy.minesweeper

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.min

data class UiState(
    val mines: List<Mine> = emptyList(),
    val isGameOver : Boolean = false,
    val bombCount: Int = 0,
    val points: Int = 0,
)

class MinesViewModel : ViewModel(){

    private val _state = MutableStateFlow(UiState())
    val state get() = _state.asStateFlow()

    private val point = 1
    private var power = 1

    init {
        initGame()
    }

    private fun initGame() {
        val mines = (1..66).map { Mine(it % 6 == 0) }
        _state.value = UiState(mines.shuffled())
    }

    fun onTap(mine: Mine) {
        _state.update { state ->
            state.copy(
                isGameOver = state.bombCount >= 3,
                bombCount = if (mine.hasABomb) state.bombCount.inc() else state.bombCount,
                points = if (mine.hasABomb) state.points - 10 else (state.points + (point * power)).also {
                    power++
                }
            )
        }
    }

    fun restartGame() {
        initGame()
    }
}