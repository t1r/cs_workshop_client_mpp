package ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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

object AuthScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { AuthScreenModel() }
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.current

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Авторизация") },
                )
            },
            content = { pv ->
                Column(
                    modifier = Modifier.padding(pv).fillMaxSize().verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.weight(1F))
                    OutlinedTextField(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        value = state.name.orEmpty(),
                        onValueChange = { screenModel.onNameChanged(it) },
                        label = { Text("Имя") },
                    )
                    OutlinedTextField(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        value = state.name.orEmpty(),
                        onValueChange = { screenModel.onPasswordChanged(it) },
                        label = { Text("Пароль") },
                    )
                    Button(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        content = { Text("Авторизация") },
                        onClick = { screenModel.auth() },
                    )
                    Spacer(modifier = Modifier.weight(1F))
                    Button(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        content = { Text("По умолчанию") },
                        onClick = { screenModel.setDefault() },
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            },
        )
    }
}