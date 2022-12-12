package ui.food

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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

data class FoodScreen(
    private val id: Long,
) : Screen {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { FoodScreenModel(id) }
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.current

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Продукт") },
                    navigationIcon = {
                        IconButton(
                            onClick = { navigator?.pop() },
                        ) {
                            Icon(Icons.Filled.ArrowBack, "")
                        }
                    },
                )
            },
            content = { pv ->
                Crossfade(
                    modifier = Modifier.padding(pv).fillMaxSize(),
                    targetState = state.contentState,
                ) { contentState ->
                    when (contentState) {
                        is FoodScreenModel.ContentState.Idle,
                        is FoodScreenModel.ContentState.Loading -> Progress()

                        is FoodScreenModel.ContentState.Result -> DataContent(
                            model = contentState.model,
                        )

                        is FoodScreenModel.ContentState.Error -> Error { screenModel.fetchData() }
                    }
                }
            },
        )
    }

    @Composable
    private fun DataContent(
        model: FoodModel,
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text("Название")
                Spacer(
                    modifier = Modifier
                        .weight(1F)
                        .padding(16.dp),
                )
                Text(model.name)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text("Вес")
                Spacer(
                    modifier = Modifier
                        .weight(1F)
                        .padding(16.dp),
                )
                Text("${model.weight} грамм")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text("Id")
                Spacer(modifier = Modifier.weight(1F))
                Text("${model.id}")
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