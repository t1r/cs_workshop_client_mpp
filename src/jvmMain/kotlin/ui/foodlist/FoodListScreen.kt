package ui.foodlist

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import data.api.FoodModel

object FoodListScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { FoodListScreenModel() }
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.current

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
                        is FoodListScreenModel.ContentState.Idle,
                        is FoodListScreenModel.ContentState.Loading -> Progress()

                        is FoodListScreenModel.ContentState.Result -> DataContent(
                            list = contentState.foodList,
                            onItemClicked = {
//                               navigator. push()
                            },
                        )

                        is FoodListScreenModel.ContentState.Error -> Error { screenModel.fetchData() }
                    }
                }
            },
        )
    }

    @Composable
    private fun DataContent(
        list: List<FoodModel>,
        onItemClicked: (Long) -> Unit,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(list.size) { index ->
                val item = list[index]
                Row(modifier = Modifier.fillMaxWidth().clickable { onItemClicked(item.id) }) {
                    Text(item.name)
                    Spacer(modifier = Modifier.weight(1F))
                    Text("${item.weight} грамм")
                }
            }
        }
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
    private fun Error(
        onAction: () -> Unit,
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(1F))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Что-то пошло не так",
            )
            Button(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).align(Alignment.CenterHorizontally),
                content = { Text("Повторить") },
                onClick = onAction,
            )
            Spacer(modifier = Modifier.weight(1F))
        }
    }
}