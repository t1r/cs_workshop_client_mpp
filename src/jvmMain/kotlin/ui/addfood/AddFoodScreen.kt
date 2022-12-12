package ui.addfood

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import ui.addfood.AddFoodScreenModel.Label

data class AddFoodScreen(
    private val onResult: () -> Unit,
) : Screen {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { AddFoodScreenModel() }
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.current

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Добавление продукта") },
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
                Column(
                    modifier = Modifier.padding(pv).fillMaxSize().verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .alpha(if (state.isAddFoodInProgress) 1F else 0F),
                    )
                    Spacer(modifier = Modifier.weight(1F))
                    OutlinedTextField(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        value = state.name.orEmpty(),
                        onValueChange = { screenModel.onNameChanged(it) },
                        label = { Text("Название") },
                        isError = state.isError,
                    )
                    OutlinedTextField(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        value = state.weight.orEmpty(),
                        onValueChange = { screenModel.onWeightChanged(it) },
                        label = { Text("Вес") },
                        isError = state.isError,
                    )
                    Button(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        content = { Text("Добавить") },
                        onClick = { screenModel.addFood() },
                    )
                    if (state.isError) Text("Что-то пошло не так")
                    Spacer(modifier = Modifier.weight(1F))
                }
            },
        )

        LaunchedEffect(Unit) {
            screenModel.labels.collect { label ->
                when (label) {
                    Label.Back -> {
                        onResult()
                        navigator?.pop()
                    }
                }
            }
        }
    }
}