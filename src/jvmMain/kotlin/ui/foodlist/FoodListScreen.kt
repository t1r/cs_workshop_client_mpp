package ui.foodlist

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen

object FoodListScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { FoodListScreenModel() }
        val state by screenModel.state.collectAsState()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Список продуктов") },
                )
            },
            content = { pv ->
                Crossfade(
                    modifier = Modifier.padding(pv).fillMaxSize(),
                    targetState = state.contentState,
                ) { contentState ->
                    when (contentState) {
                        FoodListScreenModel.ContentState.Idle,
                        FoodListScreenModel.ContentState.Loading -> Progress()

                        is FoodListScreenModel.ContentState.Result -> DataContent(

                        )
                    }
                }
            },
        )
    }

    @Composable
    private fun Progress() {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center).size(48.dp),
            )
        }
    }

    @Composable
    private fun DataContent() {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

        }
    }
}