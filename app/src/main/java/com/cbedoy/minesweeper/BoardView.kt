package com.cbedoy.minesweeper

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


data class Mine(val hasABomb: Boolean)

@Composable
fun BoardView(
    minesViewModel: MinesViewModel = viewModel()
) {
    val context = LocalContext.current
    val state by minesViewModel.state.collectAsState()

    LaunchedEffect(state.isGameOver) {
        if (state.isGameOver) {
            Toast.makeText(context, "Game over bro", Toast.LENGTH_SHORT).show()
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        userScrollEnabled = false,
        content = {
            items(state.mines.size) { index ->
                val mine = state.mines[index]
                MineView(mine) { minesViewModel.onTap(it) }
            }
        }
    )
}

@Composable
fun MineView(mine: Mine, onTap: (Mine) -> Unit) {
    var isTapped by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.padding(2.dp),
        backgroundColor = getBackgroundColor(mine, isTapped)
    ) {
        Text(
            text = "",
            modifier = Modifier
                .padding(16.dp)
                .clickable(enabled = isTapped.not()) {
                    isTapped = true
                    onTap(mine)
                }
        )
    }
}

fun getBackgroundColor(mine: Mine, tapped: Boolean): Color {
    return if (tapped && mine.hasABomb) Color(0xffff0000)
    else if (tapped) Color(0xffaaaaaa)
    else Color(0xffffffff)
}

@Preview(showSystemUi = true)
@Composable
fun Preview_BoardView() {
    BoardView()
}